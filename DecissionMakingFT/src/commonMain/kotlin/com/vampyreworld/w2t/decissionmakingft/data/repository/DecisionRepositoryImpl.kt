package com.vampyreworld.w2t.decissionmakingft.data.repository

import com.vampyreworld.w2t.domain.data.model.Decision
import com.vampyreworld.w2t.domain.repository.DecisionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class DecisionRepositoryImpl : DecisionRepository {
    private val decisions = MutableStateFlow<List<Decision>>(emptyList())

    override fun getDecisions(goalId: Long?, challengeId: Long?): Flow<List<Decision>> = decisions

    override fun getDecisionById(id: Long): Flow<Decision?> = 
        decisions.map { list -> list.find { it.id == id } }

    override suspend fun saveDecision(decision: Decision) {
        decisions.update { list ->
            val index = list.indexOfFirst { it.id == decision.id }
            if (index >= 0) {
                list.toMutableList().also { it[index] = decision }
            } else {
                list + decision
            }
        }
    }

    override suspend fun deleteDecision(id: Long) {
        decisions.update { list -> list.filter { it.id != id } }
    }
}
