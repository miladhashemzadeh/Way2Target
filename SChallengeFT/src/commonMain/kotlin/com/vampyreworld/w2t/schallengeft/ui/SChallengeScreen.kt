package com.vampyreworld.w2t.schallengeft.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.schallengeft.SChallengeComponent
import com.vampyreworld.w2t.schallengeft.SChallengeContract
import com.vampyreworld.w2t.schallengeft.ui.detail.ChallengeDetailScreen
import com.vampyreworld.w2t.schallengeft.ui.list.ChallengesListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SChallengeScreen(component: SChallengeComponent) {
    val state by component.state.subscribeAsState()
    val challenge = state.selectedChallenge

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(challenge?.title ?: "Challenges") },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(SChallengeContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (challenge == null) {
            ChallengesListScreen(state, component, padding)
        } else {
            ChallengeDetailScreen(challenge, component, padding)
        }
    }
}
