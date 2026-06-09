package com.vampyreworld.w2t.targetft.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Cost
import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.targetft.TargetContract
import kotlinx.coroutines.launch

class TargetStoreFactory(
    private val storeFactory: StoreFactory,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val addChallengeUseCase: AddChallengeUseCase,
    private val getChallengesUseCase: GetChallengesUseCase,
    private val goalId: Long? = null,
    private val initialTier: String? = null,
    private val parentId: Long? = null
) {
    fun create(): TargetStore =
        object : TargetStore, Store<TargetStore.Intent, TargetContract.State, TargetStore.Label> by storeFactory.create(
            name = "TargetStore",
            initialState = TargetContract.State(
                initialTier = initialTier,
                parentId = parentId
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data object Loading : Msg
        data class Loaded(val selectedGoal: Goal?, val relatedGoals: List<Goal>, val challenges: List<Challenges>) : Msg
        data class SetScreen(val screen: TargetContract.Screen) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<TargetStore.Intent, Nothing, TargetContract.State, Msg, TargetStore.Label>() {
        override fun executeIntent(intent: TargetStore.Intent) {
            when (intent) {
                TargetStore.Intent.Refresh -> {
                    loadData()
                }
                TargetStore.Intent.CancelGoal -> {
                    cancelGoal()
                }
                TargetStore.Intent.CreateChallenge -> {
                    dispatch(Msg.SetScreen(TargetContract.Screen.CHALLENGE_CREATE))
                }
                TargetStore.Intent.NavigateToChallengeList -> {
                    dispatch(Msg.SetScreen(TargetContract.Screen.CHALLENGE_LIST))
                }
                TargetStore.Intent.NavigateToAppraise -> {
                    val screen = if (state().selectedGoal != null) TargetContract.Screen.GOAL_APPRAISE else TargetContract.Screen.DETAIL
                    dispatch(Msg.SetScreen(screen))
                }
                TargetStore.Intent.NavigateToDefineSteps -> {
                    dispatch(Msg.SetScreen(TargetContract.Screen.DEFINE_STEPS))
                }
                TargetStore.Intent.Back -> {
                    if (state().currentScreen != TargetContract.Screen.DETAIL) {
                        dispatch(Msg.SetScreen(TargetContract.Screen.DETAIL))
                    }
                }
                is TargetStore.Intent.DeleteSubGoal -> {
                    deleteGoal(intent.goalId)
                }
                is TargetStore.Intent.OnChallengeClick -> {
                    // navigateToChallengeDetail handled by component, or we can handle it here
                }
                is TargetStore.Intent.ReplaceSubGoal -> {
                }
                is TargetStore.Intent.SaveGoal -> {
                    saveGoal(intent)
                }
                is TargetStore.Intent.SaveChallenge -> {
                    saveChallenge(intent)
                }
                is TargetStore.Intent.UpdateGoal -> {
                    updateGoal(intent.goal)
                }
            }
        }

        private fun updateGoal(goal: Goal) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    saveGoalUseCase(goal)
                } catch (e: Exception) {
                    publish(TargetStore.Label.Error(e.message ?: "Failed to update goal"))
                }
            }
        }

        private fun loadData() {
            scope.launch {
                dispatch(Msg.Loading)
                getGoalsUseCase().collect { goals ->
                    val selectedGoal = goals.find { it.id == goalId }
                    val relatedGoals = if (goalId != null) goals.filter { it.upperGoalId == goalId } else emptyList()
                    
                    if (goalId != null) {
                        getChallengesUseCase(goalId).collect { challenges ->
                            dispatch(Msg.Loaded(selectedGoal, relatedGoals, challenges))
                        }
                    } else {
                        dispatch(Msg.Loaded(selectedGoal, relatedGoals, emptyList()))
                    }
                }
            }
        }

        private fun saveGoal(intent: TargetStore.Intent.SaveGoal) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    val newGoal = Goal(
                        id = 0,
                        title = intent.title,
                        description = intent.description,
                        tier = GoalTier.valueOf(intent.tier),
                        upperGoalId = state().parentId,
                        priority = 50
                    )
                    saveGoalUseCase(newGoal)
                    publish(TargetStore.Label.Saved)
                } catch (e: Exception) {
                    publish(TargetStore.Label.Error(e.message ?: "Unknown error"))
                }
            }
        }

        private fun saveChallenge(intent: TargetStore.Intent.SaveChallenge) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    val challenge = Challenges(
                        id = 0,
                        solvingBeforeGoalId = null,
                        title = intent.title,
                        desc = intent.description,
                        cost = Cost(0, 0, 0),
                        priority = when(intent.impact) {
                            "High" -> 90
                            "Medium" -> 50
                            else -> 20
                        },
                        isBarrier = true,
                        parentGoalId = intent.goalId ?: goalId,
                        moodImpact = 0,
                        prosAfterSolve = null,
                        consAfterFailure = null
                    )
                    addChallengeUseCase(challenge)
                    dispatch(Msg.SetScreen(TargetContract.Screen.DETAIL))
                    loadData()
                } catch (e: Exception) {
                    publish(TargetStore.Label.Error(e.message ?: "Failed to save challenge"))
                } finally {
                    dispatch(Msg.Loaded(state().selectedGoal, state().relatedGoals, state().challenges))
                }
            }
        }

        private fun cancelGoal() {
            val currentGoal = state().selectedGoal ?: return
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    saveGoalUseCase(currentGoal.copy(status = GoalStatus.CANCELLED))
                    publish(TargetStore.Label.Saved) // Or a specific label for cancellation
                } catch (e: Exception) {
                    publish(TargetStore.Label.Error(e.message ?: "Failed to cancel goal"))
                }
            }
        }

        private fun deleteGoal(id: Long) {
            scope.launch {
                dispatch(Msg.Loading)
                try {
                    deleteGoalUseCase(id)
                    // Refresh is automatic since getGoalsUseCase is a Flow
                } catch (e: Exception) {
                    publish(TargetStore.Label.Error(e.message ?: "Failed to delete goal"))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<TargetContract.State, Msg> {
        override fun TargetContract.State.reduce(msg: Msg): TargetContract.State =
            when (msg) {
                Msg.Loading -> copy(isLoading = true)
                is Msg.Loaded -> copy(
                    isLoading = false,
                    selectedGoal = msg.selectedGoal,
                    relatedGoals = msg.relatedGoals,
                    challenges = msg.challenges
                )
                is Msg.SetScreen -> copy(currentScreen = msg.screen)
            }
    }
}
