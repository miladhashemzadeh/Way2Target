package com.vampyreworld.w2t.decissionmakingft

import com.vampyreworld.w2t.decissionmakingft.data.repository.DecisionRepositoryImpl
import com.vampyreworld.w2t.decissionmakingft.domain.usecase.GetDecisionsUseCaseImpl
import com.vampyreworld.w2t.decissionmakingft.domain.usecase.SaveDecisionUseCaseImpl
import com.vampyreworld.w2t.domain.repository.DecisionRepository
import com.vampyreworld.w2t.domain.usecase.GetDecisionsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveDecisionUseCase
import org.koin.dsl.module

val decisionMakingModule = module {
    single<DecisionRepository> { DecisionRepositoryImpl() }
    factory<GetDecisionsUseCase> { GetDecisionsUseCaseImpl(get()) }
    factory<SaveDecisionUseCase> { SaveDecisionUseCaseImpl(get()) }
}
