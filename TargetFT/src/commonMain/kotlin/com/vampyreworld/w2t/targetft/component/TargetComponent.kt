package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Cost
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.domain.data.model.StabilityCondition
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.targetft.TargetContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

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
    private val navigateToChildTarget: (parentId: Long, tier: String) -> Unit = { _, _ -> },
    private val navigateToChallenge: (goalId: Long) -> Unit = {},
    private val navigateToChallengeDetail: (goalId: Long, challengeId: Long) -> Unit = { _, _ -> }
) : TargetComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        TargetContract.State(
            selectedGoal = goalId?.let { 
                Goal(
                    id = it,
                    title = "Goal $it",
                    tier = if (it == 1L) GoalTier.MASTER else if (it == 101L) GoalTier.MILESTONE else GoalTier.ACTION,
                    childGoalIds = if (it == 1L) listOf(101, 102) else emptyList(),
                    priority = if (it == 1L) 85 else 40
                ) 
            },
            relatedGoals = if (goalId == 1L) listOf(
                Goal(id = 101, title = "Milestone 101", upperGoalId = 1, tier = GoalTier.MILESTONE),
                Goal(id = 102, title = "Milestone 102", upperGoalId = 1, tier = GoalTier.MILESTONE)
            ) else emptyList(),
            challenges = if (goalId != null) listOf(
                Challenges(
                    id = 501, 
                    solvingBeforeGoalId = goalId, 
                    title = "Technical Barrier", 
                    desc = "Hard to implement X", 
                    cost = Cost(10, 50, 100), 
                    priority = 80, 
                    isBarrier = true, 
                    parentGoalId = goalId, 
                    moodImpact = 10, 
                    prosAfterSolve = null, 
                    consAfterFailure = null,
                    stabilityConditions = listOf(
                        StabilityCondition(1, "Internet Access", "Stable connection required", true),
                        StabilityCondition(2, "Energy Level", "High focus needed", false)
                    )
                )
            ) else emptyList(),
            initialTier = initialTier,
            parentId = parentId
        )
    )
    override val state: Value<TargetContract.State> = _state

    private val _sideEffects = MutableSharedFlow<TargetContract.SideEffect>()
    override val sideEffects: Flow<TargetContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: TargetContract.Intent) {
        when (intent) {
            TargetContract.Intent.OnBackClicked -> onBack()
            TargetContract.Intent.Refresh -> {
                // Handle refresh
            }
            TargetContract.Intent.CancelGoal -> {
                // Handle CancelGoal
            }
            TargetContract.Intent.CreateChallenge -> {
                goalId?.let { navigateToChallenge(it) }
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
            is TargetContract.Intent.OnChallengeClick -> {
                goalId?.let { navigateToChallengeDetail(it, intent.challengeId) }
            }
            is TargetContract.Intent.DeleteSubGoal -> {
                // Handle DeleteSubGoal
            }
            is TargetContract.Intent.ReplaceSubGoal -> {
                // Handle ReplaceSubGoal
            }
        }
    }
}
