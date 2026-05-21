package com.vampyreworld.w2t.domain.repository

import com.vampyreworld.w2t.domain.data.model.Goal
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    fun getGoals(): Flow<List<Goal>>
    fun getGoalById(id: Long): Flow<Goal?>
    suspend fun saveGoal(goal: Goal)
    suspend fun deleteGoal(id: Long)
}
