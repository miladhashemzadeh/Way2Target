package com.vampyreworld.w2t.targetft.presentation.intent

sealed interface TargetDetailIntent {
    data object Save : TargetDetailIntent
    data object Delete : TargetDetailIntent
    data object Back : TargetDetailIntent
}
