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
import com.vampyreworld.w2t.targetft.ui.challenge.ChallengeCreateScreen
import com.vampyreworld.w2t.targetft.ui.challenge.ChallengesListScreen
import com.vampyreworld.w2t.targetft.ui.appraise.GoalAppraiseScreen
import com.vampyreworld.w2t.targetft.ui.appraise.ChallengeAppraiseScreen
import com.vampyreworld.w2t.targetft.ui.create.GoalStepsCreateScreen
import com.vampyreworld.w2t.targetft.ui.challenge.SolutionCreateScreen

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
                        TargetDetailScreen(goal, state.relatedGoals, state.challenges, component, padding)
                    }
                }
                TargetContract.Screen.CREATE_GOAL -> {
                    TargetCreateScreen(component, padding)
                }
                TargetContract.Screen.CHALLENGE_LIST -> {
                    ChallengesListScreen(component, state.challenges, state.relatedGoals, padding)
                }
                TargetContract.Screen.CHALLENGE_CREATE -> {
                    ChallengeCreateScreen(component, state.relatedGoals, padding)
                }
                TargetContract.Screen.CHALLENGE_DETAIL -> {
                    // ChallengeDetailScreen(component, padding)
                }
                TargetContract.Screen.GOAL_APPRAISE -> {
                    goal?.let { GoalAppraiseScreen(component, it, padding) }
                }
                TargetContract.Screen.CHALLENGE_APPRAISE -> {
                    state.challenges.firstOrNull()?.let { ChallengeAppraiseScreen(component, it, padding) }
                }
                TargetContract.Screen.DEFINE_STEPS -> {
                    goal?.let { GoalStepsCreateScreen(component, it, padding) }
                }
                TargetContract.Screen.SOLUTION_CREATE -> {
                    state.challenges.firstOrNull()?.let { SolutionCreateScreen(component, it, padding) }
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
