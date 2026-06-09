package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.targetft.TargetContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface TargetComponent {
    val state: Value<TargetContract.State>
    val sideEffects: Flow<TargetContract.SideEffect>
    
    fun onIntent(intent: TargetContract.Intent)
}

class DefaultTargetComponent(
    componentContext: ComponentContext,
    private val goalId: Long?,
    private val initialTier: String? = null,
    private val parentId: Long? = null,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val onBack: () -> Unit,
    private val navigateToDecision: (Long) -> Unit = {},
    private val navigateToMood: () -> Unit = {},
    private val navigateToGoal: (Long) -> Unit = {},
    private val navigateToChildTarget: (parentId: Long, tier: String) -> Unit = { _, _ -> },
    private val navigateToChallenge: (goalId: Long) -> Unit = {},
    private val navigateToChallengeDetail: (goalId: Long, challengeId: Long) -> Unit = { _, _ -> }
) : TargetComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(TargetContract.State(initialTier = initialTier, parentId = parentId))
    override val state: Value<TargetContract.State> = _state

    private val _sideEffects = MutableSharedFlow<TargetContract.SideEffect>()
    override val sideEffects: Flow<TargetContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            if (goalId != null) {
                getGoalsUseCase().collect { goals ->
                    val selectedGoal = goals.find { it.id == goalId }
                    val relatedGoals = goals.filter { it.upperGoalId == goalId }
                    _state.value = _state.value.copy(
                        selectedGoal = selectedGoal,
                        relatedGoals = relatedGoals,
                        isLoading = false
                    )
                }
            } else {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    override fun onIntent(intent: TargetContract.Intent) {
        when (intent) {
            TargetContract.Intent.OnBackClicked -> {
                if (_state.value.currentScreen != TargetContract.Screen.DETAIL) {
                    _state.value = _state.value.copy(currentScreen = TargetContract.Screen.DETAIL)
                } else {
                    onBack()
                }
            }
            TargetContract.Intent.Refresh -> loadData()
            TargetContract.Intent.CancelGoal -> {
                // Handle CancelGoal
            }
            TargetContract.Intent.CreateChallenge -> {
                _state.value = _state.value.copy(currentScreen = TargetContract.Screen.CHALLENGE_CREATE)
            }
            TargetContract.Intent.CreateChildGoal -> {
                state.value.selectedGoal?.let { currentGoal ->
                    val childTier = when (currentGoal.tier) {
                        GoalTier.MASTER -> "MILESTONE"
                        GoalTier.MILESTONE -> "ACTION"
                        GoalTier.ACTION -> "ACTION"
                    }
                    navigateToChildTarget(currentGoal.id, childTier)
                }
            }
            TargetContract.Intent.MakeDecision -> {
                goalId?.let { navigateToDecision(it) }
            }
            TargetContract.Intent.SetMood -> {
                navigateToMood()
            }
            TargetContract.Intent.NavigateToChallengeList -> {
                _state.value = _state.value.copy(currentScreen = TargetContract.Screen.CHALLENGE_LIST)
            }
            TargetContract.Intent.NavigateToAppraise -> {
                _state.value = _state.value.copy(currentScreen = TargetContract.Screen.GOAL_APPRAISE)
            }
            TargetContract.Intent.NavigateToDefineSteps -> {
                _state.value = _state.value.copy(currentScreen = TargetContract.Screen.DEFINE_STEPS)
            }
            is TargetContract.Intent.OnSaveGoal -> {
                saveGoal(intent)
            }
            is TargetContract.Intent.OnSaveChallenge -> {
                // Handle OnSaveChallenge in DefaultTargetComponent
            }
            is TargetContract.Intent.OnChallengeClick -> {
                goalId?.let { navigateToChallengeDetail(it, intent.challengeId) }
            }
            is TargetContract.Intent.OnGoalClick -> {
                navigateToGoal(intent.goalId)
            }
            is TargetContract.Intent.DeleteSubGoal -> {
                // Handle DeleteSubGoal
            }
            is TargetContract.Intent.ReplaceSubGoal -> {
                // Handle ReplaceSubGoal
            }
            is TargetContract.Intent.UpdateGoal -> {
                scope.launch {
                    saveGoalUseCase(intent.goal)
                }
            }
        }
    }

    private fun saveGoal(intent: TargetContract.Intent.OnSaveGoal) {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val newGoal = Goal(
                    id = 0, // Database will generate
                    title = intent.title,
                    description = intent.description,
                    tier = GoalTier.valueOf(intent.tier),
                    upperGoalId = parentId,
                    priority = 50 // Default
                )
                saveGoalUseCase(newGoal)
                onBack()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}
