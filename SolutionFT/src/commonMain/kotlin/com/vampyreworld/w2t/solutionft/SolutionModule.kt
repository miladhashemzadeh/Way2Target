package com.vampyreworld.w2t.solutionft

import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val solutionModule = module {
    factoryOf(::AddSolutionUseCase)
}
