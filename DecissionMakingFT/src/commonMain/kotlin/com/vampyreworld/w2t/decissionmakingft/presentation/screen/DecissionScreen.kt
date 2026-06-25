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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecissionScreen(
    title: String,
    onBack: () -> Unit,
    onMakeDecission: () -> Unit
) {
    val colors = LocalAppColorScheme.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                    title = "Decision Analysis",
                    subtitle = "Navigate complex choices with confidence using structured logic and AI insights.",
                    avatarText = "D",
                    avatarUrl = null
                )
            }

            item {
                Text(
                    text = "How it works:",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                W2TOnboardingItem(
                    icon = "🤔",
                    title = "1. Define the Dilemma",
                    description = "Clearly formulate the choice you need to make or options you want to weigh.",
                    iconBackgroundColor = colors.accent.copy(alpha = 0.85f)
                )
            }

            item {
                W2TOnboardingItem(
                    icon = "⚖️",
                    title = "2. Weigh the Options",
                    description = "Identify pros, cons, and custom weight values for each alternative solution.",
                    iconBackgroundColor = colors.purple.copy(alpha = 0.85f)
                )
            }

            item {
                W2TOnboardingItem(
                    icon = "🤖",
                    title = "3. AI Strategy Recommendations",
                    description = "Receive weighted scores and strategic guidance to target the best possible path.",
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
                        "Start Decision Analysis",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

