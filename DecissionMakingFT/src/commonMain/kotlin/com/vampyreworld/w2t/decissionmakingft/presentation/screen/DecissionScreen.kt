package com.vampyreworld.w2t.decissionmakingft.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vampyreworld.w2t.sharedui.catalog.W2THeader
import com.vampyreworld.w2t.sharedui.catalog.W2TOnboardingItem
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecissionScreen(
    title: String,
    onBack: () -> Unit,
    onMakeDecission: () -> Unit
) {
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = strings.goBack)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                W2THeader(
                    title = strings.decisionAnalysis,
                    subtitle = strings.decisionDesc,
                    avatarText = "D",
                    avatarUrl = null
                )
            }

            item {
                Text(
                    text = strings.howItWorks,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                W2TOnboardingItem(
                    icon = "🤔",
                    title = strings.defineDilemma,
                    description = strings.defineDilemmaDesc,
                    iconBackgroundColor = colors.accent.copy(alpha = 0.85f)
                )
            }

            item {
                W2TOnboardingItem(
                    icon = "⚖️",
                    title = strings.weighOptions,
                    description = strings.weighOptionsDesc,
                    iconBackgroundColor = colors.purple.copy(alpha = 0.85f)
                )
            }

            item {
                W2TOnboardingItem(
                    icon = "🤖",
                    title = strings.aiStrategy,
                    description = strings.aiStrategyDesc,
                    iconBackgroundColor = colors.greenBlue.copy(alpha = 0.85f)
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onMakeDecission,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Text(
                        strings.startDecision,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

