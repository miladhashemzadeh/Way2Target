package com.vampyreworld.w2t.domain.data.model

data class Solution(
    val id: Long,
    val title: String,
    val desc: String,
    val solutionType: SolutionType,
    val moneyCost: String,
    val energyCost: Byte,
    val timeCost: Byte,
    val powerToAid: Long,
    val result: SolutionResult = SolutionResult.UNKNOWN
)

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
enum class SolutionResult {
    IN_PROGRESS,
    FAILED,
    SUCCESS,
    UNKNOWN
}