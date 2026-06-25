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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.vampyreworld.w2t.domain.data.model.SolutionType
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.solutionft.SolutionContract
import com.vampyreworld.w2t.solutionft.SolutionComponent
import coil3.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionScreen(component: SolutionComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    var expandedTypes by remember { mutableStateOf(false) }
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
                title = { Text("Solutions") },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(SolutionContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(colors.accent.copy(alpha = 0.1f))
                            .clickable { component.onIntent(SolutionContract.Intent.OnProfileClicked) },
                        contentAlignment = Alignment.Center
                    ) {
                        if (state.avatarUrl != null) {
                            AsyncImage(
                                model = state.avatarUrl,
                                contentDescription = "Profile",
                                modifier = Modifier.fillMaxSize().clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(
                                text = state.userName.take(1).uppercase().ifEmpty { "U" },
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = colors.accent
                            )
                        }
                    }
                }
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
                    title = "New Solution",
                    subtitle = "Define a strategy to overcome your challenge",
                    avatarText = state.userName.take(1).uppercase().ifEmpty { "S" },
                    avatarUrl = state.avatarUrl
                )

                Spacer(modifier = Modifier.height(16.dp))

                W2TCard {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            "Solution Details",
                            style = MaterialTheme.typography.titleSmall,
                            color = colors.accent
                        )
                        OutlinedTextField(
                            value = state.title,
                            onValueChange = { component.onIntent(SolutionContract.Intent.OnTitleChanged(it)) },
                            placeholder = { Text("Solution title...") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colors.accent,
                                unfocusedBorderColor = colors.border
                            )
                        )
                        OutlinedTextField(
                            value = state.description,
                            onValueChange = { component.onIntent(SolutionContract.Intent.OnDescriptionChanged(it)) },
                            placeholder = { Text("Detailed description (optional)...") },
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
                                label = { Text("Strategy Type") },
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
                            "Strategic Strength",
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
                            "Estimated Costs (0-100 scale)",
                            style = MaterialTheme.typography.titleSmall,
                            color = colors.accent,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        CostSlider(label = "Energy", value = state.energyCost, onValueChange = { component.onIntent(SolutionContract.Intent.OnEnergyCostChanged(it)) }, colors = colors)
                        CostSlider(label = "Time", value = state.timeCost, onValueChange = { component.onIntent(SolutionContract.Intent.OnTimeCostChanged(it)) }, colors = colors)
                        CostSlider(label = "Money", value = state.moneyCost, onValueChange = { component.onIntent(SolutionContract.Intent.OnMoneyCostChanged(it)) }, colors = colors)

                        Button(
                            onClick = { component.onIntent(SolutionContract.Intent.OnSaveClicked) },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                            enabled = state.title.isNotBlank() && !state.isLoading
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                            } else {
                                Text("Save Solution")
                            }
                        }
                    }
                }
            }

            if (state.solutions.isNotEmpty()) {
                item {
                    Text(
                        "Existing Solutions",
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
