package com.vampyreworld.w2t.solutionft

import com.vampyreworld.w2t.domain.repository.SolutionRepository
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import com.vampyreworld.w2t.domain.usecase.GetSolutionsUseCase
import com.vampyreworld.w2t.solutionft.data.repository.SolutionRepositoryImpl
import com.vampyreworld.w2t.solutionft.domain.usecase.AddSolutionUseCaseImpl
import com.vampyreworld.w2t.solutionft.domain.usecase.GetSolutionsUseCaseImpl
import com.vampyreworld.w2t.solutionft.store.SolutionStoreFactory
import org.koin.dsl.module

val solutionModule = module {
    single<SolutionRepository> { SolutionRepositoryImpl() }
    
    factory<AddSolutionUseCase> { AddSolutionUseCaseImpl(get()) }
    factory<GetSolutionsUseCase> { GetSolutionsUseCaseImpl(get()) }
    
    factory { (goalId: Long?, challengeId: Long?) ->
        SolutionStoreFactory(
            storeFactory = get(),
            addSolutionUseCase = get(),
            getSolutionsUseCase = get(),
            goalId = goalId,
            challengeId = challengeId
        )
    }
}
