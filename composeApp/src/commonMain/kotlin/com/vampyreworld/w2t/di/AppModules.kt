package com.vampyreworld.w2t.di

import com.vampyreworld.w2t.appraiseft.di.appraiseModule
import com.vampyreworld.w2t.decissionmakingft.decisionMakingModule
import com.vampyreworld.w2t.moodaddft.moodAddModule
import com.vampyreworld.w2t.schallengeft.sChallengeModule
import com.vampyreworld.w2t.targetft.targetModule
import com.vampyreworld.w2t.solutionft.solutionModule
import com.vampyreworld.w2t.prefrencesft.prefrencesModule
import com.vampyreworld.w2t.shomeft.sHomeModule
import org.koin.dsl.module

val featureModules = module {
    includes(
        targetModule,
        moodAddModule,
        sChallengeModule,
        decisionMakingModule,
        solutionModule,
        prefrencesModule,
        sHomeModule,
        appraiseModule
    )
}

val allAppModules = listOf(
    appModule,
    featureModules
)
