package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.UserMood

class AddMoodUseCase {
    operator fun invoke(mood: UserMood) { /* TODO: Implement */ }
}

class GetMoodHistoryUseCase {
    operator fun invoke(): List<UserMood> = emptyList()
}
