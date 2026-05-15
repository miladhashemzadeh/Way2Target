package com.vampyreworld.w2t.di

import com.vampyreworld.w2t.domain.usecase.*
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatformTools

val useCaseModule = module {
    factoryOf(::GetGoalsUseCase)
    factoryOf(::SaveGoalUseCase)
    factoryOf(::AddMoodUseCase)
    factoryOf(::GetMoodHistoryUseCase)
    factoryOf(::AddChallengeUseCase)
    factoryOf(::GetChallengesUseCase)
    factoryOf(::MakeDecisionUseCase)
    factoryOf(::AddSolutionUseCase)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        startKoin {
            appDeclaration()
            modules(useCaseModule)
        }
    }
}
