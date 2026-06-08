package com.vampyreworld.w2t.domain.usecase.target.impl

import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.repository.GoalRepository
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalByIdUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import kotlinx.coroutines.flow.Flow

class GetGoalsUseCaseImpl(
    private val repository: GoalRepository
) : GetGoalsUseCase {
    override fun invoke(): Flow<List<Goal>> {
        return repository.getGoals()
    }
}

class SaveGoalUseCaseImpl(
    private val repository: GoalRepository
) : SaveGoalUseCase {
    override suspend fun invoke(goal: Goal) {
        repository.saveGoal(goal)
    }
}

class DeleteGoalUseCaseImpl(
    private val repository: GoalRepository
) : DeleteGoalUseCase {
    override suspend fun invoke(id: Long) {
        repository.deleteGoal(id)
    }
}

class GetGoalByIdUseCaseImpl(
    private val repository: GoalRepository
) : GetGoalByIdUseCase {
    override fun invoke(id: Long): Flow<Goal?> {
        return repository.getGoalById(id)
    }
}
