package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow

class GetGoalsUseCase(private val repository: GoalRepository) {
    operator fun invoke(): Flow<List<Goal>> = repository.getGoals()
}

class SaveGoalUseCase(private val repository: GoalRepository) {
    suspend operator fun invoke(goal: Goal) = repository.saveGoal(goal)
}
