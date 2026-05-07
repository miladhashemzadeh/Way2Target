package com.vampyreworld.w2t.sharedui.theme

data class ThemeConfigs(val isDarkMode: Boolean = true,val lang: Lang = Lang.EN)

enum class Lang{
    EN,FA
}