package com.vampyreworld.w2t.decissionmakingft.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.decissionmakingft.DecisionMakingComponent
import com.vampyreworld.w2t.decissionmakingft.DecisionMakingContract
import com.vampyreworld.w2t.decissionmakingft.presentation.screen.DecissionScreen

@Composable
fun DecisionMakingScreen(component: DecisionMakingComponent) {
    val state by component.state.subscribeAsState()

    DecissionScreen(
        title = "Decision Making",
        onBack = { component.onIntent(DecisionMakingContract.Intent.OnBackClicked) },
        onMakeDecission = { 
            // component.onIntent(DecisionMakingContract.Intent.OnMakeDecision(...)) 
        }
    )
}
