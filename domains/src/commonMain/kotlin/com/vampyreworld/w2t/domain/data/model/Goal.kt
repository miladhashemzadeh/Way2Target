package com.vampyreworld.w2t.domain.data.model

data class Goal(
    val id:Long,
    val upperGoalId:Long?,
    val lowerGoalId:Long?,
    val priority: GoalPriority,
    val isSkill: Boolean,
    val treeOfWay: Map<Long, String>?,
    val walkedWay: List<WayNode>?,
)

enum class GoalPriority {
    MASTER,MILESTONE,ACTION
}