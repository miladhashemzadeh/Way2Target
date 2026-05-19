package com.vampyreworld.w2t.prefrencesft

import com.vampyreworld.w2t.domain.usecase.prefrences.GetPreferencesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val prefrencesModule = module {
    factoryOf(::GetPreferencesUseCase)
}
