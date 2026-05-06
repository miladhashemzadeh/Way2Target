package com.vampyreworld.w2t.domain.data.model

data class Goal(
    val id: Long,
    val upperGoalId: Long?,
    val childGoalIds: List<Long>,
    val tier: GoalTier,
    val isSkill: Boolean,
    val wayIds: List<Long>,
    val walkedWayId: Long?,
)


enum class GoalTier {
    MASTER,
    MILESTONE,
    ACTION
}
