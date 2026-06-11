package com.vampyreworld.w2t.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.vampyreworld.w2t.database.DecisionEntity
import com.vampyreworld.w2t.database.W2TDatabase
import com.vampyreworld.w2t.domain.data.model.Decision
import com.vampyreworld.w2t.domain.data.model.DecisionContext
import com.vampyreworld.w2t.domain.repository.DecisionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DecisionRepositoryImpl(
    private val database: W2TDatabase
) : DecisionRepository {

    private val queries = database.w2TDatabaseQueries

    override fun getDecisions(goalId: Long?, challengeId: Long?): Flow<List<Decision>> {
        return queries.selectAllDecisions()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities ->
                val all = entities.map { it.toDomain() }
                if (goalId != null) {
                    all.filter { it.context is DecisionContext.ForGoal && (it.context as DecisionContext.ForGoal).goalId == goalId }
                } else if (challengeId != null) {
                    all.filter { it.context is DecisionContext.ForSolution && (it.context as DecisionContext.ForSolution).solutionId == challengeId }
                } else {
                    all
                }
            }
    }

    override fun getDecisionById(id: Long): Flow<Decision?> {
        return queries.selectDecisionById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toDomain() }
    }

    override suspend fun saveDecision(decision: Decision) {
        if (decision.id == 0L) {
            queries.insertDecision(
                context = decision.context,
                title = decision.title,
                description = decision.desc,
                cost = decision.cost,
                userRate = decision.userRate,
                aiScore = decision.aiScore
            )
        } else {
            queries.updateDecision(
                id = decision.id,
                context = decision.context,
                title = decision.title,
                description = decision.desc,
                cost = decision.cost,
                userRate = decision.userRate,
                aiScore = decision.aiScore
            )
        }
    }

    override suspend fun deleteDecision(id: Long) {
        queries.deleteDecision(id)
    }

    private fun DecisionEntity.toDomain(): Decision = Decision(
        id = id,
        context = context,
        title = title,
        desc = description,
        cost = cost,
        userRate = userRate,
        aiScore = aiScore
    )
}
