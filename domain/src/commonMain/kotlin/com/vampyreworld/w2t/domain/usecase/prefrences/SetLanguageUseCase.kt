package com.vampyreworld.w2t.domain.usecase.prefrences

import com.vampyreworld.w2t.domain.repository.SettingsRepository

class SetLanguageUseCase(private val repository: SettingsRepository) {
    operator fun invoke(langCode: String) {
        repository.setString("language_code", langCode)
    }
}
