package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Goal
import kotlinx.coroutines.flow.Flow

interface GetGoalsUseCase {
    operator fun invoke(): Flow<List<Goal>>
}

interface SaveGoalUseCase {
    suspend operator fun invoke(goal: Goal)
}

interface DeleteGoalUseCase {
    suspend operator fun invoke(id: Long)
}

interface GetGoalByIdUseCase {
    operator fun invoke(id: Long): Flow<Goal?>
}
