package com.vampyreworld.w2t.solutionft

import com.vampyreworld.w2t.domain.repository.SolutionRepository
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import com.vampyreworld.w2t.domain.usecase.GetSolutionsUseCase
import com.vampyreworld.w2t.solutionft.data.repository.SolutionRepositoryImpl
import com.vampyreworld.w2t.solutionft.domain.usecase.AddSolutionUseCaseImpl
import com.vampyreworld.w2t.solutionft.domain.usecase.GetSolutionsUseCaseImpl
import org.koin.dsl.module

val solutionModule = module {
    single<SolutionRepository> { SolutionRepositoryImpl() }
    factory<GetSolutionsUseCase> { GetSolutionsUseCaseImpl(get()) }
    factory<AddSolutionUseCase> { AddSolutionUseCaseImpl(get()) }
}
