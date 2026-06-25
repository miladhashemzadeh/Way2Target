package com.vampyreworld.w2t.solutionft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.sharedui.arch.asValue
import com.vampyreworld.w2t.solutionft.SolutionContract
import com.vampyreworld.w2t.solutionft.store.SolutionStore
import com.vampyreworld.w2t.solutionft.store.SolutionStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DefaultSolutionComponent(
    componentContext: ComponentContext,
    storeFactory: SolutionStoreFactory,
    private val onBack: () -> Unit
) : SolutionComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    override val state: Value<SolutionContract.State> = store.asValue()

    private val _sideEffects = MutableSharedFlow<SolutionContract.SideEffect>()
    override val sideEffects: Flow<SolutionContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        scope.launch {
            store.labels.collect { label ->
                when (label) {
                    SolutionStore.Label.Back -> onBack()
                    is SolutionStore.Label.Error -> {
                        // Handle error
                    }
                }
            }
        }
        store.accept(SolutionStore.Intent.Refresh)
    }

    override fun onIntent(intent: SolutionContract.Intent) {
        when (intent) {
            SolutionContract.Intent.OnBackClicked -> onBack()
            is SolutionContract.Intent.OnSolutionTextChanged -> {
                store.accept(SolutionStore.Intent.ChangeText(intent.text))
            }
            SolutionContract.Intent.OnSaveClicked -> {
                store.accept(SolutionStore.Intent.Save)
            }
            SolutionContract.Intent.OnGetAiInsightsClicked -> {
                store.accept(SolutionStore.Intent.GetAiInsights)
            }
        }
    }
}
