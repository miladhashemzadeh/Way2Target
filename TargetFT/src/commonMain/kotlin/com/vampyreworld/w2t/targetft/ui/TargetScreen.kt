package com.vampyreworld.w2t.targetft.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TargetScreen(component: TargetComponent) {
    val state by component.state.subscribeAsState()
    val goal = state.selectedGoal

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (goal == null) "New Target" else goal.tier.name) },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(TargetContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (goal == null) {
                TargetCreateScreen(component, padding)
            } else {
                TargetDetailScreen(goal, state.relatedGoals, state.challenges, component, padding)
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
