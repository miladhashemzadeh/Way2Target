package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * A prerequisite condition that must remain true for a [Challenges] to stay resolved.
 *
 * Example: a challenge "Afford rent in new city" might have a stability condition
 * "Monthly income ≥ €2 000". If that condition breaks, the challenge resurfaces.
 *
 * @property id            unique ID
 * @property title         short label (e.g. "Stable income")
 * @property description   longer explanation of what maintaining this condition requires
 * @property isMaintained  false = the condition has been broken and needs attention
 */
@Serializable
data class StabilityCondition(
    val id: Long,
    val title: String,
    val description: String = "",
    val isMaintained: Boolean = true,
)
