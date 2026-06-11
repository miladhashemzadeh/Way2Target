package com.vampyreworld.w2t.appraiseft.ui.challenge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.appraiseft.AppraiseContract
import com.vampyreworld.w2t.appraiseft.component.AppraiseComponent
import com.vampyreworld.w2t.sharedui.catalog.W2TCard
import com.vampyreworld.w2t.sharedui.catalog.W2TSectionTitle
import com.vampyreworld.w2t.sharedui.catalog.W2TStatusChip
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeAppraiseScreen(component: AppraiseComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    var expandedStatus by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Appraise Challenge",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(AppraiseContract.Intent.OnBackClicked) }) {
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Challenge Overview
            Column {
                Text(
                    text = state.challenge?.title ?: "Stuck on complex data structures",
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Goal: ${state.goal?.title ?: "Learn Programming"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.muted
                )
                Spacer(modifier = Modifier.height(12.dp))
                W2TStatusChip(
                    text = state.challengeStatus,
                    backgroundColor = when(state.challengeStatus) {
                        "Ongoing" -> colors.challengeColor
                        "Finished" -> colors.success
                        "Failed" -> Color(0xFFD32F2F)
                        else -> colors.challengeColor
                    }
                )
            }

            // Update Status Card
            W2TCard {
                W2TSectionTitle("Update Status")
                ExposedDropdownMenuBox(
                    expanded = expandedStatus,
                    onExpandedChange = { expandedStatus = it }
                ) {
                    OutlinedTextField(
                        value = state.challengeStatus,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedStatus) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.accent,
                            unfocusedBorderColor = colors.border,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expandedStatus,
                        onDismissRequest = { expandedStatus = false }
                    ) {
                        listOf("Ongoing", "Finished", "Failed").forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status) },
                                onClick = {
                                    component.onIntent(AppraiseContract.Intent.OnChallengeStatusChanged(status))
                                    expandedStatus = false
                                }
                            )
                        }
                    }
                }
            }

            // Select Preferred Solution Card
            W2TCard {
                W2TSectionTitle("Select Preferred Solution")
                Text(
                    "Which solution are you actively pursuing or found most helpful?",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.muted,
                    modifier = Modifier.padding(top = -8.dp, bottom = 8.dp)
                )
                
                state.solutions.forEach { solution ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = state.selectedSolutionId == solution.id,
                            onClick = { component.onIntent(AppraiseContract.Intent.OnSolutionSelected(solution.id)) },
                            colors = RadioButtonDefaults.colors(selectedColor = colors.accent)
                        )
                        Text(
                            text = solution.title,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "(User)", // In a real app, this would come from the solution source
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.muted
                        )
                    }
                }
            }

            Button(
                onClick = { component.onIntent(AppraiseContract.Intent.OnUpdateChallengeClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    "Update Challenge",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
