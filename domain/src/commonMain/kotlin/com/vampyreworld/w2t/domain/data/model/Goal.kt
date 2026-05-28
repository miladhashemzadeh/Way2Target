package com.vampyreworld.w2t.domain.data.model

data class Goal(
    val id: Long,
    val title: String = "",
    val description: String = "",
    val upperGoalId: Long? = null,
    val childGoalIds: List<Long> = emptyList(),
    val tier: GoalTier,
    val isSkill: Boolean = false,
    val wayIds: List<Long> = emptyList(),
    val walkedWayId: Long? = null,
    val priority: Int = 0, // 0-100
    val status: GoalStatus = GoalStatus.ACTIVE,
    val scheduling: SchedulingInfo? = null,
    val notificationEnabled: Boolean = false
)

enum class GoalTier {
    MASTER,    // Main Target
    MILESTONE, // Path Target
    ACTION     // Action Goal
}

enum class GoalStatus {
    ACTIVE,
    COMPLETED,
    CANCELLED,
    ON_HOLD
}

data class SchedulingInfo(
    val startTime: Long? = null, // Epoch millis
    val endTime: Long? = null,
    val repeatInterval: Long? = null // e.g., daily, weekly
)
