package com.vampyreworld.w2t.di

import com.vampreworld.w2t.data.di.dataModule
import com.vampyreworld.w2t.domain.di.domainModule
import org.koin.dsl.module

val baseModule = module {
    includes(dataModule, domainModule)
}

val appModule = module {
    includes(baseModule)
}
