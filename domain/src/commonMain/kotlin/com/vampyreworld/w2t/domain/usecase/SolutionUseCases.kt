package com.vampyreworld.w2t.domain.usecase

import com.vampyreworld.w2t.domain.data.model.Solution
import kotlinx.coroutines.flow.Flow

interface GetSolutionsUseCase {
    operator fun invoke(goalId: Long?, challengeId: Long?): Flow<List<Solution>>
}

interface AddSolutionUseCase {
    suspend operator fun invoke(solution: Solution, challengeId: Long? = null): Long
}
