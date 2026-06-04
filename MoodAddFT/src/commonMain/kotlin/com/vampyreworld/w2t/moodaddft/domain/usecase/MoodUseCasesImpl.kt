package com.vampyreworld.w2t.moodaddft.domain.usecase

import com.vampyreworld.w2t.domain.data.model.UserMood
import com.vampyreworld.w2t.domain.repository.UserMoodRepository
import com.vampyreworld.w2t.domain.usecase.AddMoodUseCase
import com.vampyreworld.w2t.domain.usecase.GetMoodHistoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class AddMoodUseCaseImpl(
    private val repository: UserMoodRepository
) : AddMoodUseCase {
    override suspend fun invoke(mood: UserMood) {
        repository.saveUserMood(mood)
    }
}

class GetMoodHistoryUseCaseImpl(
    private val repository: UserMoodRepository
) : GetMoodHistoryUseCase {
    override fun invoke(): Flow<List<UserMood>> = repository.getUserMoods()
}
