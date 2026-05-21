package com.vampyreworld.w2t.decissionmakingft.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Decision
import com.vampyreworld.w2t.domain.repository.DecisionRepository
import com.vampyreworld.w2t.domain.usecase.GetDecisionsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveDecisionUseCase
import kotlinx.coroutines.flow.Flow

class GetDecisionsUseCaseImpl(private val repository: DecisionRepository) : GetDecisionsUseCase {
    override fun invoke(goalId: Long?, challengeId: Long?): Flow<List<Decision>> = 
        repository.getDecisions(goalId, challengeId)
}

class SaveDecisionUseCaseImpl(private val repository: DecisionRepository) : SaveDecisionUseCase {
    override suspend fun invoke(decision: Decision) = repository.saveDecision(decision)
}
