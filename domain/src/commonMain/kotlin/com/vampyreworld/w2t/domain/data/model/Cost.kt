package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * Unified cost representation used across [ActionGoal], [Solution], and [Challenges].
 *
 * All numeric fields are Int (0–100 relative scale). Use -1 as a sentinel for "unknown".
 * Unknown values are encoded as **0.5f** in [toNeuralInputVector] — the neutral midpoint —
 * which is consistent with [UserMood.toNeuralInputVector].
 *
 * @property energyCost   0–100 relative effort/energy required; -1 = unknown
 * @property timeCost     0–100 relative time required; -1 = unknown
 * @property moneyCost    0–100 relative financial cost; -1 = unknown
 * @property moneyEstimate optional free-form display string e.g. "~$30/mo"; prefer
 *                         [moneyCost] for any ML or comparison logic
 */
@Serializable
data class Cost(
    val energyCost: Int,
    val timeCost: Int,
    val moneyCost: Int,
    val moneyEstimate: String? = null,
) {
    /** Returns a 3-element float array safe to pass into a neural network. */
    fun toNeuralInputVector(): FloatArray = floatArrayOf(
        if (energyCost < 0) 0.5f else energyCost / 100f,
        if (timeCost   < 0) 0.5f else timeCost   / 100f,
        if (moneyCost  < 0) 0.5f else moneyCost  / 100f,
    )
}
