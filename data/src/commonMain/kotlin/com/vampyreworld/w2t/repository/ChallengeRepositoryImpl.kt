package com.vampyreworld.w2t.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.vampyreworld.w2t.database.ChallengeEntity
import com.vampyreworld.w2t.database.W2TDatabase
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.StabilityCondition
import com.vampyreworld.w2t.domain.repository.ChallengeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*

class ChallengeRepositoryImpl(
    private val database: W2TDatabase
) : ChallengeRepository {

    private val queries = database.w2TDatabaseQueries

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun getChallenges(goalId: Long): Flow<List<Challenges>> {
        val baseFlow = if (goalId == 0L) {
            queries.selectAllChallenges()
        } else {
            queries.selectChallengesByParentGoalId(goalId)
        }.asFlow().mapToList(Dispatchers.IO)

        return baseFlow.flatMapLatest { entities ->
            if (entities.isEmpty()) return@flatMapLatest flowOf(emptyList())
            combine(entities.map { entity ->
                queries.selectStabilityConditionsByChallengeId(entity.id)
                    .asFlow()
                    .mapToList(Dispatchers.IO)
                    .map { conditions ->
                        entity.toDomain(conditions.map { it.toDomain() })
                    }
            }) { it.toList() }
        }
    }

    override fun getChallengeById(id: Long): Flow<Challenges?> {
        val challengeFlow = queries.selectChallengeById(id).asFlow().mapToOneOrNull(Dispatchers.IO)
        val conditionsFlow = queries.selectStabilityConditionsByChallengeId(id).asFlow().mapToList(Dispatchers.IO)

        return combine(challengeFlow, conditionsFlow) { challenge, conditions ->
            challenge?.toDomain(conditions.map { it.toDomain() })
        }
    }

    override suspend fun saveChallenge(challenge: Challenges) {
        database.transaction {
            if (challenge.id == 0L) {
                queries.insertChallenge(
                    title = challenge.title,
                    description = challenge.desc,
                    status = challenge.status,
                    parentGoalId = challenge.parentGoalId,
                    mustSolveBeforeGoalId = challenge.mustSolveBeforeGoalId,
                    cost = challenge.cost,
                    priority = challenge.priority,
                    isBarrier = challenge.isBarrier,
                    moodImpact = challenge.moodImpact,
                    candidateSolutionIds = challenge.candidateSolutionIds,
                    appliedSolutionIds = challenge.appliedSolutionIds,
                    failedSolutionIds = challenge.failedSolutionIds,
                    prosAfterSolveId = challenge.prosAfterSolveId,
                    consAfterFailureId = challenge.consAfterFailureId
                )
            } else {
                queries.updateChallenge(
                    id = challenge.id,
                    title = challenge.title,
                    description = challenge.desc,
                    status = challenge.status,
                    parentGoalId = challenge.parentGoalId,
                    mustSolveBeforeGoalId = challenge.mustSolveBeforeGoalId,
                    cost = challenge.cost,
                    priority = challenge.priority,
                    isBarrier = challenge.isBarrier,
                    moodImpact = challenge.moodImpact,
                    candidateSolutionIds = challenge.candidateSolutionIds,
                    appliedSolutionIds = challenge.appliedSolutionIds,
                    failedSolutionIds = challenge.failedSolutionIds,
                    prosAfterSolveId = challenge.prosAfterSolveId,
                    consAfterFailureId = challenge.consAfterFailureId
                )

                // Sync stability conditions
                queries.deleteStabilityConditionsByChallengeId(challenge.id)
                challenge.stabilityConditions.forEach { condition ->
                    queries.insertStabilityCondition(
                        challengeId = challenge.id,
                        title = condition.title,
                        description = condition.description,
                        isMaintained = condition.isMaintained
                    )
                }
            }
        }
    }

    override suspend fun deleteChallenge(id: Long) {
        database.transaction {
            queries.deleteStabilityConditionsByChallengeId(id)
            queries.deleteChallenge(id)
        }
    }

    private fun ChallengeEntity.toDomain(stabilityConditions: List<StabilityCondition>): Challenges = Challenges(
        id = id,
        title = title,
        desc = description,
        parentGoalId = parentGoalId,
        mustSolveBeforeGoalId = mustSolveBeforeGoalId,
        status = status,
        cost = cost,
        priority = priority,
        isBarrier = isBarrier,
        moodImpact = moodImpact,
        candidateSolutionIds = candidateSolutionIds,
        appliedSolutionIds = appliedSolutionIds,
        failedSolutionIds = failedSolutionIds,
        prosAfterSolveId = prosAfterSolveId,
        consAfterFailureId = consAfterFailureId,
        stabilityConditions = stabilityConditions
    )

    private fun com.vampyreworld.w2t.database.StabilityConditionEntity.toDomain(): StabilityCondition = StabilityCondition(
        id = id,
        title = title,
        description = description,
        isMaintained = isMaintained
    )
}
