package com.vampyreworld.w2t.targetft

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.vampyreworld.w2t.domain.repository.GoalRepository
import com.vampyreworld.w2t.targetft.data.repository.GoalRepositoryImpl
import com.vampyreworld.w2t.targetft.presentation.store.TargetMasterStoreFactory
import org.koin.dsl.module

val targetModule = module {
    single { DefaultStoreFactory() }
    single<GoalRepository> { GoalRepositoryImpl() }
    // Use cases are provided by domainModule
    factory { TargetMasterStoreFactory(get(), get()) }
}
