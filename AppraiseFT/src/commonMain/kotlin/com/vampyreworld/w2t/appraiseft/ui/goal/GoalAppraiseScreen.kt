package com.vampyreworld.w2t.appraiseft.ui.goal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.appraiseft.AppraiseContract
import com.vampyreworld.w2t.appraiseft.component.AppraiseComponent
import com.vampyreworld.w2t.sharedui.catalog.W2TCard
import com.vampyreworld.w2t.sharedui.catalog.W2TProgressBar
import com.vampyreworld.w2t.sharedui.catalog.W2TSectionTitle
import com.vampyreworld.w2t.sharedui.catalog.W2THeader
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalAppraiseScreen(component: AppraiseComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(AppraiseContract.Intent.OnBackClicked) }) {
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            W2THeader(
                title = strings.appraiseGoal,
                subtitle = state.goal?.title ?: strings.unknown,
                avatarText = "📈"
            )

            // Goal Summary Card
            W2TCard {
                W2TSectionTitle(strings.goalSummary)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = state.goal?.title ?: strings.unknown,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    W2TProgressBar(progress = 0.7f)
                    Text(
                        text = strings.percentComplete.format(70),
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.accent,
                        modifier = Modifier.align(androidx.compose.ui.Alignment.End)
                    )
                    Text(
                        text = strings.goalSummaryPrompt,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.muted,
                        lineHeight = 20.sp
                    )
                }
            }

            // Reflection Card
            W2TCard {
                W2TSectionTitle(strings.yourReflection)
                OutlinedTextField(
                    value = state.reflection,
                    onValueChange = { component.onIntent(AppraiseContract.Intent.OnReflectionChanged(it)) },
                    placeholder = { Text(strings.reflectionPlaceholder) },
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

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { component.onIntent(AppraiseContract.Intent.OnCompleteGoalClicked) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.success),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        strings.markAsComplete,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
                OutlinedButton(
                    onClick = { component.onIntent(AppraiseContract.Intent.OnArchiveGoalClicked) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.muted),
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(colors.border))
                ) {
                    Text(
                        strings.archiveGoal,
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
