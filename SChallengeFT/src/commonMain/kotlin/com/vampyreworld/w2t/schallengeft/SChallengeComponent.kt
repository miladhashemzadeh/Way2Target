package com.vampyreworld.w2t.schallengeft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface SChallengeComponent {
    val state: Value<SChallengeContract.State>
    val sideEffects: Flow<SChallengeContract.SideEffect>
    
    fun onIntent(intent: SChallengeContract.Intent)
}

class DefaultSChallengeComponent(
    componentContext: ComponentContext,
    private val addChallengeUseCase: AddChallengeUseCase,
    private val getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit
) : SChallengeComponent, ComponentContext by componentContext {

    private val _state = MutableValue(SChallengeContract.State())
    override val state: Value<SChallengeContract.State> = _state

    private val _sideEffects = MutableSharedFlow<SChallengeContract.SideEffect>()
    override val sideEffects: Flow<SChallengeContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: SChallengeContract.Intent) {
        when (intent) {
            SChallengeContract.Intent.OnBackClicked -> onBack()
            is SChallengeContract.Intent.OnAddChallenge -> {
                // Handle add challenge
            }
        }
    }
}
