package com.vampyreworld.w2t.shomeft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.MasterGoal
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.profile.GetUserProfileUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface HomeComponent {
    val state: Value<HomeContract.State>
    val sideEffects: Flow<HomeContract.SideEffect>

    fun onIntent(intent: HomeContract.Intent)

    fun onNavigateToTarget()
    fun onNavigateToProfile()
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
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val navigateToTarget: (Long?) -> Unit,
    private val navigateToProfile: () -> Unit,
    private val navigateToMoodAdd: () -> Unit,
    private val navigateToSChallenge: (Long?) -> Unit,
    private val navigateToDecisionMaking: (Long) -> Unit,
    private val navigateToSolution: () -> Unit,
    private val navigateToPreferences: () -> Unit,
    private val navigateToAboutUs: () -> Unit,
    private val onExit: () -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val scope = componentScope()
    private val _state = MutableValue(HomeContract.State())
    override val state: Value<HomeContract.State> = _state

    private val _sideEffects = MutableSharedFlow<HomeContract.SideEffect>()
    override val sideEffects = _sideEffects.asSharedFlow()

    private var isBackDouble = false

    init {
        loadGoals()
        loadProfile()
        backHandler.register(BackCallback {
            if (isBackDouble) {
                onExit()
            } else {
                isBackDouble = true
                scope.launch {
                    _sideEffects.emit(HomeContract.SideEffect.ShowToast("D-Back for Exit"))
                    delay(2000)
                    isBackDouble = false
                }
            }
        })
    }

    private fun loadGoals() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getGoalsUseCase().collect { goals ->
                val masterGoals = goals.filterIsInstance<MasterGoal>()
                _state.value = _state.value.copy(masterGoals = masterGoals, isLoading = false)
            }
        }
    }

    private fun loadProfile() {
        scope.launch {
            getUserProfileUseCase().collect { profile ->
                _state.value = _state.value.copy(
                    userName = profile.name.ifEmpty { "کاربر" },
                    avatarUrl = profile.avatarUrl
                )
            }
        }
    }

    override fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            HomeContract.Intent.OnProfileClick -> {
                navigateToProfile()
            }
            HomeContract.Intent.CreateMasterGoal -> {
                navigateToTarget(null)
            }
            HomeContract.Intent.OnCheckMoodClick -> {
                navigateToMoodAdd()
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
            is HomeContract.Intent.OnActionCheck -> {
                // Handle Action Check
            }
            is HomeContract.Intent.OnViewStrategyClick -> {
                // Handle View Strategy
            }
        }
    }

    override fun onNavigateToTarget() = navigateToTarget(null)
    override fun onNavigateToProfile() = navigateToProfile()
    override fun onNavigateToMoodAdd() = navigateToMoodAdd()
    override fun onNavigateToSChallenge() = navigateToSChallenge(0L)
    override fun onNavigateToDecisionMaking() = navigateToDecisionMaking(0L)
    override fun onNavigateToSolution() = navigateToSolution()
    override fun onNavigateToPreferences() = navigateToPreferences()
    override fun onNavigateToAboutUs() = navigateToAboutUs()
}
