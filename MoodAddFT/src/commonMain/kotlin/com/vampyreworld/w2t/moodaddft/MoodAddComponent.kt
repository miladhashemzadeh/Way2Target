package com.vampyreworld.w2t.moodaddft

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.usecase.AddMoodUseCase
import com.vampyreworld.w2t.domain.usecase.GetMoodHistoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface MoodAddComponent {
    val state: Value<MoodAddContract.State>
    val sideEffects: Flow<MoodAddContract.SideEffect>
    
    fun onIntent(intent: MoodAddContract.Intent)
}

class DefaultMoodAddComponent(
    componentContext: ComponentContext,
    private val addMoodUseCase: AddMoodUseCase,
    private val getMoodHistoryUseCase: GetMoodHistoryUseCase,
    private val onBack: () -> Unit
) : MoodAddComponent, ComponentContext by componentContext {

    private val _state = MutableValue(MoodAddContract.State())
    override val state: Value<MoodAddContract.State> = _state

    private val _sideEffects = MutableSharedFlow<MoodAddContract.SideEffect>()
    override val sideEffects: Flow<MoodAddContract.SideEffect> = _sideEffects.asSharedFlow()

    override fun onIntent(intent: MoodAddContract.Intent) {
        when (intent) {
            MoodAddContract.Intent.OnBackClicked -> onBack()
            is MoodAddContract.Intent.OnAddMood -> {
                // Handle add mood
            }
        }
    }
}
