package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Challenges

class AddChallengeUseCase {
    operator fun invoke(challenge: Challenges) { /* TODO: Implement */ }
}

class GetChallengesUseCase {
    operator fun invoke(): List<Challenges> = emptyList()
}
