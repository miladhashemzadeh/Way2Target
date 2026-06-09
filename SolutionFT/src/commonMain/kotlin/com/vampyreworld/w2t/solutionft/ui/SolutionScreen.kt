package com.vampyreworld.w2t.solutionft.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.solutionft.SolutionContract
import com.vampyreworld.w2t.solutionft.component.SolutionComponent
import com.vampyreworld.w2t.solutionft.presentation.screen.ListOfSolutionsScreen

@Composable
fun SolutionScreen(component: SolutionComponent) {
    val state by component.state.subscribeAsState()

    ListOfSolutionsScreen(
        goalId = null, // In a more complete app, this might come from state
        challengeId = null,
        solutions = state.solutions,
        onBack = { component.onIntent(SolutionContract.Intent.OnBackClicked) },
        onAddSolution = { 
            // In a real app, this would open a dialog or navigate to a specialized screen
            // For now, let's just use the intent we have in component
            component.onIntent(SolutionContract.Intent.OnSaveClicked) 
        },
        onSolutionClick = { /* Navigate to solution detail */ }
    )
}
