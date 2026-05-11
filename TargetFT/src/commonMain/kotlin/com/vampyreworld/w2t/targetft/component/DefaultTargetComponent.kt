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

class DefaultTargetComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory
) : TargetComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        TargetStoreFactory(storeFactory).create()
    }

    override val state: Value<TargetStore.State> = store.asValue()

    override val labels: Flow<TargetStore.Label> = store.labels

    override fun onIntent(intent: TargetStore.Intent) {
        store.accept(intent)
    }
}
