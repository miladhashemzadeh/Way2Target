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
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.remember


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrefrencesScreen(component: PrefrencesComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(strings.settings, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Dark Mode row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = strings.darkMode,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = state.isDarkMode,
                            onCheckedChange = { component.onIntent(PrefrencesContract.Intent.OnThemeChanged(it)) }
                        )
                    }

                    HorizontalDivider(color = colors.border.copy(alpha = 0.5f))

                    // Language Selector
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = strings.language,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            val isEnglish = state.language == "en"
                            val isFarsi = state.language == "fa"

                            val englishBgColor by animateColorAsState(
                                targetValue = if (isEnglish) colors.accent else colors.bgLight,
                                animationSpec = tween(durationMillis = 250)
                            )
                            val englishContentColor by animateColorAsState(
                                targetValue = if (isEnglish) Color.White else MaterialTheme.colorScheme.onBackground,
                                animationSpec = tween(durationMillis = 250)
                            )

                            val farsiBgColor by animateColorAsState(
                                targetValue = if (isFarsi) colors.accent else colors.bgLight,
                                animationSpec = tween(durationMillis = 250)
                            )
                            val farsiContentColor by animateColorAsState(
                                targetValue = if (isFarsi) Color.White else MaterialTheme.colorScheme.onBackground,
                                animationSpec = tween(durationMillis = 250)
                            )

                            val englishInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                            Button(
                                onClick = { component.onIntent(PrefrencesContract.Intent.OnLanguageChanged("en")) },
                                interactionSource = englishInteractionSource,
                                modifier = Modifier.weight(1f).bounce(englishInteractionSource),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = englishBgColor,
                                    contentColor = englishContentColor
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(strings.english, fontWeight = if (isEnglish) FontWeight.Bold else FontWeight.Normal)
                            }

                            val farsiInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                            Button(
                                onClick = { component.onIntent(PrefrencesContract.Intent.OnLanguageChanged("fa")) },
                                interactionSource = farsiInteractionSource,
                                modifier = Modifier.weight(1f).bounce(farsiInteractionSource),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = farsiBgColor,
                                    contentColor = farsiContentColor
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(strings.farsi, fontWeight = if (isFarsi) FontWeight.Bold else FontWeight.Normal)
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            val backInteractionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
            Button(
                onClick = { component.onIntent(PrefrencesContract.Intent.OnBackClicked) },
                interactionSource = backInteractionSource,
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp).bounce(backInteractionSource)
            ) {
                Text(strings.goBack, fontWeight = FontWeight.Bold)
            }
        }
    }
}


