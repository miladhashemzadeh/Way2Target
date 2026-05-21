package com.vampyreworld.w2t.schallengeft.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.schallengeft.SChallengeComponent
import com.vampyreworld.w2t.schallengeft.SChallengeContract
import com.vampyreworld.w2t.schallengeft.ui.components.ChallengeCard

@Composable
fun ChallengesListScreen(
    state: SChallengeContract.State,
    component: SChallengeComponent,
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Active Challenges", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        items(state.challenges) { challenge ->
            ChallengeCard(
                challenge = challenge,
                onClick = { 
                    // In a real app, send intent to select challenge
                    // component.onIntent(SChallengeContract.Intent.SelectChallenge(challenge.id))
                }
            )
        }

        if (state.challenges.isEmpty()) {
            item {
                Text("No challenges found. Keep moving forward!", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
