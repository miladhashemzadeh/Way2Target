package com.vampyreworld.w2t.schallengeft.data.repository

import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class ChallengeRepositoryImpl : ChallengeRepository {
    private val challenges = MutableStateFlow<List<Challenges>>(emptyList())

    override fun getChallenges(goalId: Long): Flow<List<Challenges>> = 
        challenges.map { list -> list.filter { it.parentGoalId == goalId } }

    override fun getChallengeById(id: Long): Flow<Challenges?> = 
        challenges.map { list -> list.find { it.id == id } }

    override suspend fun saveChallenge(challenge: Challenges) {
        challenges.update { list ->
            val index = list.indexOfFirst { it.id == challenge.id }
            if (index >= 0) {
                list.toMutableList().also { it[index] = challenge }
            } else {
                list + challenge
            }
        }
    }

    override suspend fun deleteChallenge(id: Long) {
        challenges.update { list -> list.filter { it.id != id } }
    }
}
