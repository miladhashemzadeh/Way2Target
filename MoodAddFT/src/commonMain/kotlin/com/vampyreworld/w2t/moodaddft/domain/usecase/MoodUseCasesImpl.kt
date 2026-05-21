package com.vampyreworld.w2t.moodaddft.domain.usecase

import com.vampyreworld.w2t.domain.data.model.UserMood
import com.vampyreworld.w2t.domain.usecase.AddMoodUseCase
import com.vampyreworld.w2t.domain.usecase.GetMoodHistoryUseCase

class AddMoodUseCaseImpl : AddMoodUseCase {
    override fun invoke(mood: UserMood) { /* TODO: Implement */ }
}

class GetMoodHistoryUseCaseImpl : GetMoodHistoryUseCase {
    override fun invoke(): List<UserMood> = emptyList()
}
