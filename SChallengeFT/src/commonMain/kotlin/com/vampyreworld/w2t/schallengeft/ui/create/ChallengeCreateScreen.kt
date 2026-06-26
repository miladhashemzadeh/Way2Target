package com.vampyreworld.w2t.schallengeft.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.sharedui.catalog.W2TCard
import com.vampyreworld.w2t.sharedui.catalog.W2THeader
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengeCreateScreen(component: ChallengeCreateComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current
    var expandedGoals by remember { mutableStateOf(false) }
    var expandedImpact by remember { mutableStateOf(false) }

    val getImpactLabel = { level: ChallengeCreateContract.ImpactLevel ->
        when (level) {
            ChallengeCreateContract.ImpactLevel.HIGH -> strings.highImpact
            ChallengeCreateContract.ImpactLevel.MEDIUM -> strings.mediumImpact
            ChallengeCreateContract.ImpactLevel.LOW -> strings.lowImpact
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(ChallengeCreateContract.Intent.OnBackClicked) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = strings.goBack)
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
            W2THeader(
                title = strings.addNewChallenge,
                subtitle = strings.onboardingChallengeDesc,
                avatarText = "⚡"
            )
            W2TCard {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Challenge Title
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            strings.challengeTitle,
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.muted
                        )
                        OutlinedTextField(
                            value = state.title,
                            onValueChange = { component.onIntent(ChallengeCreateContract.Intent.OnTitleChanged(it)) },
                            placeholder = { Text(strings.challengeTitlePlaceholder) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colors.accent,
                                unfocusedBorderColor = colors.border,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }

                    // Description
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            strings.description,
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.muted
                        )
                        OutlinedTextField(
                            value = state.description,
                            onValueChange = { component.onIntent(ChallengeCreateContract.Intent.OnDescriptionChanged(it)) },
                            placeholder = { Text(strings.challengeDescriptionPlaceholder) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 120.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colors.accent,
                                unfocusedBorderColor = colors.border,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }

                    // Link to Goal
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            strings.linkToGoal,
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.muted
                        )
                        ExposedDropdownMenuBox(
                            expanded = expandedGoals,
                            onExpandedChange = { expandedGoals = it }
                        ) {
                            OutlinedTextField(
                                value = state.availableGoals.find { it.id == state.selectedGoalId }?.title ?: strings.selectAGoal,
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                shape = RoundedCornerShape(12.dp),
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGoals) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colors.accent,
                                    unfocusedBorderColor = colors.border
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedGoals,
                                onDismissRequest = { expandedGoals = false }
                            ) {
                                state.availableGoals.forEach { goal ->
                                    DropdownMenuItem(
                                        text = { Text(goal.title) },
                                        onClick = {
                                            component.onIntent(ChallengeCreateContract.Intent.OnGoalSelected(goal.id))
                                            expandedGoals = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Impact Level
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            strings.impactLevel,
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.muted
                        )
                        ExposedDropdownMenuBox(
                            expanded = expandedImpact,
                            onExpandedChange = { expandedImpact = it }
                        ) {
                            OutlinedTextField(
                                value = getImpactLabel(state.impactLevel),
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                shape = RoundedCornerShape(12.dp),
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedImpact) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colors.accent,
                                    unfocusedBorderColor = colors.border
                                )
                            )
                            ExposedDropdownMenu(
                                expanded = expandedImpact,
                                onDismissRequest = { expandedImpact = false }
                            ) {
                                ChallengeCreateContract.ImpactLevel.values().forEach { level ->
                                    DropdownMenuItem(
                                        text = { Text(getImpactLabel(level)) },
                                        onClick = {
                                            component.onIntent(ChallengeCreateContract.Intent.OnImpactLevelSelected(level))
                                            expandedImpact = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Button(
                onClick = { component.onIntent(ChallengeCreateContract.Intent.OnCreateClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.challengeColor),
                shape = RoundedCornerShape(28.dp),
                enabled = !state.isLoading && state.title.isNotBlank()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        strings.createChallenge,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
