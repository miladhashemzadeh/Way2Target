package com.vampyreworld.w2t.targetft

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import org.koin.dsl.module

val targetModule = module {
    single<StoreFactory> { DefaultStoreFactory() }
}
