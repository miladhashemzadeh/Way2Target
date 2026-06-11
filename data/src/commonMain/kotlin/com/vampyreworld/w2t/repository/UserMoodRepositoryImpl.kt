package com.vampyreworld.w2t.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.vampyreworld.w2t.database.UserMoodEntity
import com.vampyreworld.w2t.database.W2TDatabase
import com.vampyreworld.w2t.domain.data.model.UserMood
import com.vampyreworld.w2t.domain.repository.UserMoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserMoodRepositoryImpl(
    private val database: W2TDatabase
) : UserMoodRepository {

    private val queries = database.w2TDatabaseQueries

    override fun getUserMoods(): Flow<List<UserMood>> {
        return queries.selectAllUserMoods()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun saveUserMood(userMood: UserMood) {
        queries.insertUserMood(
            timestamp = userMood.timestamp,
            energyRate = userMood.energyRate,
            creativityRate = userMood.creativityRate,
            focusRate = userMood.focusRate,
            socialRate = userMood.socialRate,
            selfControlRate = userMood.selfControlRate
        )
    }

    private fun UserMoodEntity.toDomain(): UserMood = UserMood(
        timestamp = timestamp,
        energyRate = energyRate,
        creativityRate = creativityRate,
        focusRate = focusRate,
        socialRate = socialRate,
        selfControlRate = selfControlRate
    )
}
