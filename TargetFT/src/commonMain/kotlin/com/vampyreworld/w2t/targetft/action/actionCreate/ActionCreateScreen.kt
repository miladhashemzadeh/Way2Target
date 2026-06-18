package com.vampyreworld.w2t.targetft.action.actionCreate

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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.sharedui.catalog.W2TCard
import com.vampyreworld.w2t.sharedui.catalog.W2THeader
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@Composable
fun ActionCreateScreen(
    component: ActionCreateContract.Component,
    padding: PaddingValues
) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val scrollState = rememberScrollState()
    
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var completionCriteria by remember { mutableStateOf("") }
    var energyCost by remember { mutableStateOf(50f) }
    var timeCost by remember { mutableStateOf(50f) }
    var moneyCost by remember { mutableStateOf(50f) }
    var selectedIcon by remember { mutableStateOf("🎯") }

    val icons = listOf("💻", "📈", "🧘‍♀️", "📚", "💰", "🚀", "🎨", "🏡", "🎯", "✨", "🏃")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        W2THeader(
            title = "Create Action Goal",
            subtitle = if (state.parentId != null) "As child of Target #${state.parentId}" else "Define your concrete action",
            avatarText = "+"
        )

        W2TCard {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column {
                    Text(
                        text = "Goal Title",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("e.g., Study 30 min/day") },
                        modifier = Modifier.fillMaxWidth(),
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
                        text = "Description",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("How will you do this?") },
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
                        text = "Completion Criteria",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = completionCriteria,
                        onValueChange = { completionCriteria = it },
                        placeholder = { Text("What does 'done' look like?") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.accent,
                            unfocusedBorderColor = colors.border,
                            unfocusedContainerColor = colors.bgLight.copy(alpha = 0.5f)
                        )
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Estimated Costs (0-100)",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    CostSlider(label = "Energy", value = energyCost, onValueChange = { energyCost = it }, colors = colors)
                    CostSlider(label = "Time", value = timeCost, onValueChange = { timeCost = it }, colors = colors)
                    CostSlider(label = "Money", value = moneyCost, onValueChange = { moneyCost = it }, colors = colors)
                }

                Column {
                    Text(
                        text = "Select an Icon",
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

        Button(
            onClick = { 
                if (title.isNotBlank()) {
                    component.onIntent(ActionCreateContract.Intent.OnSaveGoal(
                        title = title,
                        description = description,
                        completionCriteria = completionCriteria,
                        energyCost = energyCost.toInt(),
                        timeCost = timeCost.toInt(),
                        moneyCost = moneyCost.toInt()
                    ))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = title.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Create Action Goal", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun CostSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    colors: com.vampyreworld.w2t.sharedui.theme.color.AppColorScheme
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = colors.muted)
            Text(text = value.toInt().toString(), style = MaterialTheme.typography.bodySmall, color = colors.accent)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = colors.accent,
                activeTrackColor = colors.accent,
                inactiveTrackColor = colors.border
            )
        )
    }
}
