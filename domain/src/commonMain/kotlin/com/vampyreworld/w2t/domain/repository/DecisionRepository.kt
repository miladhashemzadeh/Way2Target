package com.vampyreworld.w2t.domain.repository

import com.vampyreworld.w2t.domain.data.model.Decision
import kotlinx.coroutines.flow.Flow

interface DecisionRepository {
    fun getDecisions(goalId: Long?, challengeId: Long?): Flow<List<Decision>>
    fun getDecisionById(id: Long): Flow<Decision?>
    suspend fun saveDecision(decision: Decision)
    suspend fun deleteDecision(id: Long)
}
