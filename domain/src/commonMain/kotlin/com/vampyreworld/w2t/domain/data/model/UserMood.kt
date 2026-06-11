package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * A snapshot of the user's current mental/physical state at a point in time.
 *
 * All rate fields use 0–100 scale; -1 means the user skipped / unknown.
 * Unknown values are encoded as **0.5f** in [toNeuralInputVector] — the neutral
 * midpoint — consistent with [Cost.toNeuralInputVector].
 *
 * @property timestamp       epoch millis when this mood was recorded
 * @property energyRate      physical energy level (0 = exhausted, 100 = peak)
 * @property creativityRate  creative/lateral thinking capacity
 * @property focusRate       ability to concentrate on a single task
 * @property socialRate      desire/capacity for social interaction
 * @property selfControlRate impulse regulation and discipline level
 */
@Serializable
data class UserMood(
    val timestamp: Long,
    val energyRate: Int,
    val creativityRate: Int,
    val focusRate: Int,
    val socialRate: Int,
    val selfControlRate: Int,
) {
    /**
     * Returns a fixed-size float array safe to pass directly into genann.c.
     * Unknown values (-1) are encoded as 0.5f (neutral midpoint), not 0.0f,
     * so they don't bias the network toward "low" states.
     */
    fun toNeuralInputVector(): FloatArray = floatArrayOf(
        if (energyRate      < 0) 0.5f else energyRate      / 100f,
        if (creativityRate  < 0) 0.5f else creativityRate  / 100f,
        if (focusRate       < 0) 0.5f else focusRate       / 100f,
        if (socialRate      < 0) 0.5f else socialRate      / 100f,
        if (selfControlRate < 0) 0.5f else selfControlRate / 100f,
    )
}
