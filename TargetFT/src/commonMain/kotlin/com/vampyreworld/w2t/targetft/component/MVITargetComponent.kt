package com.vampyreworld.w2t.targetft.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.vampyreworld.w2t.core.arch.asValue
import com.vampyreworld.w2t.targetft.store.TargetStore
import com.vampyreworld.w2t.targetft.store.TargetStoreFactory
import kotlinx.coroutines.flow.Flow

class MVITargetComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val onBack: () -> Unit
) : TargetComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(storeFactory).create()
    }

     val state: Value<TargetStore.State> = store.asValue()

     val labels: Flow<TargetStore.Label> = store.labels

     fun onIntent(intent: TargetStore.Intent) {
        store.accept(intent)
    }

    override fun onBackClicked() = onBack()
}
