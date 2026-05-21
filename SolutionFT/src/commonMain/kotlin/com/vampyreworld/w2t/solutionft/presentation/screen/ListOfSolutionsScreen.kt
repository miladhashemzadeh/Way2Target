package com.vampyreworld.w2t.solutionft.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Solution

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfSolutionsScreen(
    goalId: Long?,
    challengeId: Long?,
    solutions: List<Solution>,
    onBack: () -> Unit,
    onAddSolution: () -> Unit,
    onSolutionClick: (Long) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solutions") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddSolution) {
                Icon(Icons.Default.Add, contentDescription = "Add Solution")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (solutions.isEmpty()) {
                Text("No solutions found", modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    items(solutions) { solution ->
                        SolutionItem(solution = solution, onClick = { onSolutionClick(solution.id) })
                    }
                }
            }
        }
    }
}

@Composable
fun SolutionItem(solution: Solution, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = solution.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Type: ${solution.solutionType}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
