package com.vampyreworld.w2t.schallengeft

import com.vampyreworld.w2t.domain.repository.ChallengeRepository
import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengeByIdUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import com.vampyreworld.w2t.schallengeft.data.repository.ChallengeRepositoryImpl
import com.vampyreworld.w2t.schallengeft.domain.usecase.AddChallengeUseCaseImpl
import com.vampyreworld.w2t.schallengeft.domain.usecase.GetChallengeByIdUseCaseImpl
import com.vampyreworld.w2t.schallengeft.domain.usecase.GetChallengesUseCaseImpl
import org.koin.dsl.module

val sChallengeModule = module {
    single<ChallengeRepository> { ChallengeRepositoryImpl() }
    factory<GetChallengesUseCase> { GetChallengesUseCaseImpl(get()) }
    factory<AddChallengeUseCase> { AddChallengeUseCaseImpl(get()) }
    factory<GetChallengeByIdUseCase> { GetChallengeByIdUseCaseImpl(get()) }
}
