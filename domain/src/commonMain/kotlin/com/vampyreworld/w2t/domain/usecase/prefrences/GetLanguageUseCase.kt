package com.vampyreworld.w2t.domain.usecase.prefrences

import com.vampyreworld.w2t.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetLanguageUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<String> {
        return repository.getStringFlow("language_code", "en")
    }
}
