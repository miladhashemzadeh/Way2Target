package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow

class GetChallengesUseCase(private val repository: ChallengeRepository) {
    operator fun invoke(goalId: Long): Flow<List<Challenges>> = repository.getChallenges(goalId)
}

class AddChallengeUseCase(private val repository: ChallengeRepository) {
    suspend operator fun invoke(challenge: Challenges) = repository.saveChallenge(challenge)
}

class GetChallengeByIdUseCase(private val repository: ChallengeRepository) {
    operator fun invoke(id: Long): Flow<Challenges?> = repository.getChallengeById(id)
}
