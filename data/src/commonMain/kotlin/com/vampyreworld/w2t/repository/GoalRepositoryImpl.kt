package com.vampyreworld.w2t.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.vampyreworld.w2t.database.W2TDatabase
import com.vampyreworld.w2t.domain.data.model.*
import com.vampyreworld.w2t.domain.repository.GoalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class GoalRepositoryImpl(
    private val database: W2TDatabase
) : GoalRepository {

    private val queries = database.w2TDatabaseQueries

    override fun getGoals(): Flow<List<Goal>> {
        val masters = queries.selectAllMasterGoals().asFlow().mapToList(Dispatchers.IO)
        val milestones = queries.selectAllMilestoneGoals().asFlow().mapToList(Dispatchers.IO)
        val actions = queries.selectAllActionGoals().asFlow().mapToList(Dispatchers.IO)

        return combine(masters, milestones, actions) { m, mi, a ->
            m.map { it.toDomain() } + mi.map { it.toDomain() } + a.map { it.toDomain() }
        }
    }

    override fun getGoalById(id: Long): Flow<Goal?> {
        val master = queries.selectMasterGoalById(id).asFlow().mapToOneOrNull(Dispatchers.IO)
        val milestone = queries.selectMilestoneGoalById(id).asFlow().mapToOneOrNull(Dispatchers.IO)
        val action = queries.selectActionGoalById(id).asFlow().mapToOneOrNull(Dispatchers.IO)

        return combine(master, milestone, action) { m, mi, a ->
            m?.toDomain() ?: mi?.toDomain() ?: a?.toDomain()
        }
    }

    override suspend fun saveGoal(goal: Goal) {
        when (goal) {
            is MasterGoal -> {
                if (goal.id == 0L) {
                    queries.insertMasterGoal(
                        title = goal.title,
                        description = goal.description,
                        priority = goal.priority,
                        status = goal.status,
                        isLifeGoal = goal.isLifeGoal,
                        milestoneIds = goal.milestoneIds,
                        walkedMilestoneId = goal.walkedMilestoneId
                    )
                } else {
                    queries.updateMasterGoal(
                        id = goal.id,
                        title = goal.title,
                        description = goal.description,
                        priority = goal.priority,
                        status = goal.status,
                        isLifeGoal = goal.isLifeGoal,
                        milestoneIds = goal.milestoneIds,
                        walkedMilestoneId = goal.walkedMilestoneId
                    )
                }
            }
            is MilestoneGoal -> {
                if (goal.id == 0L) {
                    queries.insertMilestoneGoal(
                        title = goal.title,
                        description = goal.description,
                        priority = goal.priority,
                        status = goal.status,
                        masterGoalId = goal.masterGoalId,
                        actionIds = goal.actionIds,
                        walkedActionId = goal.walkedActionId,
                        isSkill = goal.isSkill,
                        wayIds = goal.wayIds,
                        walkedWayId = goal.walkedWayId
                    )
                } else {
                    queries.updateMilestoneGoal(
                        id = goal.id,
                        title = goal.title,
                        description = goal.description,
                        priority = goal.priority,
                        status = goal.status,
                        masterGoalId = goal.masterGoalId,
                        actionIds = goal.actionIds,
                        walkedActionId = goal.walkedActionId,
                        isSkill = goal.isSkill,
                        wayIds = goal.wayIds,
                        walkedWayId = goal.walkedWayId
                    )
                }
            }
            is ActionGoal -> {
                if (goal.id == 0L) {
                    queries.insertActionGoal(
                        title = goal.title,
                        description = goal.description,
                        priority = goal.priority,
                        status = goal.status,
                        milestoneGoalId = goal.milestoneGoalId,
                        schedule = goal.schedule,
                        cost = goal.cost,
                        notificationEnabled = goal.notificationEnabled,
                        completionCriteria = goal.completionCriteria
                    )
                } else {
                    queries.updateActionGoal(
                        id = goal.id,
                        title = goal.title,
                        description = goal.description,
                        priority = goal.priority,
                        status = goal.status,
                        milestoneGoalId = goal.milestoneGoalId,
                        schedule = goal.schedule,
                        cost = goal.cost,
                        notificationEnabled = goal.notificationEnabled,
                        completionCriteria = goal.completionCriteria
                    )
                }
            }
        }
    }

    override suspend fun deleteGoal(id: Long) {
        queries.deleteMasterGoal(id)
        queries.deleteMilestoneGoal(id)
        queries.deleteActionGoal(id)
    }

    private fun com.vampyreworld.w2t.database.MasterGoalEntity.toDomain(): MasterGoal = MasterGoal(
        id = id,
        title = title,
        description = description,
        priority = priority,
        status = status,
        isLifeGoal = isLifeGoal,
        milestoneIds = milestoneIds,
        walkedMilestoneId = walkedMilestoneId
    )

    private fun com.vampyreworld.w2t.database.MilestoneGoalEntity.toDomain(): MilestoneGoal = MilestoneGoal(
        id = id,
        title = title,
        description = description,
        priority = priority,
        status = status,
        masterGoalId = masterGoalId,
        actionIds = actionIds,
        walkedActionId = walkedActionId,
        isSkill = isSkill,
        wayIds = wayIds,
        walkedWayId = walkedWayId
    )

    private fun com.vampyreworld.w2t.database.ActionGoalEntity.toDomain(): ActionGoal = ActionGoal(
        id = id,
        title = title,
        description = description,
        priority = priority,
        status = status,
        milestoneGoalId = milestoneGoalId,
        schedule = schedule,
        cost = cost,
        notificationEnabled = notificationEnabled,
        completionCriteria = completionCriteria
    )
}
