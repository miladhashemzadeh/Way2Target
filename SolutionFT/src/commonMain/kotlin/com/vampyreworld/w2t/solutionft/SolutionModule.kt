package com.vampyreworld.w2t.solutionft

import com.vampyreworld.w2t.solutionft.store.SolutionStoreFactory
import org.koin.dsl.module

val solutionModule = module {
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

