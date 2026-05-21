package com.vampyreworld.w2t.targetft.presentation.state

import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalTier

data class TargetDetailState(
    val isLoading: Boolean = false,
    val goal: Goal? = null,
    val tier: GoalTier = GoalTier.ACTION,
    val error: String? = null
)
