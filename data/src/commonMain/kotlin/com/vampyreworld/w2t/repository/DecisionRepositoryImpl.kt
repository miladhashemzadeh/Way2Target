package com.vampyreworld.w2t.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.vampyreworld.w2t.database.DecisionEntity
import com.vampyreworld.w2t.database.W2TDatabase
import com.vampyreworld.w2t.domain.data.model.Decision
import com.vampyreworld.w2t.domain.repository.DecisionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DecisionRepositoryImpl(
    private val database: W2TDatabase
) : DecisionRepository {

    private val queries = database.w2TDatabaseQueries

    override fun getDecisions(goalId: Long?, challengeId: Long?): Flow<List<Decision>> {
        return queries.selectAllDecisions()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }
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
                context = Json.encodeToString(decision.context),
                title = decision.title,
                description = decision.desc,
                cost = Json.encodeToString(decision.cost),
                userRate = decision.userRate.toLong(),
                aiScore = decision.aiScore.toLong()
            )
        } else {
            queries.updateDecision(
                id = decision.id,
                context = Json.encodeToString(decision.context),
                title = decision.title,
                description = decision.desc,
                cost = Json.encodeToString(decision.cost),
                userRate = decision.userRate.toLong(),
                aiScore = decision.aiScore.toLong()
            )
        }
    }

    override suspend fun deleteDecision(id: Long) {
        queries.deleteDecision(id)
    }

    private fun DecisionEntity.toDomain(): Decision {
        return Decision(
            id = id,
            context = Json.decodeFromString(context),
            title = title,
            desc = description,
            cost = Json.decodeFromString(cost),
            userRate = userRate.toInt(),
            aiScore = aiScore.toInt()
        )
    }
}
