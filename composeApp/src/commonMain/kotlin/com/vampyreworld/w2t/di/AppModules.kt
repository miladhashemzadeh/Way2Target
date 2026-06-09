package com.vampyreworld.w2t.di

import com.vampyreworld.navigation.AppRouterImpl
import com.vampyreworld.navigation.Router
import com.vampreworld.w2t.data.di.dataModule as settingsModule
import com.vampyreworld.w2t.di.dataModule as databaseModule
import com.vampyreworld.w2t.domain.di.domainModule
import com.vampyreworld.w2t.appraiseft.di.appraiseModule
import com.vampyreworld.w2t.decissionmakingft.decisionMakingModule
import com.vampyreworld.w2t.moodaddft.moodAddModule
import com.vampyreworld.w2t.schallengeft.sChallengeModule
import com.vampyreworld.w2t.targetft.targetModule
import com.vampyreworld.w2t.solutionft.solutionModule
import com.vampyreworld.w2t.prefrencesft.prefrencesModule
import com.vampyreworld.w2t.shomeft.sHomeModule
import org.koin.dsl.module

val navigationModule = module {
    single<Router> { AppRouterImpl() }
}

val featureModules = module {
    includes(
        navigationModule,
        settingsModule,
        databaseModule,
        domainModule,
        targetModule,
        moodAddModule,
        sChallengeModule,
        decisionMakingModule,
        solutionModule,
        prefrencesModule,
        sHomeModule,
        appraiseModule,
    )
}


val allAppModules = listOf(
    appModule,
    featureModules
)
