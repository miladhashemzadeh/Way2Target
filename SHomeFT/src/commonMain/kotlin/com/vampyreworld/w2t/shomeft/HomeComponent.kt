package com.vampyreworld.w2t.shomeft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalTier
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface HomeComponent {
    val state: Value<HomeContract.State>
    val sideEffects: kotlinx.coroutines.flow.Flow<HomeContract.SideEffect>

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
    private val navigateToTarget: (Long?) -> Unit,
    private val navigateToMoodAdd: () -> Unit,
    private val navigateToSChallenge: (Long) -> Unit,
    private val navigateToDecisionMaking: (Long) -> Unit,
    private val navigateToSolution: () -> Unit,
    private val navigateToPreferences: () -> Unit,
    private val navigateToAboutUs: () -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(
        HomeContract.State(
            masterGoals = listOf(
                Goal(1, null, emptyList(), GoalTier.MASTER, false, emptyList(), null),
                Goal(2, null, emptyList(), GoalTier.MASTER, false, emptyList(), null),
                Goal(3, null, emptyList(), GoalTier.MASTER, false, emptyList(), null),
                Goal(4, null, emptyList(), GoalTier.MASTER, false, emptyList(), null),
                Goal(5, null, emptyList(), GoalTier.MASTER, false, emptyList(), null)
            )
        )
    )
    override val state: Value<HomeContract.State> = _state

    private val _sideEffects = MutableSharedFlow<HomeContract.SideEffect>()
    override val sideEffects = _sideEffects.asSharedFlow()

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
                // For now just remove from list to show it "works"
                _state.value = _state.value.copy(
                    masterGoals = _state.value.masterGoals.filter { it.id != intent.goalId }
                )
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
