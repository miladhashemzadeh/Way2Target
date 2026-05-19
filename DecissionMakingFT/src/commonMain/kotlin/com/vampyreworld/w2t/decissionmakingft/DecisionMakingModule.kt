package com.vampyreworld.w2t.decissionmakingft

import com.vampyreworld.w2t.domain.usecase.MakeDecisionUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val decisionMakingModule = module {
    factoryOf(::MakeDecisionUseCase)
}
