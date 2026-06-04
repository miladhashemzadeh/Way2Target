package com.vampyreworld.w2t.di

import com.vampyreworld.w2t.database.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataModule: Module = module {
    single { DatabaseDriverFactory(get()) }
}
