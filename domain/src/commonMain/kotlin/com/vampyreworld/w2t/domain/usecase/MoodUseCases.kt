package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.UserMood
import kotlinx.coroutines.flow.Flow

interface AddMoodUseCase {
    suspend operator fun invoke(mood: UserMood)
}

interface GetMoodHistoryUseCase {
    operator fun invoke(): Flow<List<UserMood>>
}
