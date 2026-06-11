package com.vampyreworld.w2t.database

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
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

val intAdapter = object : ColumnAdapter<Int, Long> {
    override fun decode(databaseValue: Long): Int = databaseValue.toInt()
    override fun encode(value: Int): Long = value.toLong()
}

val longAdapter = object : ColumnAdapter<Long, Long> {
    override fun decode(databaseValue: Long): Long = databaseValue
    override fun encode(value: Long): Long = value
}

val goalStatusAdapter = EnumColumnAdapter<GoalStatus>()
val wayStatusAdapter = EnumColumnAdapter<WayStatus>()
val relationTypeAdapter = EnumColumnAdapter<RelationType>()
val solutionTypeAdapter = EnumColumnAdapter<SolutionType>()
val solutionResultAdapter = EnumColumnAdapter<SolutionResult>()

val actionScheduleAdapter = jsonColumnAdapter<ActionSchedule>()
val costAdapter = jsonColumnAdapter<Cost>()
val decisionContextAdapter = jsonColumnAdapter<DecisionContext>()
val longListAdapter = jsonColumnAdapter<List<Long>>()
