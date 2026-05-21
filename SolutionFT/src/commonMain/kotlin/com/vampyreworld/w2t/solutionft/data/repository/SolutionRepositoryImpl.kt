package com.vampyreworld.w2t.solutionft.data.repository

import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.repository.SolutionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class SolutionRepositoryImpl : SolutionRepository {
    private val solutions = MutableStateFlow<List<Solution>>(emptyList())

    override fun getSolutions(goalId: Long?, challengeId: Long?): Flow<List<Solution>> = solutions

    override fun getSolutionById(id: Long): Flow<Solution?> = 
        solutions.map { list -> list.find { it.id == id } }

    override suspend fun saveSolution(solution: Solution) {
        solutions.update { list ->
            val index = list.indexOfFirst { it.id == solution.id }
            if (index >= 0) {
                list.toMutableList().also { it[index] = solution }
            } else {
                list + solution
            }
        }
    }

    override suspend fun deleteSolution(id: Long) {
        solutions.update { list -> list.filter { it.id != id } }
    }
}
