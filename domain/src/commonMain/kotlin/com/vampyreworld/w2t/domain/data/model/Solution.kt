package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Solution(
    val id: Long,
    val title: String,
    val desc: String,
    val solutionType: SolutionType,
    val cost: Cost,
    val aidStrength: Int,               //how much this solution advances the goal (0–100)
    val result: SolutionResult = SolutionResult.UNKNOWN,
)

@Serializable
enum class SolutionType {
    AVOIDANCE,
    DIRECT_CONFRONTATION,
    EDIT_STRUCTURAL,
    DIVIDING_AND_CONQUER,
    SUBSTITUTION,
    DELEGATE,
    PLANNING,
    EMOTIONAL_REGULATION,
    HELP,
    TRY_AND_FAIL
}

@Serializable
enum class SolutionResult {
    IN_PROGRESS,
    FAILED,
    SUCCESS,
    UNKNOWN
}
