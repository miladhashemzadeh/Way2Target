package com.vampyreworld.w2t.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.vampyreworld.w2t.database.GoalEntity
import com.vampyreworld.w2t.database.W2TDatabase
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.domain.data.model.SchedulingInfo
import com.vampyreworld.w2t.domain.repository.GoalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GoalRepositoryImpl(
    private val database: W2TDatabase
) : GoalRepository {

    private val queries = database.w2TDatabaseQueries

    override fun getGoals(): Flow<List<Goal>> {
        return queries.selectAllGoals()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override fun getGoalById(id: Long): Flow<Goal?> {
        return queries.selectGoalById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toDomain() }
    }

    override suspend fun saveGoal(goal: Goal) {
        if (goal.id == 0L) {
            queries.insertGoal(
                title = goal.title,
                description = goal.description,
                upperGoalId = goal.upperGoalId,
                childGoalIds = Json.encodeToString(goal.childGoalIds),
                tier = goal.tier.name,
                isSkill = if (goal.isSkill) 1L else 0L,
                wayIds = Json.encodeToString(goal.wayIds),
                walkedWayId = goal.walkedWayId,
                priority = goal.priority.toLong(),
                status = goal.status.name,
                scheduling = goal.scheduling?.let { Json.encodeToString(it) },
                notificationEnabled = if (goal.notificationEnabled) 1L else 0L
            )
        } else {
            queries.updateGoal(
                id = goal.id,
                title = goal.title,
                description = goal.description,
                upperGoalId = goal.upperGoalId,
                childGoalIds = Json.encodeToString(goal.childGoalIds),
                tier = goal.tier.name,
                isSkill = if (goal.isSkill) 1L else 0L,
                wayIds = Json.encodeToString(goal.wayIds),
                walkedWayId = goal.walkedWayId,
                priority = goal.priority.toLong(),
                status = goal.status.name,
                scheduling = goal.scheduling?.let { Json.encodeToString(it) },
                notificationEnabled = if (goal.notificationEnabled) 1L else 0L
            )
        }
    }

    override suspend fun deleteGoal(id: Long) {
        queries.deleteGoal(id)
    }

    private fun GoalEntity.toDomain(): Goal {
        return Goal(
            id = id,
            title = title,
            description = description,
            upperGoalId = upperGoalId,
            childGoalIds = Json.decodeFromString(childGoalIds),
            tier = GoalTier.valueOf(tier),
            isSkill = isSkill == 1L,
            wayIds = Json.decodeFromString(wayIds),
            walkedWayId = walkedWayId,
            priority = priority.toInt(),
            status = GoalStatus.valueOf(status),
            scheduling = scheduling?.let { Json.decodeFromString(it) },
            notificationEnabled = notificationEnabled == 1L
        )
    }
}
