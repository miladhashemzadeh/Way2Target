package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GoalIdList(val ids: List<Long> = emptyList())

@Serializable
data class WayNodeListWrapper(val nodes: List<WayNode> = emptyList())

@Serializable
data class StabilityConditionListWrapper(val conditions: List<StabilityCondition> = emptyList())
