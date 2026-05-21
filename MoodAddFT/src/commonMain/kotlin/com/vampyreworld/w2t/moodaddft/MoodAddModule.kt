package com.vampyreworld.w2t.moodaddft

import com.vampyreworld.w2t.domain.usecase.AddMoodUseCase
import com.vampyreworld.w2t.domain.usecase.GetMoodHistoryUseCase
import com.vampyreworld.w2t.moodaddft.domain.usecase.AddMoodUseCaseImpl
import com.vampyreworld.w2t.moodaddft.domain.usecase.GetMoodHistoryUseCaseImpl
import org.koin.dsl.module

val moodAddModule = module {
    factory<AddMoodUseCase> { AddMoodUseCaseImpl() }
    factory<GetMoodHistoryUseCase> { GetMoodHistoryUseCaseImpl() }
}
