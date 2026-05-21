package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
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
    private val onBack: () -> Unit
) : TargetComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        TargetContract.State(
            selectedGoal = goalId?.let { 
                Goal(it, null, emptyList(), if (it == 1L) GoalTier.MASTER else GoalTier.MILESTONE, false, emptyList(), null) 
            }
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
        }
    }
}
