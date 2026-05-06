package com.vampyreworld.w2t.domain.data.model
 
/**
 * Unified cost representation used across Decision, Solution, and Challenge.
 *
 * Previously each model had its own cost fields with inconsistent types:
 *   - Decision: energyCost: Byte, timeCost: Byte, moneyCost: Byte
 *   - Solution: moneyCost: String, energyCost: Byte, timeCost: Byte
 *   - Challenges: moneyCost: String?, energyCost: Byte?, timeCost: Byte?
 *
 * All fields are now Int (0–100) — consistent, non-nullable with -1 sentinel for unknown.
 * [moneyEstimate] is a nullable String for free-form amounts where a fixed scale
 * doesn't make sense (e.g. "$50–$200/mo"), but prefer [moneyCost] (0–100 relative scale)
 * for ML features.
 */
data class Cost(
    val energyCost: Int,            // 0–100 relative effort scale; -1 = unknown
    val timeCost: Int,              // 0–100 relative time scale; -1 = unknown
    val moneyCost: Int,             // 0–100 relative money scale; -1 = unknown
    val moneyEstimate: String? = null,  // optional free-form e.g. "~$30" for display
) {
    fun toNeuralInputVector(): FloatArray = floatArrayOf(
        if (energyCost < 0) 0.5f else energyCost / 100f,
        if (timeCost   < 0) 0.5f else timeCost   / 100f,
        if (moneyCost  < 0) 0.5f else moneyCost  / 100f,
    )
}
 