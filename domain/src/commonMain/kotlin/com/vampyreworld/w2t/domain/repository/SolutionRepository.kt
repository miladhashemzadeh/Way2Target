package com.vampyreworld.w2t.domain.repository

import com.vampyreworld.w2t.domain.data.model.Solution
import kotlinx.coroutines.flow.Flow

interface SolutionRepository {
    fun getSolutions(goalId: Long?, challengeId: Long?): Flow<List<Solution>>
    fun getSolutionById(id: Long): Flow<Solution?>
    suspend fun saveSolution(solution: Solution): Long
    suspend fun deleteSolution(id: Long)
}
