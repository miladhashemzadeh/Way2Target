package com.vampyreworld.w2t.targetft.presentation.component

import com.vampyreworld.w2t.core.arch.MviComponent
import com.vampyreworld.w2t.targetft.presentation.intent.TargetDetailIntent
import com.vampyreworld.w2t.targetft.presentation.state.TargetDetailState

interface TargetDetailComponent : MviComponent<TargetDetailState, TargetDetailIntent, TargetDetailComponent.Label> {
    sealed interface Label {
        data object Back : Label
    }
}
