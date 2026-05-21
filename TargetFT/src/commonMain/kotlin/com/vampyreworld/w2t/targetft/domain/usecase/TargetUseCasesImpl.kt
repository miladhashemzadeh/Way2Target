package com.vampyreworld.w2t.targetft.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.repository.GoalRepository
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import kotlinx.coroutines.flow.Flow

class GetGoalsUseCaseImpl(private val repository: GoalRepository) : GetGoalsUseCase {
    override fun invoke(): Flow<List<Goal>> = repository.getGoals()
}

class SaveGoalUseCaseImpl(private val repository: GoalRepository) : SaveGoalUseCase {
    override suspend fun invoke(goal: Goal) = repository.saveGoal(goal)
}
