package com.vampyreworld.w2t.domain.repository

import com.vampyreworld.w2t.domain.data.model.Challenges
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun getChallenges(goalId: Long): Flow<List<Challenges>>
    fun getChallengeById(id: Long): Flow<Challenges?>
    suspend fun saveChallenge(challenge: Challenges)
    suspend fun deleteChallenge(id: Long)
}
