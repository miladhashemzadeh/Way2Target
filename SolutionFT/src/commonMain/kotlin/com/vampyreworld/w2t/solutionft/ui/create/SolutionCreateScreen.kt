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
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionCreateScreen(component: SolutionCreateComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    var expandedTypes by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add New Solution",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(SolutionCreateContract.Intent.OnBackClicked) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
            Text(
                text = "Select Source",
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
                                SolutionCreateContract.SolutionSource.MY_IDEA -> "My Idea"
                                SolutionCreateContract.SolutionSource.AI_ASSISTANT -> "AI"
                                SolutionCreateContract.SolutionSource.EXTERNAL_RESOURCE -> "External"
                            },
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            if (state.source == SolutionCreateContract.SolutionSource.MY_IDEA) {
                W2TCard {
                    W2TSectionTitle("Solution Details")
                    
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                "Solution Description",
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.muted
                            )
                            OutlinedTextField(
                                value = state.description,
                                onValueChange = { component.onIntent(SolutionCreateContract.Intent.OnDescriptionChanged(it)) },
                                placeholder = { Text("Suggest a new approach, resource, or strategy.") },
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
                                "Strategy Type",
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.muted
                            )
                            ExposedDropdownMenuBox(
                                expanded = expandedTypes,
                                onExpandedChange = { expandedTypes = it }
                            ) {
                                OutlinedTextField(
                                    value = state.solutionType.name.lowercase().replace("_", " ").replaceFirstChar { it.uppercase() },
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
                                            text = { Text(type.name.lowercase().replace("_", " ").replaceFirstChar { it.uppercase() }) },
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
                            "Add Solution",
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
                            "AI solutions are automatically generated based on the challenge." 
                            else "External resources integration coming soon.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.muted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }
    }
}
