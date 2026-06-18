package com.vampyreworld.w2t.targetft.action.actionCreate

import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.domain.data.model.ActionSchedule
import kotlinx.coroutines.flow.Flow

interface ActionCreateContract {
    data class State(
        val isLoading: Boolean = false,
        val parentId: Long? = null,
        val error: String? = null,
        val title: String = "",
        val description: String = "",
        val completionCriteria: String = "",
        val energyCost: Int = 50,
        val timeCost: Int = 50,
        val moneyCost: Int = 0,
        val schedule: ActionSchedule? = null
    )

    sealed class SideEffect {
        object Back : SideEffect()
        data class ShowError(val message: String) : SideEffect()
    }

    sealed class Intent {
        object OnBackClicked : Intent()
        data class OnTitleChanged(val title: String) : Intent()
        data class OnDescriptionChanged(val description: String) : Intent()
        data class OnCriteriaChanged(val criteria: String) : Intent()
        data class OnCostChanged(val energy: Int, val time: Int, val money: Int) : Intent()
        data class OnScheduleChanged(val schedule: ActionSchedule?) : Intent()
        data class OnSaveGoal(
            val title: String,
            val description: String,
            val completionCriteria: String,
            val energyCost: Int,
            val timeCost: Int,
            val moneyCost: Int,
            val schedule: ActionSchedule? = null
        ) : Intent()
    }

    interface Component {
        val state: Value<State>
        val sideEffects: Flow<SideEffect>
        fun onIntent(intent: Intent)
    }
}
