package com.vampyreworld.w2t.targetft

import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val targetModule = module {
    factoryOf(::GetGoalsUseCase)
    factoryOf(::SaveGoalUseCase)
}
