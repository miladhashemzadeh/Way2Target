package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * Sealed hierarchy for goals. Each tier has its own distinct shape — no more
 * optional fields that only apply to some tiers.
 *
 * Hierarchy:
 * ```
 * MasterGoal  ("Migrate to Germany")
 *   └─ MilestoneGoal  ("Learn German B1")
 *         └─ ActionGoal  ("Study 30 min/day for 6 weeks")
 * ```
 *
 * Use [GoalStatus] to represent lifecycle state across all tiers.
 * Use [GoalRelation] to represent cross-goal dependencies (blocking, helping, etc.).
 */
@Serializable
sealed interface Goal {
    val id: Long
    val title: String
    val description: String
    val priority: Int       // 0–100; higher = more important
    val status: GoalStatus
    val tier: GoalTier
    val upperGoalId: Long?

    fun withStatus(status: GoalStatus): Goal
}

// ─────────────────────────────────────────────────────────────────────────────
// Tier 1 — Master Goal
// ─────────────────────────────────────────────────────────────────────────────

/**
 * The top-level life target (e.g. "Migrate to Germany", "Start my own business").
 *
 * A master goal is decomposed into [MilestoneGoal]s tracked via [milestoneIds].
 *
 * @property isLifeGoal     true if this is a long-horizon (~years) life ambition
 * @property milestoneIds   ordered list of [MilestoneGoal] IDs that fulfil this goal
 * @property walkedMilestoneId the milestone currently in progress; null if not started
 */
@Serializable
data class MasterGoal(
    override val id: Long,
    override val title: String,
    override val description: String = "",
    override val priority: Int = 0,
    override val status: GoalStatus = GoalStatus.ACTIVE,
    val isLifeGoal: Boolean = false,
    val milestoneIds: List<Long> = emptyList(),
    val walkedMilestoneId: Long? = null,
) : Goal {
    override val tier: GoalTier = GoalTier.MASTER
    override val upperGoalId: Long? = null
    override fun withStatus(status: GoalStatus): Goal = copy(status = status)
}

// ─────────────────────────────────────────────────────────────────────────────
// Tier 2 — Milestone Goal
// ─────────────────────────────────────────────────────────────────────────────

/**
 * A significant sub-target on the path to a [MasterGoal]
 * (e.g. "Reach German B1", "Save €10 000").
 *
 * A milestone is fulfilled by completing its [actionIds].
 *
 * @property masterGoalId   the parent [MasterGoal] this milestone belongs to
 * @property actionIds      ordered list of [ActionGoal] IDs that fulfil this milestone
 * @property walkedActionId the action currently in progress; null if not started
 * @property isSkill        true if completing this milestone confers a measurable skill
 * @property wayIds         [Way] IDs representing possible paths through this milestone
 * @property walkedWayId    which [Way] is currently being walked
 */
@Serializable
data class MilestoneGoal(
    override val id: Long,
    override val title: String,
    override val description: String = "",
    override val priority: Int = 0,
    override val status: GoalStatus = GoalStatus.ACTIVE,
    val masterGoalId: Long,
    val actionIds: List<Long> = emptyList(),
    val walkedActionId: Long? = null,
    val isSkill: Boolean = false,
    val wayIds: List<Long> = emptyList(),
    val walkedWayId: Long? = null,
) : Goal {
    override val tier: GoalTier = GoalTier.MILESTONE
    override val upperGoalId: Long? get() = masterGoalId
    override fun withStatus(status: GoalStatus): Goal = copy(status = status)
}

// ─────────────────────────────────────────────────────────────────────────────
// Tier 3 — Action Goal
// ─────────────────────────────────────────────────────────────────────────────

/**
 * A concrete, schedulable action (e.g. "Study 30 min/day for 6 weeks",
 * "Call the embassy on Tuesday").
 *
 * This is the only tier that carries scheduling, cost, and notification data —
 * because it's the only tier the user *does* rather than *plans*.
 *
 * @property milestoneGoalId    the parent [MilestoneGoal]
 * @property schedule           *how* and *when* to perform this action; see [ActionSchedule]
 * @property cost               estimated effort, time, and money
 * @property notificationEnabled whether the user wants reminders for this action
 * @property completionCriteria plain-text description of what "done" looks like;
 *                              e.g. "Pass Goethe B1 certificate exam"
 */
@Serializable
data class ActionGoal(
    override val id: Long,
    override val title: String,
    override val description: String = "",
    override val priority: Int = 0,
    override val status: GoalStatus = GoalStatus.ACTIVE,
    val milestoneGoalId: Long,
    val schedule: ActionSchedule? = null,
    val cost: Cost? = null,
    val notificationEnabled: Boolean = false,
    val completionCriteria: String = "",
) : Goal {
    override val tier: GoalTier = GoalTier.ACTION
    override val upperGoalId: Long? get() = milestoneGoalId
    override fun withStatus(status: GoalStatus): Goal = copy(status = status)
}

// ─────────────────────────────────────────────────────────────────────────────
// Shared enums
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Lifecycle state shared by all [Goal] tiers.
 *
 * Note: [Way.status] is derived from its owning goal's status —
 * do not update them independently.
 */
@Serializable
enum class GoalStatus {
    ACTIVE,
    COMPLETED,
    CANCELLED,
    ON_HOLD
}

@Serializable
enum class GoalTier {
    MASTER,
    MILESTONE,
    ACTION
}
