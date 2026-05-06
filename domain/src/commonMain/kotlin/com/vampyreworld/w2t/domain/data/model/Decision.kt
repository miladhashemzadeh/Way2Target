package com.vampyreworld.w2t.domain.data.model


data class Decision(
    val id: Long,
    val context: DecisionContext,
    val title: String,
    val desc: String,
    val cost: Cost,
    val userRate: Int,                  // 0–100; was Byte (signed, -128..127 — misleading for a rating)
    val aiScore: Int,                   // 0–100; was appRate: Byte — renamed to clarify it's the neural net's output
) : WayNode


sealed class DecisionContext {
    data class ForGoal(val goalId: Long) : DecisionContext()
    data class ForSolution(val solutionId: Long) : DecisionContext()
}
