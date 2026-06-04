package com.vampyreworld.w2t.domain.repository

import com.vampyreworld.w2t.domain.data.model.UserMood
import kotlinx.coroutines.flow.Flow

interface UserMoodRepository {
    fun getUserMoods(): Flow<List<UserMood>>
    suspend fun saveUserMood(userMood: UserMood)
}
