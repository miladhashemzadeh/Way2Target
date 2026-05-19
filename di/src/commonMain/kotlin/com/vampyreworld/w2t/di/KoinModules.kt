package com.vampyreworld.w2t.di

import com.vampyreworld.w2t.domain.usecase.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

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

val baseModule = module {
    includes(useCaseModule)
}

val appModule = module {
    includes(baseModule)
}
