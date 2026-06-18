package com.vampyreworld.w2t.domain.usecase.profile

import com.vampyreworld.w2t.domain.data.model.UserProfile
import com.vampyreworld.w2t.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserProfileUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(): Flow<UserProfile> {
        return settingsRepository.getStringFlow("user_profile_json", "").map { json ->
            if (json.isBlank()) {
                UserProfile()
            } else {
                try {
                    kotlinx.serialization.json.Json.decodeFromString<UserProfile>(json)
                } catch (e: Exception) {
                    UserProfile()
                }
            }
        }
    }
}

class SaveUserProfileUseCase(private val settingsRepository: SettingsRepository) {
    operator fun invoke(profile: UserProfile) {
        val json = kotlinx.serialization.json.Json.encodeToString(UserProfile.serializer(), profile)
        settingsRepository.setString("user_profile_json", json)
        // Also update individual keys for easy access if needed
        settingsRepository.setString("user_name", profile.name)
    }
}
