package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * A concrete strategy applied to resolve a [Challenges].
 *
 * Implements [WayNode] so solutions can appear alongside challenges
 * as steps in a [Way], giving a full before/after picture of the path.
 *
 * @property id          unique ID
 * @property title       short name (e.g. "Hire a relocation consultant")
 * @property desc        detailed description of how to apply this solution
 * @property solutionType strategic category; see [SolutionType]
 * @property cost        estimated effort, time and money to execute
 * @property aidStrength 0–100: how much this solution advances the parent goal;
 *                       100 = fully resolves it, 0 = no direct impact
 * @property result      current execution state; see [SolutionResult]
 */
@Serializable
data class Solution(
    val id: Long,
    val title: String,
    val desc: String,
    val solutionType: SolutionType,
    val cost: Cost,
    val aidStrength: Int,
    val result: SolutionResult = SolutionResult.UNKNOWN,
) : WayNode

/**
 * The broad strategic archetype of a [Solution].
 *
 * Helps the recommendation engine surface relevant past solutions
 * and helps users reflect on which strategy they're defaulting to.
 */
@Serializable
enum class SolutionType {
    /** Sidestep the challenge entirely. */
    AVOIDANCE,
    /** Face the challenge head-on. */
    DIRECT_CONFRONTATION,
    /** Change the underlying structure that causes the challenge. */
    EDIT_STRUCTURAL,
    /** Break the challenge into smaller, manageable pieces. */
    DIVIDING_AND_CONQUER,
    /** Replace the blocked path with an alternative that achieves the same goal. */
    SUBSTITUTION,
    /** Hand off responsibility to someone better placed to handle it. */
    DELEGATE,
    /** Resolve via preparation, scheduling, and foresight. */
    PLANNING,
    /** Manage emotional/psychological friction rather than the external obstacle. */
    EMOTIONAL_REGULATION,
    /** Seek assistance from others. */
    HELP,
    /** Experiment with low-commitment attempts before committing fully. */
    TRY_AND_FAIL,
}

@Serializable
enum class SolutionResult {
    IN_PROGRESS,
    FAILED,
    SUCCESS,
    UNKNOWN,
}
