package com.vampyreworld.w2t.solutionft.ui.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.SolutionType
import com.vampyreworld.w2t.sharedui.catalog.W2TCard
import com.vampyreworld.w2t.sharedui.catalog.W2TSectionTitle
import com.vampyreworld.w2t.sharedui.catalog.W2TSelectableItem
import com.vampyreworld.w2t.sharedui.catalog.W2THeader
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionCreateScreen(component: SolutionCreateComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current
    var expandedTypes by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(SolutionCreateContract.Intent.OnBackClicked) }) {
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            W2THeader(
                title = strings.addNewSolution,
                subtitle = strings.defineStrategy,
                avatarText = "💡"
            )
            Text(
                text = strings.selectSource,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SolutionCreateContract.SolutionSource.values().forEach { source ->
                    val isSelected = state.source == source
                    OutlinedButton(
                        onClick = { component.onIntent(SolutionCreateContract.Intent.OnSourceChanged(source)) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isSelected) colors.accent.copy(alpha = 0.1f) else Color.Transparent,
                            contentColor = if (isSelected) colors.accent else colors.muted
                        ),
                        border = if (isSelected) ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(colors.accent)) else ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text(
                            text = when(source) {
                                SolutionCreateContract.SolutionSource.MY_IDEA -> strings.myIdea
                                SolutionCreateContract.SolutionSource.AI_ASSISTANT -> strings.ai
                                SolutionCreateContract.SolutionSource.EXTERNAL_RESOURCE -> strings.external
                            },
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            if (state.source == SolutionCreateContract.SolutionSource.MY_IDEA) {
                W2TCard {
                    W2TSectionTitle(strings.solutionDetails)
                    
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                strings.solutionDescription,
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.muted
                            )
                            OutlinedTextField(
                                value = state.description,
                                onValueChange = { component.onIntent(SolutionCreateContract.Intent.OnDescriptionChanged(it)) },
                                placeholder = { Text(strings.solutionDescriptionPlaceholder) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 120.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = colors.accent,
                                    unfocusedBorderColor = colors.border
                                )
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                strings.strategyType,
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.muted
                            )
                            ExposedDropdownMenuBox(
                                expanded = expandedTypes,
                                onExpandedChange = { expandedTypes = it }
                            ) {
                                OutlinedTextField(
                                    value = state.solutionType.getLocalizedName(strings),
                                    onValueChange = {},
                                    readOnly = true,
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    shape = RoundedCornerShape(12.dp),
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTypes) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = colors.accent,
                                        unfocusedBorderColor = colors.border
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedTypes,
                                    onDismissRequest = { expandedTypes = false }
                                ) {
                                    SolutionType.values().forEach { type ->
                                        DropdownMenuItem(
                                            text = { Text(type.getLocalizedName(strings)) },
                                            onClick = {
                                                component.onIntent(SolutionCreateContract.Intent.OnSolutionTypeChanged(type))
                                                expandedTypes = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Button(
                    onClick = { component.onIntent(SolutionCreateContract.Intent.OnAddSolutionClicked) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colors.success),
                    shape = RoundedCornerShape(28.dp),
                    enabled = !state.isLoading && state.description.isNotBlank()
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(
                            strings.addSolution,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = if (state.source == SolutionCreateContract.SolutionSource.AI_ASSISTANT) 
                            strings.aiSolutionNote 
                            else strings.externalSolutionNote,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.muted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}

private fun SolutionType.getLocalizedName(strings: com.vampyreworld.w2t.sharedui.localization.AppStrings): String = when (this) {
    SolutionType.AVOIDANCE -> strings.solTypeAvoidance
    SolutionType.DIRECT_CONFRONTATION -> strings.solTypeDirectConfrontation
    SolutionType.EDIT_STRUCTURAL -> strings.solTypeEditStructural
    SolutionType.DIVIDING_AND_CONQUER -> strings.solTypeDividingAndConquer
    SolutionType.SUBSTITUTION -> strings.solTypeSubstitution
    SolutionType.DELEGATE -> strings.solTypeDelegate
    SolutionType.PLANNING -> strings.solTypePlanning
    SolutionType.EMOTIONAL_REGULATION -> strings.solTypeEmotionalRegulation
    SolutionType.HELP -> strings.solTypeHelp
    SolutionType.TRY_AND_FAIL -> strings.solTypeTryAndFail
}
