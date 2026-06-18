package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Challenges
import kotlinx.coroutines.flow.Flow

interface GetChallengesUseCase {
    operator fun invoke(goalId: Long): Flow<List<Challenges>>
}

interface AddChallengeUseCase {
    suspend operator fun invoke(challenge: Challenges)
}

interface GetChallengeByIdUseCase {
    operator fun invoke(id: Long): Flow<Challenges?>
}

interface DeleteChallengeUseCase {
    suspend operator fun invoke(id: Long)
}
