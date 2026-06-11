package com.vampyreworld.w2t.targetft.detailActionGoal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import androidx.compose.ui.tooling.preview.Preview
import com.vampyreworld.w2t.sharedui.theme.W2TTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionGoalDetailScreen(component: ActionGoalDetailComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val actionGoal = state.actionGoal

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(ActionGoalDetailContract.Intent.OnBackClicked) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        if (actionGoal == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (state.isLoading) CircularProgressIndicator() else Text("Action Goal not found")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    Column {
                        Text(
                            text = actionGoal.title,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            W2TStatusChip(
                                text = if (actionGoal.status == GoalStatus.COMPLETED) "Completed" else "Pending",
                                backgroundColor = if (actionGoal.status == GoalStatus.COMPLETED) colors.success else colors.challengeColor
                            )
                            state.milestone?.let { milestone ->
                                Text(
                                    text = "Milestone: ${milestone.title} ›",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = colors.muted,
                                    modifier = Modifier.clickable {
                                        component.onIntent(ActionGoalDetailContract.Intent.OnGoalLinkClick(milestone.id))
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = actionGoal.description.ifEmpty { "No description provided." },
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                item {
                    W2TCard {
                        W2TSectionTitle("Details")
                        W2TDetailRow(
                            label = "Master Goal",
                            value = state.masterGoal?.title ?: "Not set",
                            onClick = state.masterGoal?.id?.let { id ->
                                { component.onIntent(ActionGoalDetailContract.Intent.OnGoalLinkClick(id)) }
                            }
                        )
                        W2TDetailRow(
                            label = "Milestone",
                            value = state.milestone?.title ?: "Not set",
                            onClick = state.milestone?.id?.let { id ->
                                { component.onIntent(ActionGoalDetailContract.Intent.OnGoalLinkClick(id)) }
                            }
                        )
                        W2TDetailRow(label = "Due Date", value = "Tomorrow, 5:00 PM")
                        W2TDetailRow(
                            label = "Reminders",
                            value = if (actionGoal.notificationEnabled) "30 min before, 1 hr before" else "Disabled"
                        )
                        W2TDetailRow(label = "Effort Level", value = "Medium")
                    }
                }

                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(
                            onClick = { component.onIntent(ActionGoalDetailContract.Intent.OnMarkCompleteClicked) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colors.success),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Text(
                                "Mark as Complete",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        Button(
                            onClick = { component.onIntent(ActionGoalDetailContract.Intent.OnEditClicked) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Text(
                                "Edit Action",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ActionGoalDetailScreenPreview() {
    W2TTheme {
        // Mock component would go here for preview
    }
}
