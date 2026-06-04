package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Way(
    val id: Long,
    val goalId: Long,
    val status: WayStatus,
    val steps: List<WayNode>?,
)

@Serializable
enum class WayStatus {
    ACTIVE,
    COMPLETED,
    ABANDONED
}
