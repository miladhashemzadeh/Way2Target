package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * One possible path through a [MilestoneGoal].
 *
 * A [Way] is a sequence of [WayNode]s — [Challenges] and [Solution]s interleaved —
 * that together describe how the user progressed (or plans to progress) through a milestone.
 *
 * Multiple [Way]s can exist for the same goal, representing alternative strategies.
 * Only one should be [WayStatus.ACTIVE] at a time; the rest are DRAFT or ABANDONED.
 *
 * **Status note:** [WayStatus] reflects the way's own execution state.
 * It should stay in sync with the owning [MilestoneGoal.status] — do not update them
 * independently. Prefer updating goal status and deriving way status from it.
 *
 * @property id      unique ID
 * @property goalId  the [MilestoneGoal] this way belongs to
 * @property status  execution state of this particular path
 * @property steps   ordered sequence of [WayNode]s (challenges + solutions); never null
 */
@Serializable
data class Way(
    val id: Long,
    val goalId: Long,
    val status: WayStatus,
    val steps: List<WayNode> = emptyList(),
)

@Serializable
enum class WayStatus {
    /** This path is a candidate but not yet being walked. */
    DRAFT,
    /** The user is currently walking this path. */
    ACTIVE,
    /** All steps completed successfully. */
    COMPLETED,
    /** The user gave up on or discarded this path. */
    ABANDONED,
}
