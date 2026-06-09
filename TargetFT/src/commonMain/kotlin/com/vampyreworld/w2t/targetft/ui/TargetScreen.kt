package com.vampyreworld.w2t.targetft.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.component.TargetComponent
import com.vampyreworld.w2t.targetft.ui.create.TargetCreateScreen
import com.vampyreworld.w2t.targetft.ui.detail.TargetDetailScreen
import com.vampyreworld.w2t.targetft.ui.create.GoalStepsCreateScreen

@Composable
fun TargetScreen(component: TargetComponent) {
    val state by component.state.subscribeAsState()
    val goal = state.selectedGoal

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when (state.currentScreen) {
                TargetContract.Screen.DETAIL -> {
                    if (goal == null && state.initialTier != null) {
                        TargetCreateScreen(component, padding)
                    } else if (goal != null) {
                        TargetDetailScreen(goal, state.relatedGoals, component, padding)
                    }
                }
                TargetContract.Screen.CREATE_GOAL -> {
                    TargetCreateScreen(component, padding)
                }
                TargetContract.Screen.DEFINE_STEPS -> {
                    goal?.let { GoalStepsCreateScreen(component, it, padding) }
                }
            }

            AnimatedVisibility(
                visible = state.isLoading,
                modifier = Modifier.align(Alignment.TopCenter).padding(padding)
            ) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
