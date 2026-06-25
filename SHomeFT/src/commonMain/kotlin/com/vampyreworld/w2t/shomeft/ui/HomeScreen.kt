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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.shomeft.HomeComponent
import com.vampyreworld.w2t.shomeft.HomeContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(component: HomeComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
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
                W2THeader(
                    title = "صبح بخیر، ${state.userName}!",
                    subtitle = "آماده‌ای برای رسیدن به اهدافت؟",
                    avatarText = state.userName.take(1).uppercase(),
                    avatarUrl = state.avatarUrl
                )
            }

            item {
                W2TMoodWidget(
                    title = "How are you feeling?",
                    description = "Quick check to optimize your decision-making.",
                    buttonText = "Check Mood",
                    onButtonClick = { component.onIntent(HomeContract.Intent.OnCheckMoodClick) }
                )
            }

            item {
                W2TCard {
                    W2TSectionTitle("Your Master Goals")
                    if (state.masterGoals.isEmpty()) {
                        Text(
                            "No master goals yet. Start by creating one!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.muted,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        state.masterGoals.forEach { goal ->
                            val (icon, cleanTitle) = goal.title.extractIcon()
                            W2TGoalItem(
                                icon = icon.ifEmpty { "🎯" },
                                title = cleanTitle,
                                progress = 0.7f, // Mock progress for now
                                progressText = "70% Complete",
                                onClick = { component.onIntent(HomeContract.Intent.OnMasterGoalClick(goal.id)) }
                            )
                        }
                    }
                }
            }

            item {
                W2TCard {
                    W2TSectionTitle("Today's Actions")
                    if (state.todayActions.isEmpty()) {
                        Text(
                            "No actions for today. Take some rest!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.muted,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        state.todayActions.forEach { action ->
                            val (icon, cleanTitle) = action.title.extractIcon()
                            W2TActionItem(
                                title = if (icon.isNotEmpty()) "$icon $cleanTitle" else cleanTitle,
                                subtitle = "From your milestones",
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
                    title = "AI Insights",
                    description = "Your current progress suggests focusing on practical projects for faster skill acquisition.",
                    buttonText = "View Strategy",
                    onButtonClick = { }
                )
            }

            // Adding extra space at the end
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        if (showDeleteSheet != null) {
            ModalBottomSheet(onDismissRequest = { showDeleteSheet = null }) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth().padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Delete Master Goal?", style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "This action cannot be undone. All milestones and actions will be lost.",
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Type 'Goal #${showDeleteSheet?.id}' to confirm", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = deleteGoalName,
                        onValueChange = { deleteGoalName = it },
                        placeholder = { Text("Goal #${showDeleteSheet?.id}") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (deleteGoalName == "Goal #${showDeleteSheet?.title}") {
                                component.onIntent(HomeContract.Intent.DeleteMasterGoal(showDeleteSheet!!.id))
                                showDeleteSheet = null
                                deleteGoalName = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        enabled = deleteGoalName == "Goal #${showDeleteSheet?.title}",
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Delete Permanently")
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
