package com.vampyreworld.w2t.schallengeft

import com.vampyreworld.w2t.schallengeft.store.SChallengeStoreFactory
import org.koin.dsl.module

val sChallengeModule = module {
    factory { (goalId: Long?, challengeId: Long?) ->
        SChallengeStoreFactory(
            storeFactory = get(),
            addChallengeUseCase = get(),
            getChallengesUseCase = get(),
            getChallengeByIdUseCase = get(),
            goalId = goalId,
            challengeId = challengeId
        )
    }
}

