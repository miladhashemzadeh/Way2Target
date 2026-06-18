package com.vampyreworld.w2t.domain.usecase.schallenge.impl

import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.repository.ChallengeRepository
import com.vampyreworld.w2t.domain.usecase.AddChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.DeleteChallengeUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengeByIdUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import kotlinx.coroutines.flow.Flow

class GetChallengesUseCaseImpl(
    private val repository: ChallengeRepository
) : GetChallengesUseCase {
    override fun invoke(goalId: Long): Flow<List<Challenges>> {
        return repository.getChallenges(goalId)
    }
}

class AddChallengeUseCaseImpl(
    private val repository: ChallengeRepository
) : AddChallengeUseCase {
    override suspend fun invoke(challenge: Challenges) {
        repository.saveChallenge(challenge)
    }
}

class GetChallengeByIdUseCaseImpl(
    private val repository: ChallengeRepository
) : GetChallengeByIdUseCase {
    override fun invoke(id: Long): Flow<Challenges?> {
        return repository.getChallengeById(id)
    }
}

class DeleteChallengeUseCaseImpl(
    private val repository: ChallengeRepository
) : DeleteChallengeUseCase {
    override suspend fun invoke(id: Long) {
        repository.deleteChallenge(id)
    }
}

