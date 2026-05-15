package com.vampyreworld.w2t.di

import org.koin.core.module.Module

fun initKoinIos(additionalModules: List<Module> = emptyList()) {
    initKoin(additionalModules)
}
