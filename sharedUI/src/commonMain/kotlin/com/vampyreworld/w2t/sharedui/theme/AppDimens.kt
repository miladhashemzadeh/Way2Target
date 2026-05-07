package com.vampyreworld.w2t.sharedui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AppDimens(
    // Spacing
    val spacingSmall: Dp,
    val spacingMedium: Dp,
    val spacingLarge: Dp,
    val spacingExtraLarge: Dp,
    // Text sizes
    val textSmall: TextUnit,
    val textMedium: TextUnit,
    val textLarge: TextUnit,
    val textTitle: TextUnit,
    val textHeadline: TextUnit,
)

val DefaultAppDimens = AppDimens(
    spacingSmall = 8.dp,
    spacingMedium = 12.dp,
    spacingLarge = 16.dp,
    spacingExtraLarge = 24.dp,
    textSmall = 12.sp,
    textMedium = 14.sp,
    textLarge = 16.sp,
    textTitle = 20.sp,
    textHeadline = 24.sp,
)

val LocalAppDimens = staticCompositionLocalOf { DefaultAppDimens }
