package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Goal

class GetGoalsUseCase {
    operator fun invoke(): List<Goal> = emptyList() // TODO: Implement with repository
}

class SaveGoalUseCase {
    operator fun invoke(goal: Goal) { /* TODO: Implement with repository */ }
}
