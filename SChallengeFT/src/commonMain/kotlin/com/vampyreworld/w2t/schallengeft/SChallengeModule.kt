package com.vampyreworld.w2t.schallengeft

import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val sChallengeModule = module {
    factoryOf(::AddChallengeUseCase)
    factoryOf(::GetChallengesUseCase)
}
