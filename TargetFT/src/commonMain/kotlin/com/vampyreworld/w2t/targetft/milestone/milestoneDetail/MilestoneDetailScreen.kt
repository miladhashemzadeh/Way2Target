package com.vampyreworld.w2t.targetft.milestone.milestoneDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings

import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MilestoneDetailScreen(
    component: MilestoneDetailContract.Component,
    padding: PaddingValues
) {
    val state by component.state.subscribeAsState()
    val goal = state.selectedGoal ?: return
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current
    
    var isEditing by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf(goal.title) }
    var editedDescription by remember { mutableStateOf(goal.description) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            val (icon, cleanTitle) = goal.title.extractIcon()
            TopAppBar(
                title = { Text(if (icon.isNotEmpty()) "$icon $cleanTitle" else cleanTitle, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(MilestoneDetailContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.goBack)
                    }
                },
                colors = com.vampyreworld.w2t.sharedui.catalog.w2tTopAppBarColors()
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

        item {
            W2TCard {
                Column(modifier = Modifier.fillMaxWidth()) {
                    if (isEditing) {
                        OutlinedTextField(
                            value = editedTitle,
                            onValueChange = { editedTitle = it },
                            label = { Text(strings.titleLabel) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = editedDescription,
                            onValueChange = { editedDescription = it },
                            label = { Text(strings.description) },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    val updatedGoal = when(goal) {
                                        is com.vampyreworld.w2t.domain.data.model.MasterGoal -> goal.copy(title = editedTitle, description = editedDescription)
                                        is com.vampyreworld.w2t.domain.data.model.MilestoneGoal -> goal.copy(title = editedTitle, description = editedDescription)
                                        is com.vampyreworld.w2t.domain.data.model.ActionGoal -> goal.copy(title = editedTitle, description = editedDescription)
                                    }
                                    component.onIntent(MilestoneDetailContract.Intent.UpdateGoal(updatedGoal))
                                    isEditing = false
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text(strings.save) }
                            OutlinedButton(onClick = { isEditing = false }, modifier = Modifier.weight(1f)) { Text(strings.cancel) }
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            W2TStatusChip(text = strings.ongoing, backgroundColor = colors.accent)
                            val parentId = state.parentId
                            if (parentId != null) {
                                Text(
                                    text = strings.parentGoalPrefix, 
                                    style = MaterialTheme.typography.bodySmall, 
                                    color = colors.muted,
                                    modifier = Modifier.clickable { component.onIntent(MilestoneDetailContract.Intent.OnGoalClick(parentId, "MASTER")) }
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { isEditing = true }) {
                                Icon(Icons.Default.Edit, contentDescription = strings.edit, tint = colors.accent)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = goal.title,
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        W2TProgressBar(progress = 0.5f, color = colors.accent)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = strings.percentComplete.format(50),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.accent,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        item {
            Button(
                onClick = { component.onIntent(MilestoneDetailContract.Intent.NavigateToChallengeList) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(28.dp)
            ) {
                Icon(Icons.Default.FlashOn, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(strings.createChallengeForGoal)
            }
        }

        item {
            W2TCard {
                W2TSectionTitle(strings.actionGoalsCount.format(state.actions.size))
                if (state.actions.isEmpty()) {
                    Text(
                        text = strings.noActionsDefined,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.muted,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    state.actions.forEach { action ->
                        val (actionIcon, actionCleanTitle) = action.title.extractIcon()
                        val (_, milestoneCleanTitle) = goal.title.extractIcon()
                        W2TActionItem(
                            title = if (actionIcon.isNotEmpty()) "$actionIcon $actionCleanTitle" else actionCleanTitle,
                            subtitle = milestoneCleanTitle,
                            time = if (action.status == GoalStatus.COMPLETED) strings.completed else strings.dueTomorrow,
                            checked = action.status == GoalStatus.COMPLETED,
                            onCheckedChange = { isChecked ->
                                component.onIntent(MilestoneDetailContract.Intent.UpdateGoal(
                                    action.withStatus(if (isChecked) GoalStatus.COMPLETED else GoalStatus.ACTIVE)
                                ))
                            },
                            onClick = { component.onIntent(MilestoneDetailContract.Intent.OnGoalClick(action.id, "ACTION")) }
                        )
                    }
                }
            }
        }

        if (state.challenges.isNotEmpty()) {
            item {
                W2TCard {
                    W2TSectionTitle(strings.activeChallengesCount.format(state.challenges.size))
                    state.challenges.forEach { challenge ->
                        W2TChallengeCard(
                            title = challenge.title,
                            goalTitle = goal.title,
                            description = challenge.desc,
                            status = if (challenge.status == GoalStatus.COMPLETED) strings.finished else strings.ongoing,
                            modifier = Modifier.clickable { 
                                component.onIntent(MilestoneDetailContract.Intent.OnChallengeClick(challenge.id))
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }

        item {
            Button(
                onClick = { component.onIntent(MilestoneDetailContract.Intent.CreateAction) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent.copy(alpha = 0.1f), contentColor = colors.accent),
                shape = RoundedCornerShape(28.dp),
                elevation = null
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(strings.addAction)
            }
        }

        item {
            TextButton(
                onClick = { component.onIntent(MilestoneDetailContract.Intent.DeleteGoal) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text(strings.deleteMilestone)
            }
        }
        
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}
}
