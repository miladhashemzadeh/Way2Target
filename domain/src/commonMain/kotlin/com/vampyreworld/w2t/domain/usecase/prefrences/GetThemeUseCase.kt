package com.vampyreworld.w2t.domain.usecase.prefrences

import com.vampyreworld.w2t.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetThemeUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<Boolean> {
        return repository.getBooleanFlow("is_dark_mode", false)
    }
}
