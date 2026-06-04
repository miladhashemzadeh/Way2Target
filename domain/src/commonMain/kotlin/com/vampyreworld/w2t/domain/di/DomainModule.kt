package com.vampyreworld.w2t.domain.di

import com.vampyreworld.w2t.domain.usecase.prefrences.GetThemeUseCase
import com.vampyreworld.w2t.domain.usecase.prefrences.SetThemeUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetThemeUseCase(get()) }
    factory { SetThemeUseCase(get()) }
}
