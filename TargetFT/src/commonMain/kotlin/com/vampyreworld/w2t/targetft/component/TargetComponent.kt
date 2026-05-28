package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Cost
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalTier
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
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val onBack: () -> Unit,
    private val navigateToDecision: (Long) -> Unit = {},
    private val navigateToMood: () -> Unit = {}
) : TargetComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        TargetContract.State(
            selectedGoal = goalId?.let { 
                Goal(
                    id = it,
                    title = "Goal $it",
                    tier = if (it == 1L) GoalTier.MASTER else if (it == 101L) GoalTier.MILESTONE else GoalTier.ACTION,
                    childGoalIds = listOf(101, 102)
                ) 
            },
            relatedGoals = if (goalId == 1L) listOf(
                Goal(id = 101, title = "Milestone 101", upperGoalId = 1, tier = GoalTier.MILESTONE),
                Goal(id = 102, title = "Milestone 102", upperGoalId = 1, tier = GoalTier.MILESTONE)
            ) else emptyList(),
            challenges = if (goalId != null) listOf(
                Challenges(501, goalId, "Technical Barrier", "Hard to implement X", Cost(10, 50, 100), 80, true, goalId, emptyList(), emptyList(), emptyList(), 10, null, null, emptyList())
            ) else emptyList()
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
                // Handle CreateChallenge
            }
            TargetContract.Intent.CreateMilestone -> {
                // Handle CreateMilestone
            }
            TargetContract.Intent.MakeDecision -> {
                goalId?.let { navigateToDecision(it) }
            }
            TargetContract.Intent.SetMood -> {
                navigateToMood()
            }
            is TargetContract.Intent.OnChallengeClick -> {
                // Navigate to challenge detail
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
