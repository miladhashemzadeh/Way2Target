package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * Describes *when* and *how often* an [ActionGoal] should be performed.
 *
 * Three scheduling patterns are supported — use the appropriate subtype:
 * - [FixedDate]   — one-time task with a deadline (e.g. "submit form by Friday")
 * - [Recurring]   — repeating task on a cadence (e.g. "review vocabulary every day")
 * - [HabitBased]  — "X amount per Y period" habit (e.g. "study 30 min/day for 6 weeks")
 *
 * All timestamps are epoch millis. Durations are in milliseconds unless noted.
 */
@Serializable
sealed interface ActionSchedule {

    /**
     * A single occurrence with an optional deadline.
     *
     * @property startTime  when to begin; null = start immediately
     * @property deadline   hard cutoff; null = no deadline
     * @property reminderMs milliseconds before [deadline] to send a reminder; null = no reminder
     */
    @Serializable
    data class FixedDate(
        val startTime: Long? = null,
        val deadline: Long? = null,
        val reminderMs: Long? = null,
    ) : ActionSchedule

    /**
     * A task that repeats on a fixed interval.
     *
     * @property startTime      first occurrence (epoch millis)
     * @property endTime        stop recurring after this time; null = indefinite
     * @property intervalMs     repeat period in milliseconds (e.g. 86_400_000 = daily)
     * @property daysOfWeek     optional explicit day mask (Mon=1 … Sun=7); overrides [intervalMs]
     *                          when set, e.g. [1, 3, 5] = Mon/Wed/Fri
     * @property notifyBeforeMs lead-time for notification; null = no notification
     */
    @Serializable
    data class Recurring(
        val startTime: Long,
        val endTime: Long? = null,
        val intervalMs: Long,
        val daysOfWeek: List<Int> = emptyList(),
        val notifyBeforeMs: Long? = null,
    ) : ActionSchedule

    /**
     * Habit-style scheduling: "do [sessionDuration] of work per [periodMs] for [totalDuration]."
     *
     * Example — "study 30 min/day for 6 weeks":
     * ```
     * HabitBased(
     *   sessionDurationMs = 30 * 60 * 1_000,   // 30 minutes
     *   periodMs          = 24 * 60 * 60 * 1_000, // 1 day
     *   totalDurationMs   = 6 * 7 * 24 * 60 * 60 * 1_000, // 6 weeks
     * )
     * ```
     *
     * @property sessionDurationMs  target duration of a single session in millis
     * @property periodMs           the window in which [sessionDurationMs] should be completed
     * @property totalDurationMs    how long to maintain this habit; null = ongoing
     * @property startTime          when the habit begins; null = immediately
     * @property currentStreak      number of consecutive periods completed (updated by domain layer)
     * @property longestStreak      historical best streak
     */
    @Serializable
    data class HabitBased(
        val sessionDurationMs: Long,
        val periodMs: Long,
        val totalDurationMs: Long? = null,
        val startTime: Long? = null,
        val currentStreak: Int = 0,
        val longestStreak: Int = 0,
    ) : ActionSchedule
}
