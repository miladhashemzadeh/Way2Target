package com.vampyreworld.w2t.targetft

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.vampyreworld.w2t.domain.repository.GoalRepository
import com.vampyreworld.w2t.targetft.data.repository.GoalRepositoryImpl
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.targetft.domain.usecase.GetGoalsUseCaseImpl
import com.vampyreworld.w2t.targetft.domain.usecase.SaveGoalUseCaseImpl
import com.vampyreworld.w2t.targetft.presentation.store.TargetMasterStoreFactory
import org.koin.dsl.module

val targetModule = module {
    single { DefaultStoreFactory() }
    single<GoalRepository> { GoalRepositoryImpl() }
    factory<GetGoalsUseCase> { GetGoalsUseCaseImpl(get()) }
    factory<SaveGoalUseCase> { SaveGoalUseCaseImpl(get()) }
    factory { TargetMasterStoreFactory(get(), get()) }
    // Add other screen factories here
}
