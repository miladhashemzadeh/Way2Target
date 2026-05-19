package com.vampyreworld.w2t.di

import org.koin.dsl.module

val baseModule = module {
    // Core application-wide dependencies (e.g., Database, Network, Repositories)
}

val appModule = module {
    includes(baseModule)
}
