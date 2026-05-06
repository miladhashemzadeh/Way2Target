package com.vampyreworld.w2t.domain.data.model


data class UserMood(
    val timestamp: Long,
    val energyRate: Int,            // 0–100, or -1 if unknown
    val creativityRate: Int,        // 0–100, or -1 if unknown
    val focusRate: Int,             // 0–100, or -1 if unknown
    val socialRate: Int,            // 0–100, or -1 if unknown
    val selfControlRate: Int,       // 0–100, or -1 if unknown
) {
    /**
     * Returns a fixed-size float array safe to pass directly into genann.c.
     * Unknown values are encoded as 0.0f (neutral), not NaN or null.
     */
    fun toNeuralInputVector(): FloatArray = floatArrayOf(
        if (energyRate     < 0) 0f else energyRate     / 100f,
        if (creativityRate < 0) 0f else creativityRate / 100f,
        if (focusRate      < 0) 0f else focusRate      / 100f,
        if (socialRate     < 0) 0f else socialRate     / 100f,
        if (selfControlRate< 0) 0f else selfControlRate/ 100f,
    )
}
