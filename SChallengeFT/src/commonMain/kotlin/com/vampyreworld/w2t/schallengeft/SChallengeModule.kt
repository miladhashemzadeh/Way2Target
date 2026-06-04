package com.vampyreworld.w2t.schallengeft

import com.vampyreworld.w2t.domain.repository.ChallengeRepository
import com.vampyreworld.w2t.schallengeft.data.repository.ChallengeRepositoryImpl
import org.koin.dsl.module

val sChallengeModule = module {
    single<ChallengeRepository> { ChallengeRepositoryImpl() }
}
