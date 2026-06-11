package com.vampyreworld.w2t.targetft.ui.challenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.targetft.master.MasterComponent

@Composable
fun ChallengesListScreen(
    component: MasterComponent,
    challenges: List<Challenges>,
    goals: List<Goal>,
    padding: PaddingValues
) {
    val colors = LocalAppColorScheme.current
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Ongoing (2)", "Finished (5)", "Failed (1)") // Static for now matching design

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Navigate to add challenge */ },
                containerColor = colors.challengeColor,
                contentColor = MaterialTheme.colorScheme.surface,
                shape = androidx.compose.foundation.shape.CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Challenge")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            W2THeader(
                title = "Challenge Center",
                subtitle = "Manage your blockers",
                avatarText = "!"
            )

            W2TTabNav(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(challenges) { challenge ->
                    val linkedGoal = goals.find { it.id == challenge.parentGoalId }
                    W2TChallengeCard(
                        title = challenge.title,
                        goalTitle = linkedGoal?.title ?: "No linked goal",
                        description = challenge.desc,
                        status = "Ongoing" // Static for now
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(text = "Possible Solutions:", style = MaterialTheme.typography.titleSmall)
                            W2TSolutionItem(title = "Explore visual tutorials on YouTube", source = "Suggested by user")
                            W2TSolutionItem(title = "Break down into smaller sub-problems", source = "Proposed by AI Assistant", isAi = true)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        W2TStrategyCard(
                            description = "Focus on one data structure at a time, implement basic operations, then move to complex algorithms.",
                            onButtonClick = { }
                        )
                    }
                }
                
                // Example of a failed challenge
                item {
                    W2TChallengeCard(
                        title = "Procrastinating on daily coding",
                        goalTitle = "Learn Programming",
                        description = "Struggling to maintain daily coding practice. Tend to get distracted.",
                        status = "Failed"
                    )
                }
            }
        }
    }
}
