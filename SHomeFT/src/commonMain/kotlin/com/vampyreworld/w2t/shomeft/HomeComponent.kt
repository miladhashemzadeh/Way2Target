package com.vampyreworld.w2t.shomeft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface HomeComponent {
    val state: Value<HomeContract.State>
    val sideEffects: Flow<HomeContract.SideEffect>

    fun onIntent(intent: HomeContract.Intent)

    fun onNavigateToTarget()
    fun onNavigateToMoodAdd()
    fun onNavigateToSChallenge()
    fun onNavigateToDecisionMaking()
    fun onNavigateToSolution()
    fun onNavigateToPreferences()
    fun onNavigateToAboutUs()
}

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val navigateToTarget: (Long?) -> Unit,
    private val navigateToMoodAdd: () -> Unit,
    private val navigateToSChallenge: (Long) -> Unit,
    private val navigateToDecisionMaking: (Long) -> Unit,
    private val navigateToSolution: () -> Unit,
    private val navigateToPreferences: () -> Unit,
    private val navigateToAboutUs: () -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(HomeContract.State())
    override val state: Value<HomeContract.State> = _state

    private val _sideEffects = MutableSharedFlow<HomeContract.SideEffect>()
    override val sideEffects = _sideEffects.asSharedFlow()

    init {
        loadGoals()
    }

    private fun loadGoals() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getGoalsUseCase().collect { goals ->
                val masterGoals = goals.filter { it.tier == GoalTier.MASTER }
                _state.value = _state.value.copy(masterGoals = masterGoals, isLoading = false)
            }
        }
    }

    override fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            HomeContract.Intent.OnProfileClick -> {
                // Handle intent
            }
            HomeContract.Intent.CreateMasterGoal -> {
                navigateToTarget(null)
            }
            is HomeContract.Intent.OnMasterGoalClick -> {
                navigateToTarget(intent.goalId)
            }
            is HomeContract.Intent.DeleteMasterGoal -> {
                scope.launch {
                    deleteGoalUseCase(intent.goalId)
                }
            }
            is HomeContract.Intent.CreateChallengeForMasterGoal -> {
                navigateToSChallenge(intent.goalId)
            }
        }
    }

    override fun onNavigateToTarget() = navigateToTarget(null)
    override fun onNavigateToMoodAdd() = navigateToMoodAdd()
    override fun onNavigateToSChallenge() = navigateToSChallenge(0L)
    override fun onNavigateToDecisionMaking() = navigateToDecisionMaking(0L)
    override fun onNavigateToSolution() = navigateToSolution()
    override fun onNavigateToPreferences() = navigateToPreferences()
    override fun onNavigateToAboutUs() = navigateToAboutUs()
}
