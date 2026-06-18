package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val name: String = "",
    val age: Int? = null,
    val mentalType: MentalType = MentalType.UNKNOWN,
    val worldview: Worldview = Worldview.UNKNOWN,
    val avatarUrl: String? = null
)

@Serializable
enum class MentalType(val displayName: String) {
    ANALYTICAL("تحلیلی"),
    CREATIVE("خلاق"),
    DRIVEN("عملگرا"),
    STRATEGIC("استراتژیک"),
    EMPATHETIC("همدل"),
    UNKNOWN("نامشخص")
}

@Serializable
enum class Worldview(val displayName: String) {
    STOIC("رواقی‌گری"),
    OPTIMIST("خوش‌بین"),
    REALIST("واقع‌بین"),
    PRAGMATIC("عمل‌گرا"),
    IDEALIST("آرمان‌گرا"),
    UNKNOWN("نامشخص")
}
