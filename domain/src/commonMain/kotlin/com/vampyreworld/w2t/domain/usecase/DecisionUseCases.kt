package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Decision
import com.vampyreworld.w2t.domain.repository.DecisionRepository
import kotlinx.coroutines.flow.Flow

class GetDecisionsUseCase(private val repository: DecisionRepository) {
    operator fun invoke(goalId: Long?, challengeId: Long?): Flow<List<Decision>> = 
        repository.getDecisions(goalId, challengeId)
}

class SaveDecisionUseCase(private val repository: DecisionRepository) {
    suspend operator fun invoke(decision: Decision) = repository.saveDecision(decision)
}
