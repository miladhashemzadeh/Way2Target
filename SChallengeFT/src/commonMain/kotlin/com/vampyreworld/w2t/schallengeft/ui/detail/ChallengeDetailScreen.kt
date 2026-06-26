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
import com.vampyreworld.w2t.domain.data.model.SolutionType
import com.vampyreworld.w2t.schallengeft.SChallengeContract
import com.vampyreworld.w2t.schallengeft.component.SChallengeComponent
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings

@Composable
fun ChallengeDetailScreen(
    challenge: Challenges,
    solutions: List<Solution>,
    component: SChallengeComponent,
    padding: PaddingValues
) {
    val strings = LocalAppStrings.current
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(padding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ChallengeHeader(challenge)
        }

        item {
            ActionRow(component, challenge.id)
        }

        if (challenge.stabilityConditions.isNotEmpty()) {
            item {
                Text(strings.stabilityConditions, style = MaterialTheme.typography.titleLarge)
            }
            items(challenge.stabilityConditions) { condition ->
                StabilityConditionItem(condition, component)
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(strings.suggestedSolutions, style = MaterialTheme.typography.titleLarge)
                TextButton(onClick = { component.onIntent(SChallengeContract.Intent.OnViewSolutions) }) {
                    Text(strings.viewAll)
                }
            }
        }

        if (solutions.isEmpty()) {
            item {
                Text(strings.noSolutionsFound, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            items(solutions) { solution ->
                SolutionItem(solution)
            }
        }
    }
}

@Composable
private fun ChallengeHeader(challenge: Challenges) {
    val strings = LocalAppStrings.current
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Bolt, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = strings.activeChallenge, style = MaterialTheme.typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = challenge.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = challenge.desc.ifEmpty { strings.noDescriptionChallenge },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ActionRow(component: SChallengeComponent, challengeId: Long) {
    val strings = LocalAppStrings.current
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { component.onIntent(SChallengeContract.Intent.OnTakeAiHelp) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer, contentColor = MaterialTheme.colorScheme.onSecondaryContainer)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(strings.aiHelp)
            }
            Button(
                onClick = { component.onIntent(SChallengeContract.Intent.OnAddSolution) },
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(strings.solution)
            }
            OutlinedButton(
                onClick = { component.onIntent(SChallengeContract.Intent.OnMakeDecision) },
                modifier = Modifier.weight(1f)
            ) {
                Text(strings.decide)
            }
        }
        
        TextButton(
            onClick = { component.onIntent(SChallengeContract.Intent.OnDeleteChallenge(challengeId)) },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
        ) {
            Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(strings.deleteChallenge)
        }
    }
}

@Composable
private fun SolutionItem(solution: Solution) {
    val strings = LocalAppStrings.current
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
                Text(strings.strategyType + ": " + solution.solutionType.getLocalizedName(strings), style = MaterialTheme.typography.labelSmall)
                Text(strings.strategicStrength + ": ${solution.aidStrength}%", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
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

private fun SolutionType.getLocalizedName(strings: com.vampyreworld.w2t.sharedui.localization.AppStrings): String = when (this) {
    SolutionType.AVOIDANCE -> strings.solTypeAvoidance
    SolutionType.DIRECT_CONFRONTATION -> strings.solTypeDirectConfrontation
    SolutionType.EDIT_STRUCTURAL -> strings.solTypeEditStructural
    SolutionType.DIVIDING_AND_CONQUER -> strings.solTypeDividingAndConquer
    SolutionType.SUBSTITUTION -> strings.solTypeSubstitution
    SolutionType.DELEGATE -> strings.solTypeDelegate
    SolutionType.PLANNING -> strings.solTypePlanning
    SolutionType.EMOTIONAL_REGULATION -> strings.solTypeEmotionalRegulation
    SolutionType.HELP -> strings.solTypeHelp
    SolutionType.TRY_AND_FAIL -> strings.solTypeTryAndFail
}
