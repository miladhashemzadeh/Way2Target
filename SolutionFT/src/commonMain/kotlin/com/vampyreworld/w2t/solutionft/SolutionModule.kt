package com.vampyreworld.w2t.solutionft

import com.vampyreworld.w2t.domain.repository.SolutionRepository
import com.vampyreworld.w2t.solutionft.data.repository.SolutionRepositoryImpl
import org.koin.dsl.module

val solutionModule = module {
    single<SolutionRepository> { SolutionRepositoryImpl() }
}
