package com.vampyreworld.w2t.shomeft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings
import com.vampyreworld.w2t.shomeft.HomeComponent
import com.vampyreworld.w2t.shomeft.HomeContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current
    var showDeleteSheet by remember { mutableStateOf<Goal?>(null) }
    var deleteGoalName by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        component.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is HomeContract.SideEffect.ShowToast -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            W2TBottomNavigation(component)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { component.onIntent(HomeContract.Intent.CreateMasterGoal) },
                containerColor = colors.accent,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(32.dp))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                val greetingText = if (strings.english == "English") {
                    "${strings.morningGreeting}, ${state.userName}!"
                } else {
                    "${strings.morningGreeting}، ${state.userName}!"
                }
                W2THeader(
                    title = greetingText,
                    subtitle = strings.readyForGoals,
                    avatarText = state.userName.take(1).uppercase(),
                    avatarUrl = state.avatarUrl
                )
            }

            item {
                W2TMoodWidget(
                    title = strings.howAreYouFeeling,
                    description = strings.moodCheckDesc,
                    buttonText = strings.checkMood,
                    onButtonClick = { component.onIntent(HomeContract.Intent.OnCheckMoodClick) }
                )
            }

            item {
                W2TCard {
                    W2TSectionTitle(strings.masterGoals)
                    if (state.masterGoals.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = strings.defineVision,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = strings.breakdownPath,
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.muted
                            )

                            // Step-by-step showcase
                            val steps = listOf(
                                Triple("🎯", strings.masterGoal, strings.masterGoalDesc),
                                Triple("📍", strings.milestones, strings.milestonesDesc),
                                Triple("⚡", strings.actions, strings.actionsDesc)
                            )

                            steps.forEach { (icon, stepTitle, stepDesc) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(colors.accent.copy(alpha = 0.12f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(icon, style = MaterialTheme.typography.titleMedium)
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = stepTitle,
                                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = stepDesc,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = colors.muted
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Button(
                                onClick = { component.onIntent(HomeContract.Intent.CreateMasterGoal) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(vertical = 12.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(strings.createMasterGoal, fontWeight = FontWeight.Bold)
                            }
                        }
                    } else {
                        state.masterGoals.forEach { goal ->
                            val (icon, cleanTitle) = goal.title.extractIcon()
                            W2TGoalItem(
                                icon = icon.ifEmpty { "🎯" },
                                title = cleanTitle,
                                progress = 0.7f, // Mock progress for now
                                progressText = strings.percentComplete.format(70),
                                onClick = { component.onIntent(HomeContract.Intent.OnMasterGoalClick(goal.id)) }
                            )
                        }
                    }
                }
            }

            item {
                W2TCard {
                    W2TSectionTitle(strings.todayActions)
                    if (state.todayActions.isEmpty()) {
                        Column(
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(vertical = 16.dp),
                             horizontalAlignment = Alignment.CenterHorizontally,
                             verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "🌴",
                                fontSize = 36.sp,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                            Text(
                                text = strings.allCaughtUp,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = strings.noActionsToday,
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.muted,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    } else {
                        state.todayActions.forEach { action ->
                            val (icon, cleanTitle) = action.title.extractIcon()
                            W2TActionItem(
                                title = if (icon.isNotEmpty()) "$icon $cleanTitle" else cleanTitle,
                                subtitle = strings.fromYourMilestones,
                                time = "10:00 AM",
                                checked = false,
                                onCheckedChange = { isChecked ->
                                    component.onIntent(HomeContract.Intent.OnActionCheck(action.id, isChecked))
                                }
                            )
                        }
                    }
                }
            }

            item {
                W2TAiInsightsCard(
                    title = strings.aiInsights,
                    description = strings.focus5dayStreak,
                    buttonText = strings.viewStrategy,
                    onButtonClick = { }
                )
            }

            // Adding extra space at the end
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        if (showDeleteSheet != null) {
            val expectedConfirmText = strings.deleteGoalPlaceholder.format(showDeleteSheet?.id)
            ModalBottomSheet(onDismissRequest = { showDeleteSheet = null }) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth().padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(strings.deleteMasterGoalTitle, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        strings.deleteMasterGoalConfirm,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(strings.deleteGoalTypeToConfirm.format(showDeleteSheet?.id), style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = deleteGoalName,
                        onValueChange = { deleteGoalName = it },
                        placeholder = { Text(expectedConfirmText) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (deleteGoalName == expectedConfirmText) {
                                component.onIntent(HomeContract.Intent.DeleteMasterGoal(showDeleteSheet!!.id))
                                showDeleteSheet = null
                                deleteGoalName = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        enabled = deleteGoalName == expectedConfirmText,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(strings.deletePermanently)
                    }
                }
            }
        }
    }
}

@Composable
fun W2TBottomNavigation(component: HomeComponent) {
    com.vampyreworld.w2t.sharedui.catalog.W2TBottomNavigation(
        onHomeClick = { },
        onProfileClick = { component.onNavigateToProfile() },
        onChallengesClick = { component.onNavigateToSChallenge() },
        onSettingsClick = { component.onNavigateToPreferences() },
        selectedTab = 0
    )
}
