package com.vampyreworld.w2t.domain.usecase.prefrences

import com.vampyreworld.w2t.domain.repository.SettingsRepository

class SetThemeUseCase(private val repository: SettingsRepository) {
    operator fun invoke(isDarkMode: Boolean) {
        repository.setBoolean("is_dark_mode", isDarkMode)
    }
}
