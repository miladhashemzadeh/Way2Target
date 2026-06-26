package com.vampyreworld.w2t.solutionft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.vampyreworld.w2t.domain.data.model.SolutionType
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.solutionft.SolutionContract
import com.vampyreworld.w2t.solutionft.SolutionComponent
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings
import coil3.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionScreen(component: SolutionComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current
    var expandedTypes by remember { mutableStateOf(false) }
    var showAdvanced by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        component.sideEffects.collect { sideEffect ->
            when (sideEffect) {
                is SolutionContract.SideEffect.ShowError -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
                }
                is SolutionContract.SideEffect.ShowSuccess -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
                }
                SolutionContract.SideEffect.Back -> {
                    // Handled by onBack callback
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(SolutionContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.goBack)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { component.onIntent(SolutionContract.Intent.OnSaveClicked) },
                containerColor = colors.accent,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Solution")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                W2THeader(
                    title = strings.newSolution,
                    subtitle = strings.defineStrategy,
                    avatarText = state.userName.take(1).uppercase().ifEmpty { "S" },
                    avatarUrl = state.avatarUrl
                )

                Spacer(modifier = Modifier.height(16.dp))

                W2TCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            strings.solutionDetails,
                            style = MaterialTheme.typography.titleSmall,
                            color = colors.accent
                        )
                        OutlinedTextField(
                            value = state.title,
                            onValueChange = { component.onIntent(SolutionContract.Intent.OnTitleChanged(it)) },
                            placeholder = { Text(strings.solutionTitlePlaceholder) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colors.accent,
                                unfocusedBorderColor = colors.border
                            )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showAdvanced = !showAdvanced }
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (showAdvanced) strings.hideCosts else strings.customizeCosts,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = colors.accent
                            )
                        }

                        AnimatedVisibility(
                            visible = showAdvanced,
                            enter = expandVertically() + fadeIn(),
                            exit = shrinkVertically() + fadeOut()
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                OutlinedTextField(
                                    value = state.description,
                                    onValueChange = { component.onIntent(SolutionContract.Intent.OnDescriptionChanged(it)) },
                                    placeholder = { Text(strings.detailedDescription) },
                                    modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = colors.accent,
                                        unfocusedBorderColor = colors.border
                                    )
                                )

                                ExposedDropdownMenuBox(
                                    expanded = expandedTypes,
                                    onExpandedChange = { expandedTypes = it }
                                ) {
                                    OutlinedTextField(
                                        value = state.solutionType.name.lowercase().replace("_", " ").replaceFirstChar { it.uppercase() },
                                        onValueChange = {},
                                        readOnly = true,
                                        label = { Text(strings.strategyType) },
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
                                                text = { Text(type.name.lowercase().replace("_", " ").replaceFirstChar { it.uppercase() }) },
                                                onClick = {
                                                    component.onIntent(SolutionContract.Intent.OnSolutionTypeChanged(type))
                                                    expandedTypes = false
                                                }
                                            )
                                        }
                                    }
                                }

                                Text(
                                    strings.strategicStrength,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = colors.accent,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Slider(
                                        value = state.aidStrength.toFloat(),
                                        onValueChange = { component.onIntent(SolutionContract.Intent.OnAidStrengthChanged(it.toInt())) },
                                        valueRange = 0f..100f,
                                        modifier = Modifier.weight(1f),
                                        colors = SliderDefaults.colors(thumbColor = colors.accent, activeTrackColor = colors.accent)
                                    )
                                    Text(
                                        text = "${state.aidStrength}%",
                                        modifier = Modifier.width(48.dp),
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.End
                                    )
                                }

                                Text(
                                    strings.estimatedCosts,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = colors.accent,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                CostSlider(label = strings.energy, value = state.energyCost, onValueChange = { component.onIntent(SolutionContract.Intent.OnEnergyCostChanged(it)) }, colors = colors)
                                CostSlider(label = strings.time, value = state.timeCost, onValueChange = { component.onIntent(SolutionContract.Intent.OnTimeCostChanged(it)) }, colors = colors)
                                CostSlider(label = strings.money, value = state.moneyCost, onValueChange = { component.onIntent(SolutionContract.Intent.OnMoneyCostChanged(it)) }, colors = colors)
                            }
                        }

                        Button(
                            onClick = { component.onIntent(SolutionContract.Intent.OnSaveClicked) },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                            enabled = state.title.isNotBlank() && !state.isLoading
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                            } else {
                                Text(strings.saveSolution)
                            }
                        }
                    }
                }
            }

            if (state.solutions.isEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        strings.understandingSolutions,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                
                item {
                    W2TOnboardingItem(
                        icon = "💡",
                        title = strings.formulateStrategies,
                        description = strings.formulateStrategiesDesc,
                        iconBackgroundColor = colors.accent.copy(alpha = 0.85f)
                    )
                }

                item {
                    W2TOnboardingItem(
                        icon = "📊",
                        title = strings.balanceCosts,
                        description = strings.balanceCostsDesc,
                        iconBackgroundColor = colors.purple.copy(alpha = 0.85f)
                    )
                }
            } else {
                item {
                    Text(
                        strings.existingSolutions,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(state.solutions.size) { index ->
                    val solution = state.solutions[index]
                    W2TSolutionItem(
                        title = solution.title,
                        source = solution.solutionType.name.lowercase().replace("_", " ").replaceFirstChar { it.uppercase() },
                        isAi = false // Can be determined by logic if needed
                    )
                }
            }

            if (state.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = colors.accent)
                    }
                }
            }
        }
    }
}

@Composable
private fun CostSlider(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    colors: com.vampyreworld.w2t.sharedui.theme.color.AppColorScheme
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, style = MaterialTheme.typography.labelMedium, color = colors.muted)
            Text("$value", style = MaterialTheme.typography.labelSmall, color = colors.muted)
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(thumbColor = colors.accent, activeTrackColor = colors.accent)
        )
    }
}
