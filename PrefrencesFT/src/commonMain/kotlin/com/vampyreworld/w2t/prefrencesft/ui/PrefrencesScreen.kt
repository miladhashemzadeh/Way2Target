package com.vampyreworld.w2t.prefrencesft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.prefrencesft.PrefrencesComponent
import com.vampyreworld.w2t.prefrencesft.PrefrencesContract
import com.vampyreworld.w2t.sharedui.catalog.W2TCard
import com.vampyreworld.w2t.sharedui.catalog.W2TBottomNavigation
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrefrencesScreen(component: PrefrencesComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Settings", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(PrefrencesContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            W2TBottomNavigation(
                onHomeClick = { component.onNavigateToHome() },
                onProfileClick = { component.onNavigateToProfile() },
                onChallengesClick = { component.onNavigateToSChallenge() },
                onSettingsClick = { },
                selectedTab = 3
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            W2TCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Dark Mode",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Switch(
                        checked = state.isDarkMode,
                        onCheckedChange = { component.onIntent(PrefrencesContract.Intent.OnThemeChanged(it)) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { component.onIntent(PrefrencesContract.Intent.OnBackClicked) },
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text("Go Back", fontWeight = FontWeight.Bold)
            }
        }
    }
}

