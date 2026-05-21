package com.vampyreworld.w2t.di

import org.koin.dsl.module

val baseModule = module {
    // Shared base dependencies if any
}

val appModule = module {
    includes(baseModule)
}
