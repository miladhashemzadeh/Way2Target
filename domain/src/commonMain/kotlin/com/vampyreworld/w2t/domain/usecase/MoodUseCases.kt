package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.UserMood

interface AddMoodUseCase {
    operator fun invoke(mood: UserMood)
}

interface GetMoodHistoryUseCase {
    operator fun invoke(): List<UserMood>
}
