package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Decision
import kotlinx.coroutines.flow.Flow

interface GetDecisionsUseCase {
    operator fun invoke(goalId: Long?, challengeId: Long?): Flow<List<Decision>>
}

interface SaveDecisionUseCase {
    suspend operator fun invoke(decision: Decision)
}
