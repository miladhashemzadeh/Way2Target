package com.vampyreworld.w2t.sharedui.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class UserProfileInfo(
    val name: String = "",
    val avatarUrl: String? = null
)

val LocalUserProfile = staticCompositionLocalOf { UserProfileInfo() }
