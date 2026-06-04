package com.vampyreworld.w2t.di

import com.vampyreworld.w2t.database.*
import com.vampyreworld.w2t.repository.*
import com.vampyreworld.w2t.domain.repository.*
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataModule: Module

val dataModule = module {
    includes(platformDataModule)
    single {
        val driver = get<DatabaseDriverFactory>().createDriver()
        W2TDatabase(
            driver = driver
        )
    }

    single<GoalRepository> { GoalRepositoryImpl(get()) }
    single<DecisionRepository> { DecisionRepositoryImpl(get()) }
    single<SolutionRepository> { SolutionRepositoryImpl(get()) }
    single<ChallengeRepository> { ChallengeRepositoryImpl(get()) }
    single<UserMoodRepository> { UserMoodRepositoryImpl(get()) }
}
