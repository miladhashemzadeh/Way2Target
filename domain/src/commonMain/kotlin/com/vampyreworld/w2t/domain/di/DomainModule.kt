package com.vampyreworld.w2t.domain.di

import com.vampyreworld.w2t.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    factory { GetGoalsUseCase(get()) }
    factory { SaveGoalUseCase(get()) }
    factory { GetChallengesUseCase(get()) }
    factory { AddChallengeUseCase(get()) }
    factory { GetSolutionsUseCase(get()) }
    factory { AddSolutionUseCase(get()) }
    factory { GetDecisionsUseCase(get()) }
    factory { SaveDecisionUseCase(get()) }
}
