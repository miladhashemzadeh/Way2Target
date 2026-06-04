package com.vampreworld.w2t.data.di

import com.russhwolf.settings.Settings
import com.vampreworld.w2t.data.repository.SettingsRepositoryImpl
import com.vampyreworld.w2t.domain.repository.SettingsRepository
import org.koin.dsl.module

val dataModule = module {
    single<Settings> { Settings() }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}
