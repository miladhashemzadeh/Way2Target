package com.vampyreworld.w2t.moodaddft.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.UserMood
import com.vampyreworld.w2t.moodaddft.MoodAddComponent
import com.vampyreworld.w2t.moodaddft.MoodAddContract
import com.vampyreworld.w2t.moodaddft.ui.components.MoodSlider
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings
import com.vampyreworld.w2t.sharedui.catalog.*
import kotlinx.datetime.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodAddScreen(component: MoodAddComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current
    var energy by remember { mutableStateOf(70f) }
    var focus by remember { mutableStateOf(60f) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        strings.quickCheckIn,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(MoodAddContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.goBack)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                strings.quickCheckIn,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                strings.howsYourMind,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(48.dp))

            if (state.isAlreadyAddedToday) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    strings.alreadyCheckedIn,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    strings.checkInTomorrow,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(48.dp))
                val backInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                Button(
                    onClick = { component.onIntent(MoodAddContract.Intent.OnBackClicked) },
                    interactionSource = backInteractionSource,
                    modifier = Modifier.fillMaxWidth().bounce(backInteractionSource),
                    contentPadding = PaddingValues(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.accent)
                ) {
                    Text(strings.goBack, style = MaterialTheme.typography.titleMedium)
                }
            } else {
                MoodSlider(
                    label = strings.energyLevel,
                    value = energy,
                    onValueChange = { energy = it },
                    icon = Icons.Default.Bolt
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                MoodSlider(
                    label = strings.focusConcentration,
                    value = focus,
                    onValueChange = { focus = it },
                    icon = Icons.Default.Psychology
                )
                
                Spacer(modifier = Modifier.height(48.dp))
                
                val saveInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                Button(
                    onClick = { 
                        component.onIntent(MoodAddContract.Intent.OnAddMood(
                            UserMood(
                                kotlinx.datetime.Clock.System.now().toEpochMilliseconds(), 
                                energy.toInt(), 
                                50, 
                                focus.toInt(), 
                                50, 
                                50
                            )
                        ))
                        component.onIntent(MoodAddContract.Intent.OnBackClicked) 
                    },
                    interactionSource = saveInteractionSource,
                    modifier = Modifier.fillMaxWidth().bounce(saveInteractionSource),
                    contentPadding = PaddingValues(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.accent)
                ) {
                    Text(strings.saveContinue, style = MaterialTheme.typography.titleMedium)
                }

                TextButton(
                    onClick = { component.onIntent(MoodAddContract.Intent.OnBackClicked) },
                    modifier = Modifier.padding(top = 8.dp).bounceClick {
                        component.onIntent(MoodAddContract.Intent.OnBackClicked)
                    }
                ) {
                    Text(strings.doThisLater, color = colors.muted)
                }
            }
        }
    }
}

