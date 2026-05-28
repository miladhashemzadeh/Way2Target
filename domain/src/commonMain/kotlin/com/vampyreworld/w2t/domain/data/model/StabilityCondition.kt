package com.vampyreworld.w2t.domain.data.model

data class StabilityCondition(
    val id: Long,
    val title: String,
    val description: String = "",
    val isMaintained: Boolean = true
)
