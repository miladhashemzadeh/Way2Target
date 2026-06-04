package com.vampyreworld.w2t.database

import app.cash.sqldelight.ColumnAdapter
import com.vampyreworld.w2t.domain.data.model.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T : Any> jsonColumnAdapter() = object : ColumnAdapter<T, String> {
    override fun decode(databaseValue: String): T = Json.decodeFromString(databaseValue)
    override fun encode(value: T): String = Json.encodeToString(value)
}

val booleanAdapter = object : ColumnAdapter<Boolean, Long> {
    override fun decode(databaseValue: Long): Boolean = databaseValue == 1L
    override fun encode(value: Boolean): Long = if (value) 1L else 0L
}

val goalTierAdapter = jsonColumnAdapter<GoalTier>()
val goalStatusAdapter = jsonColumnAdapter<GoalStatus>()
val wayStatusAdapter = jsonColumnAdapter<WayStatus>()
val schedulingInfoAdapter = jsonColumnAdapter<SchedulingInfo>()
val solutionTypeAdapter = jsonColumnAdapter<SolutionType>()
val solutionResultAdapter = jsonColumnAdapter<SolutionResult>()
val costAdapter = jsonColumnAdapter<Cost>()
val decisionContextAdapter = jsonColumnAdapter<DecisionContext>()
