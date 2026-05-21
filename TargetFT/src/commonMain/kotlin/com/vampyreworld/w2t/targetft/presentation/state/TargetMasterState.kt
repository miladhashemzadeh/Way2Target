package com.vampyreworld.w2t.targetft.presentation.state

import com.vampyreworld.w2t.domain.data.model.Goal

data class TargetMasterState(
    val isLoading: Boolean = false,
    val goals: List<Goal> = emptyList(),
    val error: String? = null
)
