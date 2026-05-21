package com.vampyreworld.w2t.targetft.data.repository

import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.repository.GoalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class GoalRepositoryImpl : GoalRepository {
    private val goals = MutableStateFlow<List<Goal>>(emptyList())

    override fun getGoals(): Flow<List<Goal>> = goals

    override fun getGoalById(id: Long): Flow<Goal?> = 
        goals.map { list -> list.find { it.id == id } }

    override suspend fun saveGoal(goal: Goal) {
        goals.update { list ->
            val index = list.indexOfFirst { it.id == goal.id }
            if (index >= 0) {
                list.toMutableList().also { it[index] = goal }
            } else {
                list + goal
            }
        }
    }

    override suspend fun deleteGoal(id: Long) {
        goals.update { list -> list.filter { it.id != id } }
    }
}
