package com.vampyreworld.w2t.domain.data.model

data class Decision(
    val id: Long,
    val goalId: Long?,
    val solutionId: Long?,
    val title: String,
    val desc: String,
    val userRate: Byte,
    val appRate: Byte,
): WayNode