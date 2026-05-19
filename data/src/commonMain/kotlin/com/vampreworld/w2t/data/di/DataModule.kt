package com.vampreworld.w2t.data.di

import com.vampreworld.w2t.data.repository.ChallengeRepositoryImpl
import com.vampreworld.w2t.data.repository.DecisionRepositoryImpl
import com.vampreworld.w2t.data.repository.GoalRepositoryImpl
import com.vampreworld.w2t.data.repository.SolutionRepositoryImpl
import com.vampyreworld.w2t.domain.repository.ChallengeRepository
import com.vampyreworld.w2t.domain.repository.DecisionRepository
import com.vampyreworld.w2t.domain.repository.GoalRepository
import com.vampyreworld.w2t.domain.repository.SolutionRepository
import org.koin.dsl.module

val dataModule = module {
    single<GoalRepository> { GoalRepositoryImpl() }
    single<ChallengeRepository> { ChallengeRepositoryImpl() }
    single<SolutionRepository> { SolutionRepositoryImpl() }
    single<DecisionRepository> { DecisionRepositoryImpl() }
}
