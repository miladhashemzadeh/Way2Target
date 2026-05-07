package com.vampyreworld.w2t.sharedui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.vampyreworld.w2t.sharedui.theme.color.AppColorScheme
import com.vampyreworld.w2t.sharedui.theme.color.BgCool
import com.vampyreworld.w2t.sharedui.theme.color.BgLight
import com.vampyreworld.w2t.sharedui.theme.color.BgSecondary
import com.vampyreworld.w2t.sharedui.theme.color.Black15
import com.vampyreworld.w2t.sharedui.theme.color.Black20
import com.vampyreworld.w2t.sharedui.theme.color.Black40
import com.vampyreworld.w2t.sharedui.theme.color.Black80
import com.vampyreworld.w2t.sharedui.theme.color.Blue
import com.vampyreworld.w2t.sharedui.theme.color.BrightRed
import com.vampyreworld.w2t.sharedui.theme.color.Dark
import com.vampyreworld.w2t.sharedui.theme.color.Dark10
import com.vampyreworld.w2t.sharedui.theme.color.DarkColorScheme
import com.vampyreworld.w2t.sharedui.theme.color.Error15
import com.vampyreworld.w2t.sharedui.theme.color.Error20
import com.vampyreworld.w2t.sharedui.theme.color.Error40
import com.vampyreworld.w2t.sharedui.theme.color.Error5
import com.vampyreworld.w2t.sharedui.theme.color.Error60
import com.vampyreworld.w2t.sharedui.theme.color.Error80
import com.vampyreworld.w2t.sharedui.theme.color.GradientBlue
import com.vampyreworld.w2t.sharedui.theme.color.Gray
import com.vampyreworld.w2t.sharedui.theme.color.GrayBorderColor
import com.vampyreworld.w2t.sharedui.theme.color.GrayDeep
import com.vampyreworld.w2t.sharedui.theme.color.LightColorScheme
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.theme.color.MessageBackground
import com.vampyreworld.w2t.sharedui.theme.color.MessageStateColor
import com.vampyreworld.w2t.sharedui.theme.color.NaturalWhite10
import com.vampyreworld.w2t.sharedui.theme.color.NaturalWhite100
import com.vampyreworld.w2t.sharedui.theme.color.NaturalWhite15
import com.vampyreworld.w2t.sharedui.theme.color.NaturalWhite20
import com.vampyreworld.w2t.sharedui.theme.color.NaturalWhite40
import com.vampyreworld.w2t.sharedui.theme.color.NaturalWhite5
import com.vampyreworld.w2t.sharedui.theme.color.NaturalWhite60
import com.vampyreworld.w2t.sharedui.theme.color.NaturalWhite80
import com.vampyreworld.w2t.sharedui.theme.color.Orange
import com.vampyreworld.w2t.sharedui.theme.color.Orange2
import com.vampyreworld.w2t.sharedui.theme.color.Secondary
import com.vampyreworld.w2t.sharedui.theme.color.Shadow
import com.vampyreworld.w2t.sharedui.theme.color.Success10
import com.vampyreworld.w2t.sharedui.theme.color.Success100
import com.vampyreworld.w2t.sharedui.theme.color.Success20
import com.vampyreworld.w2t.sharedui.theme.color.Success40
import com.vampyreworld.w2t.sharedui.theme.color.Success5
import com.vampyreworld.w2t.sharedui.theme.color.Success60
import com.vampyreworld.w2t.sharedui.theme.color.Success80
import com.vampyreworld.w2t.sharedui.theme.color.Transparent
import com.vampyreworld.w2t.sharedui.theme.color.Unspecified
import com.vampyreworld.w2t.sharedui.theme.color.Warning10
import com.vampyreworld.w2t.sharedui.theme.color.Warning100
import com.vampyreworld.w2t.sharedui.theme.color.Warning20
import com.vampyreworld.w2t.sharedui.theme.color.Warning40
import com.vampyreworld.w2t.sharedui.theme.color.Warning5
import com.vampyreworld.w2t.sharedui.theme.color.Warning60
import com.vampyreworld.w2t.sharedui.theme.color.Warning80
import com.vampyreworld.w2t.sharedui.theme.color.White20
import com.vampyreworld.w2t.sharedui.theme.color.White40
import com.vampyreworld.w2t.sharedui.theme.color.White5
import com.vampyreworld.w2t.sharedui.theme.color.White80
import com.vampyreworld.w2t.sharedui.theme.color.backgroundDark
import com.vampyreworld.w2t.sharedui.theme.color.black
import com.vampyreworld.w2t.sharedui.theme.color.black10
import com.vampyreworld.w2t.sharedui.theme.color.black100
import com.vampyreworld.w2t.sharedui.theme.color.black5
import com.vampyreworld.w2t.sharedui.theme.color.black60
import com.vampyreworld.w2t.sharedui.theme.color.blueFill
import com.vampyreworld.w2t.sharedui.theme.color.customColor1ContainerDark
import com.vampyreworld.w2t.sharedui.theme.color.customColor1Dark
import com.vampyreworld.w2t.sharedui.theme.color.customColor2ContainerDark
import com.vampyreworld.w2t.sharedui.theme.color.customColor2Dark
import com.vampyreworld.w2t.sharedui.theme.color.customColor3ContainerDark
import com.vampyreworld.w2t.sharedui.theme.color.customColor3Dark
import com.vampyreworld.w2t.sharedui.theme.color.dark20
import com.vampyreworld.w2t.sharedui.theme.color.dark40
import com.vampyreworld.w2t.sharedui.theme.color.dark60
import com.vampyreworld.w2t.sharedui.theme.color.error10
import com.vampyreworld.w2t.sharedui.theme.color.error100
import com.vampyreworld.w2t.sharedui.theme.color.errorContainerDarkMediumContrast
import com.vampyreworld.w2t.sharedui.theme.color.greenBlue
import com.vampyreworld.w2t.sharedui.theme.color.greenBoarder
import com.vampyreworld.w2t.sharedui.theme.color.highText
import com.vampyreworld.w2t.sharedui.theme.color.inversePrimaryDark
import com.vampyreworld.w2t.sharedui.theme.color.lightBlue100
import com.vampyreworld.w2t.sharedui.theme.color.lowText
import com.vampyreworld.w2t.sharedui.theme.color.outlineDark
import com.vampyreworld.w2t.sharedui.theme.color.outlineVariantDark
import com.vampyreworld.w2t.sharedui.theme.color.primary100
import com.vampyreworld.w2t.sharedui.theme.color.primaryContainerDark
import com.vampyreworld.w2t.sharedui.theme.color.purple
import com.vampyreworld.w2t.sharedui.theme.color.red
import com.vampyreworld.w2t.sharedui.theme.color.surfaceContainerDark
import com.vampyreworld.w2t.sharedui.theme.color.surfaceContainerHighDark
import com.vampyreworld.w2t.sharedui.theme.color.white10
import com.vampyreworld.w2t.sharedui.theme.color.white100
import com.vampyreworld.w2t.sharedui.theme.color.white60

