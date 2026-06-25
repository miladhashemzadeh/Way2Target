package com.vampyreworld.w2t.domain.usecase.solution.impl

import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.repository.ChallengeRepository
import com.vampyreworld.w2t.domain.repository.SolutionRepository
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import com.vampyreworld.w2t.domain.usecase.GetSolutionsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class GetSolutionsUseCaseImpl(
    private val repository: SolutionRepository
) : GetSolutionsUseCase {
    override fun invoke(goalId: Long?, challengeId: Long?): Flow<List<Solution>> {
        return repository.getSolutions(goalId, challengeId)
    }
}

class AddSolutionUseCaseImpl(
    private val solutionRepository: SolutionRepository,
    private val challengeRepository: ChallengeRepository
) : AddSolutionUseCase {
    override suspend fun invoke(solution: Solution, challengeId: Long?): Long {
        val solutionId = solutionRepository.saveSolution(solution)
        if (challengeId != null) {
            val challenge = challengeRepository.getChallengeById(challengeId).firstOrNull()
            if (challenge != null) {
                val updatedChallenge = challenge.copy(
                    candidateSolutionIds = challenge.candidateSolutionIds + solutionId
                )
                challengeRepository.saveChallenge(updatedChallenge)
            }
        }
        return solutionId
    }
}
