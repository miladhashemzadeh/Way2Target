package com.vampyreworld.w2t.decissionmakingft

import com.vampyreworld.w2t.decissionmakingft.data.repository.DecisionRepositoryImpl
import com.vampyreworld.w2t.domain.repository.DecisionRepository
import org.koin.dsl.module

val decisionMakingModule = module {
    single<DecisionRepository> { DecisionRepositoryImpl() }
}
