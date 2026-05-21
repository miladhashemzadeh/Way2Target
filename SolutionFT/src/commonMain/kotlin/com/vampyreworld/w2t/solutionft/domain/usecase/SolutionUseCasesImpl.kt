package com.vampyreworld.w2t.solutionft.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.repository.SolutionRepository
import com.vampyreworld.w2t.domain.usecase.AddSolutionUseCase
import com.vampyreworld.w2t.domain.usecase.GetSolutionsUseCase
import kotlinx.coroutines.flow.Flow

class GetSolutionsUseCaseImpl(private val repository: SolutionRepository) : GetSolutionsUseCase {
    override fun invoke(goalId: Long?, challengeId: Long?): Flow<List<Solution>> = 
        repository.getSolutions(goalId, challengeId)
}

class AddSolutionUseCaseImpl(private val repository: SolutionRepository) : AddSolutionUseCase {
    override suspend fun invoke(solution: Solution) = repository.saveSolution(solution)
}
