package com.vampyreworld.w2t.targetft

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.vampyreworld.w2t.targetft.presentation.store.TargetMasterStoreFactory
import org.koin.dsl.module

val targetModule = module {
    single { DefaultStoreFactory() }
    factory { TargetMasterStoreFactory(get(), get()) }
    // Add other screen factories here
}
