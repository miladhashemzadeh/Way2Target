package com.vampyreworld.w2t.appraiseft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.vampyreworld.w2t.appraiseft.AppraiseContract
import com.vampyreworld.w2t.appraiseft.store.AppraiseStore
import com.vampyreworld.w2t.appraiseft.store.AppraiseStoreFactory
import com.vampyreworld.w2t.core.utils.componentScope
import com.vampyreworld.w2t.sharedui.arch.asValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DefaultAppraiseComponent(
    componentContext: ComponentContext,
    storeFactory: AppraiseStoreFactory,
    private val onBack: () -> Unit
) : AppraiseComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }
    private val scope = componentScope()

    override val state: Value<AppraiseContract.State> = store.asValue()

    private val _sideEffects = MutableSharedFlow<AppraiseContract.SideEffect>()
    override val sideEffects: Flow<AppraiseContract.SideEffect> = _sideEffects.asSharedFlow()

    init {
        scope.launch {
            store.labels.collect { label ->
                when (label) {
                    AppraiseStore.Label.Back -> onBack()
                }
            }
        }
    }

    override fun onIntent(intent: AppraiseContract.Intent) {
        when (intent) {
            AppraiseContract.Intent.OnBackClicked -> onBack()
            AppraiseContract.Intent.OnAppraiseClicked -> store.accept(AppraiseStore.Intent.Appraise)
            is AppraiseContract.Intent.OnChallengeStatusChanged -> store.accept(AppraiseStore.Intent.ChangeChallengeStatus(intent.status))
            is AppraiseContract.Intent.OnSolutionSelected -> store.accept(AppraiseStore.Intent.SelectSolution(intent.solutionId))
            is AppraiseContract.Intent.OnReflectionChanged -> store.accept(AppraiseStore.Intent.ChangeReflection(intent.reflection))
            AppraiseContract.Intent.OnUpdateChallengeClicked -> store.accept(AppraiseStore.Intent.UpdateChallenge)
            AppraiseContract.Intent.OnCompleteGoalClicked -> store.accept(AppraiseStore.Intent.CompleteGoal)
            AppraiseContract.Intent.OnArchiveGoalClicked -> store.accept(AppraiseStore.Intent.ArchiveGoal)
        }
    }
}
