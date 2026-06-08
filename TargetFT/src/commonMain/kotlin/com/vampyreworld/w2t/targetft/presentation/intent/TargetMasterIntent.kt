package com.vampyreworld.w2t.targetft.presentation.intent

sealed interface TargetMasterIntent {
    data object Refresh : TargetMasterIntent
    data class OnGoalClick(val goalId: Long) : TargetMasterIntent
    data class DeleteGoal(val goalId: Long) : TargetMasterIntent
    data object OnAddGoalClick : TargetMasterIntent
    data object OnBackClick : TargetMasterIntent
}
