package com.vampyreworld.w2t.sharedui.theme.color

import androidx.compose.ui.graphics.Color

// ======================================================
// BASE COLORS (DEFINED DIRECTLY)
// ======================================================

val primary100 = Color(0x1e88E5FF)
val Secondary = Color(0x35d687FF)

val Primary80 = primary100.copy(alpha = .8f)
val Primary60 = primary100.copy(alpha = .6f)
val Primary40 = primary100.copy(alpha = .4f)
val Primary20 = primary100.copy(alpha = .2f)
val Primary10 = primary100.copy(alpha = .1f)
val Primary5 = primary100.copy(alpha = .05f)
val Primary15 = primary100.copy(alpha = .15f)

// ======================================================
// DARK / WHITE
// ======================================================

val Dark = Color(0xFF111418)

val Dark10 = Dark.copy(alpha = .1f)
val dark20 = Dark.copy(alpha = .2f)
val dark40 = Dark.copy(alpha = .4f)
val dark60 = Dark.copy(alpha = .6f)

val highText = Color(0xFFE1E2E8)
val lowText = Color(0xFFC3C6CF)

val white100 = Color.White

val White80 = white100.copy(alpha = .8f)
val white60 = white100.copy(alpha = .6f)
val White40 = white100.copy(alpha = .4f)
val White20 = white100.copy(alpha = .2f)
val white10 = white100.copy(alpha = .1f)
val White5 = white100.copy(alpha = .05f)

// ======================================================
// BACKGROUNDS
// ======================================================

val BgLight = Color(0xFFF8F9FF)
val BgSecondary = Color(0xFFECEDF4)
val BgCool = Color(0xFFE7E8EE)

// ======================================================
// ACCENT COLORS
// ======================================================

val lightBlue100 = Color(0xFFD3E4FF)

val greenBlue = Color(0xFF2B6A46)
val greenBoarder = Color(0xFFAFF1C4)

val blueFill = Color(0xFFCDE5FF)

// tertiary replacement
val purple = Color(0xFF815512)

// ======================================================
// ERROR / WARNING
// ======================================================

val red = Color(0xFFBA1A1A)

// warning -> customColor2Dark
val Warning100 = Color(0xFFF6BC70)

val Warning80 = Warning100.copy(alpha = .8f)
val Warning60 = Warning100.copy(alpha = .6f)
val Warning40 = Warning100.copy(alpha = .4f)
val Warning20 = Warning100.copy(alpha = .2f)
val Warning10 = Warning100.copy(alpha = .1f)
val Warning5 = Warning100.copy(alpha = .05f)

// error -> errorContainerDarkMediumContrast
val error100 = Color(0xFFFF5449)

val Error80 = error100.copy(alpha = .8f)
val Error60 = error100.copy(alpha = .6f)
val Error40 = error100.copy(alpha = .4f)
val Error20 = error100.copy(alpha = .2f)
val Error15 = error100.copy(alpha = .15f)
val error10 = error100.copy(alpha = .1f)
val Error5 = error100.copy(alpha = .05f)

// ======================================================
// ORANGE
// ======================================================

val Orange = Color(0xFF815512)
val Orange2 = Color(0xFFF6BC70)

// ======================================================
// SURFACE / NEUTRAL
// ======================================================

val NaturalWhite100 = Color(0xFFF8F9FF)

val NaturalWhite80 = NaturalWhite100.copy(alpha = .8f)
val NaturalWhite60 = NaturalWhite100.copy(alpha = .6f)
val NaturalWhite40 = NaturalWhite100.copy(alpha = .4f)
val NaturalWhite20 = NaturalWhite100.copy(alpha = .2f)
val NaturalWhite15 = NaturalWhite100.copy(alpha = .15f)
val NaturalWhite10 = NaturalWhite100.copy(alpha = .1f)
val NaturalWhite5 = NaturalWhite100.copy(alpha = .05f)

val black = Color.Black

val black100 = Color(0xFF111418)

val Black80 = black100.copy(alpha = .8f)
val black60 = black100.copy(alpha = .6f)
val Black40 = black100.copy(alpha = .4f)
val Black20 = black100.copy(alpha = .2f)
val Black15 = black100.copy(alpha = .15f)
val black10 = black100.copy(alpha = .1f)
val black5 = black100.copy(alpha = .05f)

// ======================================================
// SUCCESS
// ======================================================

val Success100 = Color(0xFF2B6A46)

val Success80 = Success100.copy(alpha = .8f)
val Success60 = Success100.copy(alpha = .6f)
val Success40 = Success100.copy(alpha = .4f)
val Success20 = Success100.copy(alpha = .2f)
val Success10 = Success100.copy(alpha = .1f)
val Success5 = Success100.copy(alpha = .05f)

// ======================================================
// MISC
// ======================================================

val InfoLight = Color(0xFF2E628C)

val Shadow = Color(0xFF000000).copy(alpha = 0.8f)

val Gray = Color(0xFF73777F)
val GrayDeep = Color(0xFF43474E)
val GrayBorderColor = Color(0xFFC3C6CF)

val MessageBackground = Color(0xFFAFF1C4)
val MessageStateColor = Color(0xFF2B6A46)

val Transparent = Color.Transparent
val Unspecified = Color.Unspecified

val GradientBlue = Color(0xFF39608F)

val BrightRed = error100

val Blue = Color(0xFF2E628C)


val surfaceContainerDark = Color(0xFF1D2024)
val surfaceContainerHighDark = Color(0xFF272A2F)

val primaryContainerDark = Color(0xFF1D4875)

val outlineDark = Color(0xFF8D9199)
val outlineVariantDark = Color(0xFF43474E)

val backgroundDark = Color(0xFF1e1e1e)

val inversePrimaryDark = Color(0xFF39608F)

// ======================================================
// CUSTOM DARK COLORS
// ======================================================

val customColor1Dark = Color(0xd7ecef)
val customColor1ContainerDark = Color(0xFF0C5130)

val customColor2Dark = Color(0xD8FBEA)
val customColor2ContainerDark = Color(0xFF643F00)

val customColor3Dark = Color(0xffb347FF)
val customColor3ContainerDark = Color(0xFF0B4A72)

val customColor4Dark = Color(0xFF8CCFF1)
val customColor4ContainerDark = Color(0xFF004D66)

// ======================================================
// ERROR DARK
// ======================================================

val errorContainerDarkMediumContrast = Color(0xFFFF5449)