private val lightAppColors = AppColorScheme(

    // Core
    white = white100,
    success = greenBlue,
    warning = Orange,
    error = error100,
    info = Blue,
    neutral = NaturalWhite100,
    lowText = lowText,

    // Accent
    orange = Orange,
    orange2 = Orange2,

    grayDeep = GrayDeep,
    shadow = Shadow,

    // Backgrounds
    bgSecondary = BgSecondary,
    bgCool = BgCool,

    // Primary
    primary100 = primary100,
    primary80 = primary100.copy(alpha = .8f),
    primary60 = primary100.copy(alpha = .6f),
    primary40 = primary100.copy(alpha = .4f),
    primary20 = primary100.copy(alpha = .2f),
    primary10 = primary100.copy(alpha = .1f),
    primary5 = primary100.copy(alpha = .05f),
    primary15 = primary100.copy(alpha = .15f),

    // Dark shades
    dark = Dark,
    dark20 = dark20,
    dark40 = dark40,
    dark10 = Dark10,
    dark60 = dark60,

    // White shades
    white100 = white100,
    white80 = White80,
    white60 = white60,
    white40 = White40,
    white20 = White20,
    white10 = white10,
    white5 = White5,

    // Natural white
    naturalwhite100 = NaturalWhite100,
    naturalWhite80 = NaturalWhite80,
    naturalwhite60 = NaturalWhite60,
    naturalWhite40 = NaturalWhite40,
    naturalWhite20 = NaturalWhite20,
    naturalwhite10 = NaturalWhite10,
    naturalWhite5 = NaturalWhite5,

    // Black
    black = black,
    black100 = black100,
    black80 = Black80,
    black60 = black60,
    black40 = Black40,
    black20 = Black20,
    black15 = Black15,
    black10 = black10,
    black5 = black5,

    // Success
    success100 = Success100,
    success80 = Success80,
    success60 = Success60,
    success40 = Success40,
    success20 = Success20,
    success10 = Success10,
    success5 = Success5,

    // Warning
    warning100 = Warning100,
    warning80 = Warning80,
    warning60 = Warning60,
    warning40 = Warning40,
    warning20 = Warning20,
    warning10 = Warning10,
    warning5 = Warning5,

    // Error
    error100 = error100,
    error80 = Error80,
    error60 = Error60,
    error40 = Error40,
    error20 = Error20,
    error15 = Error15,
    error10 = error10,
    error5 = Error5,

    // Text
    highText = highText,

    // Secondary
    secondary = Secondary,

    // Extra
    greenBlue = greenBlue,
    greenBoarder = greenBoarder,
    blueFill = blueFill,
    lightBlue100 = lightBlue100,

    purple = purple,
    red = red,

    gray = Gray,

    messageBackground = MessageBackground,
    messageStateColor = MessageStateColor,

    grayBorderColor = GrayBorderColor,

    bgLight = BgLight,

    naturalWhite15 = NaturalWhite15,

    transparent = Transparent,
    unspecified = Unspecified,

    gradientBlue = GradientBlue,

    blue = Blue,

    brightRed = BrightRed
)
private val darkAppColors = AppColorScheme(

    // Core
    white = black100,
    success = customColor1Dark,
    warning = customColor2Dark,
    error = errorContainerDarkMediumContrast,
    info = customColor3Dark,
    neutral = black60,
    lowText = NaturalWhite40,

    // Accent
    orange = customColor2Dark,
    orange2 = customColor2ContainerDark,

    grayDeep = lowText,
    shadow = Black40.copy(alpha = 0.8f),

    // Backgrounds
    bgSecondary = surfaceContainerDark,
    bgCool = surfaceContainerHighDark,

    // Primary
    primary100 = primary100,
    primary80 = primary100.copy(alpha = .8f),
    primary60 = primary100.copy(alpha = .6f),
    primary40 = primary100.copy(alpha = .4f),
    primary20 = primary100.copy(alpha = .2f),
    primary10 = primary100.copy(alpha = .1f),
    primary5 = primary100.copy(alpha = .05f),
    primary15 = primary100.copy(alpha = .15f),

    // Dark shades
    dark = backgroundDark,
    dark20 = backgroundDark.copy(alpha = .2f),
    dark40 = backgroundDark.copy(alpha = .4f),
    dark10 = backgroundDark.copy(alpha = .1f),
    dark60 = backgroundDark.copy(alpha = .6f),

    // White shades
    white100 = white100,
    white80 = white100.copy(alpha = .8f),
    white60 = white100.copy(alpha = .6f),
    white40 = white100.copy(alpha = .4f),
    white20 = white100.copy(alpha = .2f),
    white10 = white100.copy(alpha = .1f),
    white5 = white100.copy(alpha = .05f),

    // Natural white
    naturalwhite100 = NaturalWhite100,
    naturalWhite80 = NaturalWhite100.copy(alpha = .8f),
    naturalwhite60 = NaturalWhite100.copy(alpha = .6f),
    naturalWhite40 = NaturalWhite100.copy(alpha = .4f),
    naturalWhite20 = NaturalWhite100.copy(alpha = .2f),
    naturalwhite10 = NaturalWhite100.copy(alpha = .1f),
    naturalWhite5 = NaturalWhite100.copy(alpha = .05f),

    // Black
    black = Color.Black,
    black100 = Color.Black,
    black80 = Color.Black.copy(alpha = .8f),
    black60 = Color.Black.copy(alpha = .6f),
    black40 = Color.Black.copy(alpha = .4f),
    black20 = Color.Black.copy(alpha = .2f),
    black15 = Color.Black.copy(alpha = .15f),
    black10 = Color.Black.copy(alpha = .1f),
    black5 = Color.Black.copy(alpha = .05f),

    // Success
    success100 = customColor1Dark,
    success80 = customColor1Dark.copy(alpha = .8f),
    success60 = customColor1Dark.copy(alpha = .6f),
    success40 = customColor1Dark.copy(alpha = .4f),
    success20 = customColor1Dark.copy(alpha = .2f),
    success10 = customColor1Dark.copy(alpha = .1f),
    success5 = customColor1Dark.copy(alpha = .05f),

    // Warning
    warning100 = customColor2Dark,
    warning80 = customColor2Dark.copy(alpha = .8f),
    warning60 = customColor2Dark.copy(alpha = .6f),
    warning40 = customColor2Dark.copy(alpha = .4f),
    warning20 = customColor2Dark.copy(alpha = .2f),
    warning10 = customColor2Dark.copy(alpha = .1f),
    warning5 = customColor2Dark.copy(alpha = .05f),

    // Error
    error100 = errorContainerDarkMediumContrast,
    error80 = errorContainerDarkMediumContrast.copy(alpha = .8f),
    error60 = errorContainerDarkMediumContrast.copy(alpha = .6f),
    error40 = errorContainerDarkMediumContrast.copy(alpha = .4f),
    error20 = errorContainerDarkMediumContrast.copy(alpha = .2f),
    error15 = errorContainerDarkMediumContrast.copy(alpha = .15f),
    error10 = errorContainerDarkMediumContrast.copy(alpha = .1f),
    error5 = errorContainerDarkMediumContrast.copy(alpha = .05f),

    // Text
    highText = highText,

    // Secondary
    secondary = customColor1Dark,

    // Extra
    greenBlue = customColor1Dark,
    greenBoarder = customColor1ContainerDark,
    blueFill = customColor3ContainerDark,
    lightBlue100 = primaryContainerDark,

    purple = customColor2Dark,
    red = error100,

    gray = outlineDark,

    messageBackground = customColor1ContainerDark,
    messageStateColor = customColor1Dark,

    grayBorderColor = outlineVariantDark,

    bgLight = backgroundDark,

    naturalWhite15 = white10,

    transparent = Color.Transparent,
    unspecified = Color.Unspecified,

    gradientBlue = inversePrimaryDark,

    blue = customColor3Dark,

    brightRed = errorContainerDarkMediumContrast
)


@Composable
fun W2TTheme(
    content: @Composable () -> Unit,
) {
    val config = ThemeConfigs()
    val colorScheme = when (config.isDarkMode) {
        true -> DarkColorScheme
        else -> LightColorScheme
    }
    val extraColors = when (config.isDarkMode) {
        true -> darkAppColors
        else -> lightAppColors
    }
    val appShape = AppShapes
    val typography = createAppTypography()

    CompositionLocalProvider(
        LocalAppColorScheme provides extraColors,
        LocalAppDimens provides DefaultAppDimens,
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = appShape,
            content = content
        )

    }
}