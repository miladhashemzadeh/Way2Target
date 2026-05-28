package com.vampyreworld.w2t.schallengeft.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.domain.data.model.Solution
import com.vampyreworld.w2t.domain.data.model.StabilityCondition
import com.vampyreworld.w2t.schallengeft.SChallengeComponent
import com.vampyreworld.w2t.schallengeft.SChallengeContract

@Composable
fun ChallengeDetailScreen(
    challenge: Challenges,
    solutions: List<Solution>,
    component: SChallengeComponent,
    padding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ChallengeHeader(challenge)
        }

        item {
            ActionRow(component)
        }

        if (challenge.stabilityConditions.isNotEmpty()) {
            item {
                Text("Stability Conditions", style = MaterialTheme.typography.titleLarge)
            }
            items(challenge.stabilityConditions) { condition ->
                StabilityConditionItem(condition, component)
            }
        }

        item {
            Text("Suggested Solutions", style = MaterialTheme.typography.titleLarge)
        }

        if (solutions.isEmpty()) {
            item {
                Text("No solutions found. Try AI Help or make a decision.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            items(solutions) { solution ->
                SolutionItem(solution)
            }
        }

        item {
            StatusChangeSection(component)
        }
    }
}

@Composable
private fun ChallengeHeader(challenge: Challenges) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Bolt, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Active Challenge", style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = challenge.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = challenge.desc.ifEmpty { "No description provided for this challenge." },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ActionRow(component: SChallengeComponent) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = { component.onIntent(SChallengeContract.Intent.OnTakeAiHelp) },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("AI Help")
        }
        Button(
            onClick = { component.onIntent(SChallengeContract.Intent.OnMakeDecision) },
            modifier = Modifier.weight(1f)
        ) {
            Text("Decide")
        }
    }
}

@Composable
private fun SolutionItem(solution: Solution) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(12.dp))
                Text(solution.title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(solution.desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Type: ${solution.solutionType}", style = MaterialTheme.typography.labelSmall)
                Text("Strength: ${solution.aidStrength}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun StabilityConditionItem(condition: StabilityCondition, component: SChallengeComponent) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = condition.isMaintained,
            onCheckedChange = { component.onIntent(SChallengeContract.Intent.OnUpdateStabilityCondition(condition.id, it)) }
        )
        Column {
            Text(condition.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            if (condition.description.isNotEmpty()) {
                Text(condition.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun StatusChangeSection(component: SChallengeComponent) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Update Status", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            StatusButton("Done", MaterialTheme.colorScheme.primary) { component.onIntent(SChallengeContract.Intent.OnStatusChange("Finished")) }
            StatusButton("Fail", MaterialTheme.colorScheme.error) { component.onIntent(SChallengeContract.Intent.OnStatusChange("Failed")) }
            StatusButton("Stop", MaterialTheme.colorScheme.outline) { component.onIntent(SChallengeContract.Intent.OnStatusChange("Cancelled")) }
        }
    }
}

@Composable
private fun RowScope.StatusButton(label: String, color: androidx.compose.ui.graphics.Color, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.weight(1f),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = color)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall)
    }
}
