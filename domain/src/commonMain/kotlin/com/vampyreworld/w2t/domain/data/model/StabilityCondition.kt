package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StabilityCondition(
    val id: Long,
    val title: String,
    val description: String = "",
    val isMaintained: Boolean = true
)
