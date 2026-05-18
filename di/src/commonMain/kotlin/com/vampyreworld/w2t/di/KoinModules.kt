package com.vampyreworld.w2t.di

import org.koin.dsl.module

val baseModule = module {
    // Add common dependencies here (e.g., UseCases, Repositories from domain/data)
}

val appModule = module {
    includes(baseModule)
}
