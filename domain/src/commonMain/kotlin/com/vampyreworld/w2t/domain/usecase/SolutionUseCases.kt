package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.repository.SolutionRepository
import kotlinx.coroutines.flow.Flow

class GetSolutionsUseCase(private val repository: SolutionRepository) {
    operator fun invoke(goalId: Long?, challengeId: Long?): Flow<List<Solution>> = 
        repository.getSolutions(goalId, challengeId)
}

class AddSolutionUseCase(private val repository: SolutionRepository) {
    suspend operator fun invoke(solution: Solution) = repository.saveSolution(solution)
}
