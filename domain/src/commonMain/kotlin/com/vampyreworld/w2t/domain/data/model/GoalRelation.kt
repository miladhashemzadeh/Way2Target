package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * Represents a directed relationship between two goals.
 *
 * Previously these links were scattered as ad-hoc ID fields:
 * `parentGoalId`, `upperGoalId`, `solvingBeforeGoalId`, etc.
 * Centralising them here makes the goal graph traversable and queryable.
 *
 * Example relationships:
 * ```
 * GoalRelation(fromGoalId=42, toGoalId=17, type=BLOCKS)
 * // "Goal 42 (get a visa) blocks Goal 17 (buy a flat)"
 *
 * GoalRelation(fromGoalId=5, toGoalId=9, type=ACCELERATES)
 * // "Goal 5 (learn German) accelerates Goal 9 (find a job)"
 * ```
 *
 * @property id          unique relation ID
 * @property fromGoalId  the source goal in the directed edge
 * @property toGoalId    the target goal in the directed edge
 * @property type        the semantic meaning of the edge; see [RelationType]
 * @property note        optional human-readable explanation of why this relation exists
 * @property strength    0–100 how strongly [fromGoalId] affects [toGoalId]; -1 = unknown
 */
@Serializable
data class GoalRelation(
    val id: Long,
    val fromGoalId: Long,
    val toGoalId: Long,
    val type: RelationType,
    val note: String = "",
    val strength: Int = -1,
)

/**
 * Semantic types for a [GoalRelation] edge.
 *
 * All relations are *directed*: [GoalRelation.fromGoalId] → [GoalRelation.toGoalId].
 */
@Serializable
enum class RelationType {
    /** fromGoal must be completed before toGoal can begin. */
    BLOCKS,

    /** Completing fromGoal makes toGoal easier or faster. */
    ACCELERATES,

    /** Completing fromGoal contributes partial progress toward toGoal. */
    CONTRIBUTES_TO,

    /** fromGoal and toGoal share resources; progressing one slows the other. */
    COMPETES_WITH,

    /** fromGoal directly enables toGoal to exist (e.g. parent–child). */
    ENABLES,

    /** fromGoal success increases the risk of toGoal failure. */
    CONFLICTS_WITH,
}
