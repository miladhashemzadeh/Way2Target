package com.vampyreworld.w2t.schallengeft.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.schallengeft.SChallengeContract
import com.vampyreworld.w2t.schallengeft.component.SChallengeComponent

@Composable
fun ChallengesListScreen(
    state: SChallengeContract.State,
    component: SChallengeComponent,
    padding: PaddingValues
) {
    val colors = LocalAppColorScheme.current
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Ongoing", "Finished", "Failed")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        W2TTabNav(
            tabs = tabs.map { tab ->
                val count = when (tab) {
                    "Ongoing" -> state.challenges.count { it.status == GoalStatus.ACTIVE }
                    "Finished" -> state.challenges.count { it.status == GoalStatus.COMPLETED }
                    "Failed" -> state.challenges.count { it.status == GoalStatus.CANCELLED }
                    else -> 0
                }
                "$tab ($count)"
            },
            selectedTabIndex = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val filteredChallenges = state.challenges.filter {
                when (selectedTab) {
                    0 -> it.status == GoalStatus.ACTIVE
                    1 -> it.status == GoalStatus.COMPLETED
                    2 -> it.status == GoalStatus.CANCELLED
                    else -> true
                }
            }

            items(filteredChallenges) { challenge ->
                W2TChallengeCard(
                    title = challenge.title,
                    goalTitle = "Learn Programming", // Should come from state
                    description = challenge.desc,
                    status = when (challenge.status) {
                        GoalStatus.ACTIVE -> "Ongoing"
                        GoalStatus.COMPLETED -> "Finished"
                        GoalStatus.CANCELLED -> "Failed"
                        else -> "Ongoing"
                    },
                    modifier = Modifier.clickable {
                        println("ChallengesListScreen: clicked challenge ${challenge.id}")
                        component.onIntent(SChallengeContract.Intent.OnChallengeClick(challenge.id))
                    }
                ) {
                    // Show some solutions or AI strategy if expanded or in list
                    if (challenge.status == GoalStatus.ACTIVE) {
                        W2TStrategyCard(
                            description = "Focus on one data structure at a time, implement basic operations, then move to complex algorithms.",
                            onButtonClick = { 
                                println("ChallengesListScreen: strategy clicked for challenge ${challenge.id}")
                                component.onIntent(SChallengeContract.Intent.OnViewChallengeSolutions(challenge.id))
                            }
                        )
                    }
                }
            }

            if (filteredChallenges.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text(
                            text = "No challenges found in this category.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.muted
                        )
                    }
                }
            }
        }
    }
}
