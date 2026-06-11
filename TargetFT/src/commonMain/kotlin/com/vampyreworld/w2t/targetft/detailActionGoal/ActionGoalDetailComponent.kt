package com.vampyreworld.w2t.targetft.detailActionGoal

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.ActionGoal
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface ActionGoalDetailComponent {
    val state: Value<ActionGoalDetailContract.State>
    val sideEffects: Flow<ActionGoalDetailContract.SideEffect>
    
    fun onIntent(intent: ActionGoalDetailContract.Intent)
}

class DefaultActionGoalDetailComponent(
    componentContext: ComponentContext,
    private val actionGoalId: Long,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val onBack: () -> Unit,
    private val onNavigateToEdit: (Long) -> Unit,
    private val onNavigateToGoal: (Long) -> Unit
) : ActionGoalDetailComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(ActionGoalDetailContract.State())
    override val state: Value<ActionGoalDetailContract.State> = _state

    private val _sideEffects = MutableSharedFlow<ActionGoalDetailContract.SideEffect>()
    override val sideEffects: Flow<ActionGoalDetailContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getGoalsUseCase().collect { goals ->
                val actionGoal = goals.find { it.id == actionGoalId } as? ActionGoal
                if (actionGoal != null) {
                    val milestone = goals.find { it.id == actionGoal.milestoneGoalId }
                    val masterGoal = milestone?.upperGoalId?.let { pid -> goals.find { it.id == pid } }
                    _state.value = _state.value.copy(
                        actionGoal = actionGoal,
                        milestone = milestone,
                        masterGoal = masterGoal,
                        isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        }
    }

    override fun onIntent(intent: ActionGoalDetailContract.Intent) {
        when (intent) {
            ActionGoalDetailContract.Intent.OnBackClicked -> onBack()
            ActionGoalDetailContract.Intent.OnEditClicked -> onNavigateToEdit(actionGoalId)
            ActionGoalDetailContract.Intent.OnMarkCompleteClicked -> {
                state.value.actionGoal?.let { goal ->
                    scope.launch {
                        saveGoalUseCase(goal.withStatus(GoalStatus.COMPLETED))
                    }
                }
            }
            is ActionGoalDetailContract.Intent.OnGoalLinkClick -> onNavigateToGoal(intent.goalId)
        }
    }
}
