package com.vampyreworld.w2t.solutionft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.solutionft.SolutionContract
import com.vampyreworld.w2t.solutionft.component.SolutionComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionScreen(component: SolutionComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Solutions") },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(SolutionContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { component.onIntent(SolutionContract.Intent.OnSaveClicked) },
                containerColor = colors.accent,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Solution")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                W2THeader(
                    title = "Proposed Solutions",
                    subtitle = "Strategies to overcome your challenges",
                    avatarText = "S"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { component.onIntent(SolutionContract.Intent.OnGetAiInsightsClicked) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.accent.copy(alpha = 0.1f),
                        contentColor = colors.accent
                    ),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Get AI Insights")
                }
            }

            if (state.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = colors.accent)
                    }
                }
            } else if (state.solutions.isEmpty()) {
                item {
                    W2TCard {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No solutions found. Click + to add one or use AI insights.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.muted,
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                            Button(
                                onClick = { component.onIntent(SolutionContract.Intent.OnGetAiInsightsClicked) },
                                colors = ButtonDefaults.buttonColors(containerColor = colors.accent)
                            ) {
                                Text("Get AI Insights")
                            }
                        }
                    }
                }
            } else {
                items(state.solutions) { solution ->
                    W2TSolutionItem(
                        title = solution.title,
                        source = "User", // Can be updated if we have source in model
                        isAi = false
                    )
                }
            }
        }
    }
}
