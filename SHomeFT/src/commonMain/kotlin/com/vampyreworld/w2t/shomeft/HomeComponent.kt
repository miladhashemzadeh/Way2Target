package com.vampyreworld.w2t.shomeft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
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
    private val navigateToTarget: () -> Unit,
    private val navigateToMoodAdd: () -> Unit,
    private val navigateToSChallenge: () -> Unit,
    private val navigateToDecisionMaking: () -> Unit,
    private val navigateToSolution: () -> Unit,
    private val navigateToPreferences: () -> Unit,
    private val navigateToAboutUs: () -> Unit
) : HomeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(HomeContract.State())
    override val state: Value<HomeContract.State> = _state

    private val _sideEffects = MutableSharedFlow<HomeContract.SideEffect>()
    override val sideEffects = _sideEffects.asSharedFlow()

    override fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            HomeContract.Intent.OnProfileClick -> {
                // Handle intent
            }
        }
    }

    override fun onNavigateToTarget() = navigateToTarget()
    override fun onNavigateToMoodAdd() = navigateToMoodAdd()
    override fun onNavigateToSChallenge() = navigateToSChallenge()
    override fun onNavigateToDecisionMaking() = navigateToDecisionMaking()
    override fun onNavigateToSolution() = navigateToSolution()
    override fun onNavigateToPreferences() = navigateToPreferences()
    override fun onNavigateToAboutUs() = navigateToAboutUs()
}
