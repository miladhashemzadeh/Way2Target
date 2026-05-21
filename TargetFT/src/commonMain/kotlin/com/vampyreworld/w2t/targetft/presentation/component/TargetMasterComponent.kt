package com.vampyreworld.w2t.targetft.presentation.component

import com.vampyreworld.w2t.sharedui.arch.MviComponent
import com.vampyreworld.w2t.targetft.presentation.intent.TargetMasterIntent
import com.vampyreworld.w2t.targetft.presentation.state.TargetMasterState

interface TargetMasterComponent : MviComponent<TargetMasterState, TargetMasterIntent, TargetMasterComponent.Label> {
    
    sealed interface Label {
        data object Back : Label
        data class NavigateToDetail(val goalId: Long?) : Label
        data object NavigateToAddMilestone : Label
    }
}
