package com.vampyreworld.w2t.sharedui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.vampyreworld.w2t.sharedui.theme.color.DarkAppColors
import org.jetbrains.compose.resources.Font
import way2target.sharedui.generated.resources.CrimsonPro_Black
import way2target.sharedui.generated.resources.CrimsonPro_Bold
import way2target.sharedui.generated.resources.CrimsonPro_ExtraBold
import way2target.sharedui.generated.resources.CrimsonPro_ExtraLight
import way2target.sharedui.generated.resources.CrimsonPro_Light
import way2target.sharedui.generated.resources.CrimsonPro_Medium
import way2target.sharedui.generated.resources.CrimsonPro_SemiBold
import way2target.sharedui.generated.resources.Res


@Composable
fun createAppTypography(isDarkMode: Boolean): Typography {
    // Define text colors based on theme mode
    val primaryTextColor = if (isDarkMode) Color.White else Color(0xFF1C1929)
    val secondaryTextColor = if (isDarkMode) Color.White.copy(alpha = 0.8f) else Color(0xFF1C1929).copy(alpha = 0.8f)
    val tertiaryTextColor = if (isDarkMode) Color.White.copy(alpha = 0.6f) else Color(0xFF1C1929).copy(alpha = 0.6f)


    val font = FontFamily(
        Font(Res.font.CrimsonPro_Black, FontWeight.Black),
        Font(Res.font.CrimsonPro_Bold, FontWeight.Bold),
        Font(Res.font.CrimsonPro_ExtraBold, FontWeight.ExtraBold),
        Font(Res.font.CrimsonPro_ExtraLight, FontWeight.ExtraLight),
        Font(Res.font.CrimsonPro_Light, FontWeight.Light),
        Font(Res.font.CrimsonPro_Medium, FontWeight.Medium),
        Font(Res.font.CrimsonPro_SemiBold, FontWeight.SemiBold),
        Font(Res.font.CrimsonPro_ExtraLight, FontWeight.Thin),
    )

    return Typography(
        displayLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 64.sp,
            lineHeight = 23.sp,
            letterSpacing = 0.sp,
            color = primaryTextColor
        ),
        displayMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            color = primaryTextColor
        ),
        displaySmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            color = primaryTextColor
        ),

        // Headline Styles - Better font weight distribution
        headlineLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            lineHeight = 23.sp,
            color = primaryTextColor
        ),
        //
        headlineMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 30.sp,
            color = primaryTextColor,
            letterSpacing = 0.sp,
        ),

        headlineSmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = primaryTextColor
        ),

        // Title Styles - Increased weight for better hierarchy
        //
        titleLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = primaryTextColor
        ),
        titleMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            color = primaryTextColor,
            letterSpacing = 0.sp,
        ),
        //intre
        titleSmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            lineHeight = 22.4.sp,
            color = secondaryTextColor
        ),

        // Body Styles - Better readability with slightly increased line height
        bodyLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 20.sp, // Increased from 20sp for better readability
            color = secondaryTextColor
        ),
        //
        bodyMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 20.sp, // Increased from 20sp for better readability
            color = secondaryTextColor
        ),
        //
        bodySmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 20.sp, // Increased from 16sp for better readability
            color = tertiaryTextColor
        ),

        // Label Styles - Better distinction for UI elements

        labelLarge = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 23.sp,
            letterSpacing = 0.1.sp,
            color = secondaryTextColor,
        ),
        labelMedium = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            color = tertiaryTextColor
        ),
        //inter
        labelSmall = TextStyle(
            fontFamily = font,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 20.sp,
            color = tertiaryTextColor
        )
    )
}

