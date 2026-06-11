package com.vampyreworld.w2t.shomeft

import com.vampyreworld.w2t.domain.data.model.ActionGoal
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.UserMood

interface HomeContract {
    data class State(
        val userName: String = "User",
        val isLoading: Boolean = false,
        val userMood: UserMood? = null,
        val masterGoals: List<Goal> = emptyList(),
        val todayActions: List<ActionGoal> = emptyList(),
        val aiInsights: List<AIInsight> = emptyList()
    )

    data class AIInsight(
        val id: String,
        val title: String,
        val description: String,
        val icon: String = "🤖"
    )

    sealed interface SideEffect {
        data class ShowToast(val message: String) : SideEffect
    }

    sealed interface Intent {
        data object OnProfileClick : Intent
        data object CreateMasterGoal : Intent
        data object OnCheckMoodClick : Intent
        data class OnMasterGoalClick(val goalId: Long) : Intent
        data class DeleteMasterGoal(val goalId: Long) : Intent
        data class CreateChallengeForMasterGoal(val goalId: Long) : Intent
        data class OnActionCheck(val actionId: Long, val isChecked: Boolean) : Intent
        data class OnViewStrategyClick(val insightId: String) : Intent
    }
}
