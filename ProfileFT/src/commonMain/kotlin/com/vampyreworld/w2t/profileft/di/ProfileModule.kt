package com.vampyreworld.w2t.profileft.di

import org.koin.dsl.module

val profileModule = module {
    // We don't necessarily need a factory for the component here since it's created manually in DefaultRootComponent
    // but we can put shared logic or store factories if needed.
}
