package com.vampyreworld.w2t.targetft.editTargetGoal

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.*
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface EditTargetGoalComponent {
    val state: Value<EditTargetGoalContract.State>
    val sideEffects: Flow<EditTargetGoalContract.SideEffect>
    
    fun onIntent(intent: EditTargetGoalContract.Intent)
}

class DefaultEditTargetGoalComponent(
    componentContext: ComponentContext,
    private val masterGoalId: Long,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val onBack: () -> Unit
) : EditTargetGoalComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(EditTargetGoalContract.State())
    override val state: Value<EditTargetGoalContract.State> = _state

    private val _sideEffects = MutableSharedFlow<EditTargetGoalContract.SideEffect>()
    override val sideEffects: Flow<EditTargetGoalContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        loadData()
    }

    private fun loadData() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getGoalsUseCase().collect { goals ->
                val masterGoal = goals.find { it.id == masterGoalId }
                val milestones = goals.filter { it.tier == GoalTier.MILESTONE && it.upperGoalId == masterGoalId }
                val milestoneIds = milestones.map { it.id }
                val actions = goals.filter { it.tier == GoalTier.ACTION && it.upperGoalId in milestoneIds }
                
                _state.value = _state.value.copy(
                    masterGoal = masterGoal,
                    milestones = milestones,
                    actions = actions,
                    isLoading = false
                )
            }
        }
    }

    override fun onIntent(intent: EditTargetGoalContract.Intent) {
        when (intent) {
            EditTargetGoalContract.Intent.OnBackClicked -> onBack()
            is EditTargetGoalContract.Intent.OnNewMilestoneTitleChanged -> {
                _state.value = _state.value.copy(newMilestoneTitle = intent.title)
            }
            is EditTargetGoalContract.Intent.OnNewActionTitleChanged -> {
                _state.value = _state.value.copy(newActionTitle = intent.title)
            }
            is EditTargetGoalContract.Intent.OnMilestoneSelectedForAction -> {
                _state.value = _state.value.copy(selectedMilestoneIdForAction = intent.milestoneId)
            }
            EditTargetGoalContract.Intent.OnAddMilestoneClicked -> addMilestone()
            EditTargetGoalContract.Intent.OnAddActionClicked -> addAction()
            is EditTargetGoalContract.Intent.OnRemoveGoalClicked -> {
                scope.launch {
                    deleteGoalUseCase(intent.goalId)
                }
            }
            EditTargetGoalContract.Intent.OnFinishClicked -> onBack()
        }
    }

    private fun addMilestone() {
        val title = _state.value.newMilestoneTitle
        if (title.isBlank()) return
        
        scope.launch {
            val newMilestone = MilestoneGoal(
                id = 0,
                title = title,
                masterGoalId = masterGoalId,
                status = GoalStatus.ACTIVE,
                priority = 50
            )
            saveGoalUseCase(newMilestone)
            _state.value = _state.value.copy(newMilestoneTitle = "")
        }
    }

    private fun addAction() {
        val title = _state.value.newActionTitle
        val milestoneId = _state.value.selectedMilestoneIdForAction
        if (title.isBlank() || milestoneId == null) return
        
        scope.launch {
            val newAction = ActionGoal(
                id = 0,
                title = title,
                milestoneGoalId = milestoneId,
                status = GoalStatus.ACTIVE,
                priority = 50
            )
            saveGoalUseCase(newAction)
            _state.value = _state.value.copy(newActionTitle = "")
        }
    }
}
