package com.vampyreworld.w2t.moodaddft

import com.vampyreworld.w2t.domain.usecase.AddMoodUseCase
import com.vampyreworld.w2t.domain.usecase.GetMoodHistoryUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val moodAddModule = module {
    factoryOf(::AddMoodUseCase)
    factoryOf(::GetMoodHistoryUseCase)
}
