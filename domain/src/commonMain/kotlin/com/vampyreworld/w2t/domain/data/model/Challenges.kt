package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Challenges(
    val id: Long,
    val solvingBeforeGoalId: Long?,
    val title: String,
    val desc: String = "",
    val cost: Cost,                     // Unified: replaces moneyCost, energyCost, timeCost
    val priority: Int,                  // 0–100; consistent with Decision.userRate
    val isBarrier: Boolean,
    val parentGoalId: Long?,
    val preferredSolutions: List<Long> = emptyList(),
    val helpsToSolution: List<Long> = emptyList(),
    val failedDecisions: List<Long> = emptyList(),
    val moodImpact: Int,
    val prosAfterSolve: Long?,
    val consAfterFailure: Long?,
    val stabilityConditions: List<StabilityCondition> = emptyList(),
    val status: ChallengeStatus = ChallengeStatus.ACTIVE
) : WayNode {


    fun toNeuralInputVector(): FloatArray {
        val costVector = cost.toNeuralInputVector()
        return costVector + floatArrayOf(
            priority / 100f,
            if (isBarrier) 1.0f else 0.0f
        )
    }
}

@Serializable
enum class ChallengeStatus {
    ACTIVE,
    FINISHED,
    FAILED,
    CANCELLED
}
