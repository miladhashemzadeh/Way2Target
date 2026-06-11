package com.vampyreworld.w2t.appraiseft.di

import com.vampyreworld.w2t.appraiseft.store.AppraiseStoreFactory
import org.koin.dsl.module

val appraiseModule = module {
    factory { (targetId: Long?, challengeId: Long?) ->
        AppraiseStoreFactory(
            storeFactory = get(),
            targetId = targetId,
            challengeId = challengeId
        )
    }
}
