package com.vampyreworld.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween

object NavigationAnimations {
    private const val ANIMATION_DURATION = 800

    fun fadeIn(): EnterTransition = fadeIn(
        animationSpec = tween(ANIMATION_DURATION)
    )

    fun fadeOut(): ExitTransition = fadeOut(
        animationSpec = tween(ANIMATION_DURATION)
    )
}