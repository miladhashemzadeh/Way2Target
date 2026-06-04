package com.vampyreworld.w2t.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.vampyreworld.w2t.database.ChallengesEntity
import com.vampyreworld.w2t.database.W2TDatabase
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.repository.ChallengeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ChallengeRepositoryImpl(
    private val database: W2TDatabase
) : ChallengeRepository {

    private val queries = database.w2TDatabaseQueries

    override fun getChallenges(goalId: Long): Flow<List<Challenges>> {
        return queries.selectAllChallenges()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.filter { it.parentGoalId == goalId }.map { it.toDomain() } }
    }

    override fun getChallengeById(id: Long): Flow<Challenges?> {
        return queries.selectChallengeById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toDomain() }
    }

    override suspend fun saveChallenge(challenge: Challenges) {
        if (challenge.id == 0L) {
            queries.insertChallenge(
                solvingBeforeGoalId = challenge.solvingBeforeGoalId,
                title = challenge.title,
                description = challenge.desc,
                cost = Json.encodeToString(challenge.cost),
                priority = challenge.priority.toLong(),
                isBarrier = if (challenge.isBarrier) 1L else 0L,
                parentGoalId = challenge.parentGoalId,
                preferredSolutions = Json.encodeToString(challenge.preferredSolutions),
                helpsToSolution = Json.encodeToString(challenge.helpsToSolution),
                failedDecisions = Json.encodeToString(challenge.failedDecisions),
                moodImpact = challenge.moodImpact.toLong(),
                prosAfterSolve = challenge.prosAfterSolve,
                consAfterFailure = challenge.consAfterFailure,
                stabilityConditions = Json.encodeToString(challenge.stabilityConditions)
            )
        } else {
            queries.updateChallenge(
                id = challenge.id,
                solvingBeforeGoalId = challenge.solvingBeforeGoalId,
                title = challenge.title,
                description = challenge.desc,
                cost = Json.encodeToString(challenge.cost),
                priority = challenge.priority.toLong(),
                isBarrier = if (challenge.isBarrier) 1L else 0L,
                parentGoalId = challenge.parentGoalId,
                preferredSolutions = Json.encodeToString(challenge.preferredSolutions),
                helpsToSolution = Json.encodeToString(challenge.helpsToSolution),
                failedDecisions = Json.encodeToString(challenge.failedDecisions),
                moodImpact = challenge.moodImpact.toLong(),
                prosAfterSolve = challenge.prosAfterSolve,
                consAfterFailure = challenge.consAfterFailure,
                stabilityConditions = Json.encodeToString(challenge.stabilityConditions)
            )
        }
    }

    override suspend fun deleteChallenge(id: Long) {
        queries.deleteChallenge(id)
    }

    private fun ChallengesEntity.toDomain(): Challenges {
        return Challenges(
            id = id,
            solvingBeforeGoalId = solvingBeforeGoalId,
            title = title,
            desc = description,
            cost = Json.decodeFromString(cost),
            priority = priority.toInt(),
            isBarrier = isBarrier == 1L,
            parentGoalId = parentGoalId,
            preferredSolutions = Json.decodeFromString(preferredSolutions),
            helpsToSolution = Json.decodeFromString(helpsToSolution),
            failedDecisions = Json.decodeFromString(failedDecisions),
            moodImpact = moodImpact.toInt(),
            prosAfterSolve = prosAfterSolve,
            consAfterFailure = consAfterFailure,
            stabilityConditions = stabilityConditions?.let { Json.decodeFromString(it) } ?: emptyList()
        )
    }
}
