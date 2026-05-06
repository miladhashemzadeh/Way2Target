package com.vampyreworld.w2t.domain.data.model

data class Challenges(
    val id: Long,
    val goalId: Long?,
    val title: String,
    val desc: String = "",
    val solvingBeforeGoal: Int,
    val isBarrier: Boolean,
    val priority: Byte,
    val preferredSolutions: List<Solution>?,
    val moneyCost: String?,
    val energyCost: Byte?,
    val moodCost: Byte?,
    val timeCost: Byte?,
    val prosAfterSolve: Long?,
    val consAfterSolve: Long?,
    val consAfterFailure: Long?,
    val prosAfterFailure: Long?,
    val failedDecisions: List<Decision>?,
    val helpsToSolution: List<Decision>?,
): WayNode
