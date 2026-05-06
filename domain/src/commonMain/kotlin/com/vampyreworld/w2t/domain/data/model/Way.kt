package com.vampyreworld.w2t.domain.data.model

data class Way(
    val id: Long,
    val goalId: Long,
    val status: WayStatus,
    val steps: List<WayNode>?,
)

enum class WayStatus {
    ACTIVE,
    COMPLETED,
    ABANDONED
}
