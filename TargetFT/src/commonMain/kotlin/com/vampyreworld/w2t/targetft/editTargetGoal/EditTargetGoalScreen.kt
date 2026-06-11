package com.vampyreworld.w2t.targetft.editTargetGoal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTargetGoalScreen(component: EditTargetGoalComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    var expandedMilestones by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Define Steps",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(EditTargetGoalContract.Intent.OnBackClicked) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "for \"${state.masterGoal?.title ?: ""}\"",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.muted,
                modifier = Modifier.padding(top = -16.dp, start = 8.dp)
            )

            // Milestones Card
            W2TCard {
                W2TSectionTitle("Milestones")
                OutlinedTextField(
                    value = state.newMilestoneTitle,
                    onValueChange = { component.onIntent(EditTargetGoalContract.Intent.OnNewMilestoneTitleChanged(it)) },
                    placeholder = { Text("e.g., Complete Python Basics") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.accent,
                        unfocusedBorderColor = colors.border
                    )
                )
                Button(
                    onClick = { component.onIntent(EditTargetGoalContract.Intent.OnAddMilestoneClicked) },
                    modifier = Modifier.padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Milestone")
                }
                
                if (state.milestones.isNotEmpty()) {
                    Column(modifier = Modifier.padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        state.milestones.forEach { milestone ->
                            W2TRemovableItem(
                                icon = "✨",
                                title = milestone.title,
                                onRemove = { component.onIntent(EditTargetGoalContract.Intent.OnRemoveGoalClicked(milestone.id)) }
                            )
                        }
                    }
                }
            }

            // Action Goals Card
            W2TCard {
                W2TSectionTitle("Action Goals")
                OutlinedTextField(
                    value = state.newActionTitle,
                    onValueChange = { component.onIntent(EditTargetGoalContract.Intent.OnNewActionTitleChanged(it)) },
                    placeholder = { Text("e.g., Build a simple calculator app") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.accent,
                        unfocusedBorderColor = colors.border
                    )
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text("Assign to Milestone", style = MaterialTheme.typography.labelLarge, color = colors.muted)
                ExposedDropdownMenuBox(
                    expanded = expandedMilestones,
                    onExpandedChange = { expandedMilestones = it }
                ) {
                    OutlinedTextField(
                        value = state.milestones.find { it.id == state.selectedMilestoneIdForAction }?.title ?: "Select a Milestone",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMilestones) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.accent,
                            unfocusedBorderColor = colors.border
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedMilestones,
                        onDismissRequest = { expandedMilestones = false }
                    ) {
                        state.milestones.forEach { milestone ->
                            DropdownMenuItem(
                                text = { Text(milestone.title) },
                                onClick = {
                                    component.onIntent(EditTargetGoalContract.Intent.OnMilestoneSelectedForAction(milestone.id))
                                    expandedMilestones = false
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = { component.onIntent(EditTargetGoalContract.Intent.OnAddActionClicked) },
                    modifier = Modifier.padding(top = 24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Action")
                }

                if (state.actions.isNotEmpty()) {
                    Column(modifier = Modifier.padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        state.actions.forEach { action ->
                            W2TRemovableItem(
                                icon = "✅",
                                title = action.title,
                                iconColor = colors.success,
                                onRemove = { component.onIntent(EditTargetGoalContract.Intent.OnRemoveGoalClicked(action.id)) }
                            )
                        }
                    }
                }
            }

            Button(
                onClick = { component.onIntent(EditTargetGoalContract.Intent.OnFinishClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.success),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text("Finish Goal Setup", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
