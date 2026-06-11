package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * An obstacle on the path toward a [Goal].
 *
 * Implements [WayNode] so it can appear as a step inside a [Way].
 *
 * @property id                   unique ID
 * @property title                short name of the challenge (e.g. "Language barrier")
 * @property desc                 detailed description of the challenge
 * @property parentGoalId         the [Goal] this challenge belongs to
 * @property mustSolveBeforeGoalId if non-null, this challenge must be resolved before
 *                                 that goal can begin (replaces [solvingBeforeGoalId])
 * @property cost                 estimated effort to overcome this challenge
 * @property priority             0–100 urgency/importance; consistent with other priority fields
 * @property isBarrier            true = hard blocker; false = friction/headwind only
 * @property moodImpact           signed int: positive = resolving this improves mood;
 *                                negative = it drains mood even while active
 * @property candidateSolutionIds IDs of [Solution]s that could resolve this challenge
 * @property appliedSolutionIds   IDs of [Solution]s currently being applied
 * @property failedSolutionIds    IDs of [Solution]s that were tried and didn't work
 * @property prosAfterSolveId     ID of a node (e.g. a new Goal) that unlocks on success
 * @property consAfterFailureId   ID of a node that represents the consequence of failure
 * @property stabilityConditions  conditions that must stay true to keep this challenge resolved
 */
@Serializable
data class Challenges(
    val id: Long,
    val title: String,
    val desc: String = "",
    val parentGoalId: Long?,
    val mustSolveBeforeGoalId: Long? = null,
    val cost: Cost,
    val priority: Int,
    val status: GoalStatus = GoalStatus.ACTIVE,
    val isBarrier: Boolean,
    val moodImpact: Int = 0,
    val candidateSolutionIds: List<Long> = emptyList(),
    val appliedSolutionIds: List<Long> = emptyList(),
    val failedSolutionIds: List<Long> = emptyList(),
    val prosAfterSolveId: Long? = null,
    val consAfterFailureId: Long? = null,
    val stabilityConditions: List<StabilityCondition> = emptyList(),
) : WayNode {

    /**
     * Returns a float vector for use in ML feature pipelines.
     * Layout: [energyCost, timeCost, moneyCost, priority, isBarrier]
     */
    fun toNeuralInputVector(): FloatArray {
        return cost.toNeuralInputVector() + floatArrayOf(
            priority / 100f,
            if (isBarrier) 1.0f else 0.0f,
        )
    }
}
