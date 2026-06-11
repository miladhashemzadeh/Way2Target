package com.vampyreworld.w2t.targetft

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.vampyreworld.w2t.targetft.presentation.store.TargetMasterStoreFactory
import org.koin.dsl.module

val targetModule = module {
    single<StoreFactory> { DefaultStoreFactory() }
    
    // Legacy support
    factory { TargetMasterStoreFactory(get(), get(), get()) }
}
