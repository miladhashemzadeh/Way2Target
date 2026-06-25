package com.vampyreworld.w2t.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.vampyreworld.w2t.database.SolutionEntity
import com.vampyreworld.w2t.database.W2TDatabase
import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.repository.SolutionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*

class SolutionRepositoryImpl(
    private val database: W2TDatabase
) : SolutionRepository {

    private val queries = database.w2TDatabaseQueries

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSolutions(goalId: Long?, challengeId: Long?): Flow<List<Solution>> {
        if (challengeId != null) {
            return queries.selectChallengeById(challengeId)
                .asFlow()
                .mapToOneOrNull(Dispatchers.IO)
                .flatMapLatest { challenge ->
                    val ids = challenge?.candidateSolutionIds ?: emptyList()
                    if (ids.isEmpty()) {
                        flowOf(emptyList())
                    } else {
                        queries.selectSolutionsByIds(ids)
                            .asFlow()
                            .mapToList(Dispatchers.IO)
                            .map { entities -> entities.map { it.toDomain() } }
                    }
                }
        }
        return queries.selectAllSolutions()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> 
                entities.map { it.toDomain() } 
            }
    }

    override fun getSolutionById(id: Long): Flow<Solution?> {
        return queries.selectSolutionById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { 
                it?.toDomain() 
            }
    }

    override suspend fun saveSolution(solution: Solution): Long {
        return database.transactionWithResult {
            if (solution.id == 0L) {
                queries.insertSolution(
                    title = solution.title,
                    description = solution.desc,
                    solutionType = solution.solutionType,
                    cost = solution.cost,
                    aidStrength = solution.aidStrength,
                    result = solution.result
                )
                queries.lastInsertId().executeAsOne()
            } else {
                queries.updateSolution(
                    id = solution.id,
                    title = solution.title,
                    description = solution.desc,
                    solutionType = solution.solutionType,
                    cost = solution.cost,
                    aidStrength = solution.aidStrength,
                    result = solution.result
                )
                solution.id
            }
        }
    }

    override suspend fun deleteSolution(id: Long) {
        queries.deleteSolution(id)
    }

    private fun SolutionEntity.toDomain(): Solution = Solution(
        id = id,
        title = title,
        desc = description,
        solutionType = solutionType,
        cost = cost,
        aidStrength = aidStrength,
        result = result
    )
}
