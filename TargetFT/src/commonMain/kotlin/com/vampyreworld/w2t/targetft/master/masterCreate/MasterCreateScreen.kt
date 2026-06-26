package com.vampyreworld.w2t.targetft.master.masterCreate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.sharedui.catalog.W2TCard
import com.vampyreworld.w2t.sharedui.catalog.W2THeader
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings
import androidx.compose.foundation.horizontalScroll

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MasterCreateScreen(
    component: MasterCreateContract.Component,
    padding: PaddingValues
) {
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current
    val scrollState = rememberScrollState()
    
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf("🎯") }
    var isLifeGoal by remember { mutableStateOf(false) }
    var showAdvanced by remember { mutableStateOf(false) }

    val icons = listOf("💻", "📈", "🧘‍♀️", "📚", "💰", "🚀", "🎨", "🏡", "🎯", "✨", "🏃")
    val suggestions = listOf(
        strings.presetCodeLabel to (strings.presetCodeTitle to "💻"),
        strings.presetWorkoutLabel to (strings.presetWorkoutTitle to "🏃"),
        strings.presetGroceriesLabel to (strings.presetGroceriesTitle to "💰"),
        strings.presetReadLabel to (strings.presetReadTitle to "📚"),
        strings.presetReviewLabel to (strings.presetReviewTitle to "🚀"),
        strings.presetMeditateLabel to (strings.presetMeditateTitle to "🧘‍♀️")
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(MasterCreateContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.goBack)
                    }
                },
                colors = com.vampyreworld.w2t.sharedui.catalog.w2tTopAppBarColors()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            W2THeader(
                title = strings.createMasterGoal,
                subtitle = strings.masterGoalDesc,
                avatarText = "🎯"
            )

        W2TCard {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column {
                    Text(
                        text = strings.goalTitle,
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text(strings.challengeTitlePlaceholder) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.accent,
                            unfocusedBorderColor = colors.border,
                            unfocusedContainerColor = colors.bgLight.copy(alpha = 0.5f)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        suggestions.forEach { (label, pair) ->
                            val (sTitle, sIcon) = pair
                            SuggestionChip(
                                onClick = {
                                    title = sTitle
                                    selectedIcon = sIcon
                                },
                                label = { Text(label, style = MaterialTheme.typography.labelMedium) }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = strings.lifeGoalLabel,
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.muted,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = strings.isThisLongTermAmbition,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.muted.copy(alpha = 0.7f)
                        )
                    }
                    Switch(
                        checked = isLifeGoal,
                        onCheckedChange = { isLifeGoal = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = colors.accent)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showAdvanced = !showAdvanced }
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (showAdvanced) strings.hideOptionalDetails else strings.customizeIconDescription,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = colors.accent
                    )
                }

                AnimatedVisibility(
                    visible = showAdvanced,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column {
                            Text(
                                text = strings.description,
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.muted,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                placeholder = { Text(strings.whatDoesAchievingMean) },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 4,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colors.accent,
                                    unfocusedBorderColor = colors.border,
                                    unfocusedContainerColor = colors.bgLight.copy(alpha = 0.5f)
                                )
                            )
                        }

                        Column {
                            Text(
                                text = strings.selectIcon,
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.muted,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                icons.forEach { icon ->
                                    val isSelected = selectedIcon == icon
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(RoundedCornerShape(14.dp))
                                            .background(if (isSelected) colors.accent.copy(alpha = 0.1f) else colors.bgLight)
                                            .border(2.dp, if (isSelected) colors.accent else colors.border, RoundedCornerShape(14.dp))
                                            .clickable { selectedIcon = icon },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = icon, fontSize = 24.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = { 
                if (title.isNotBlank()) {
                    val finalTitle = if (title.startsWith(selectedIcon)) title else "$selectedIcon $title"
                    component.onIntent(MasterCreateContract.Intent.OnSaveGoal(finalTitle, description, isLifeGoal))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = title.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(strings.createMasterGoal, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}
}
