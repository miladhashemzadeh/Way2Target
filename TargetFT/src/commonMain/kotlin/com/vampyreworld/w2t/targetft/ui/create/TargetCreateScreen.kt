package com.vampyreworld.w2t.targetft.ui.create

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.GoalTier
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.component.TargetComponent

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Title
import androidx.compose.ui.text.font.FontWeight
import com.vampyreworld.w2t.targetft.ui.components.SectionHeader

@Composable
fun TargetCreateScreen(
    component: TargetComponent,
    padding: PaddingValues
) {
    val state by component.state.subscribeAsState()
    val scrollState = rememberScrollState()
    
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedTier by remember(state.initialTier) { 
        mutableStateOf(
            GoalTier.entries.find { it.name == state.initialTier } ?: GoalTier.MASTER
        ) 
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        val parentText = if (state.parentId != null) " as child of Target #${state.parentId}" else ""
        
        Column {
            Text(
                text = "New ${selectedTier.name} Target", 
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            if (parentText.isNotEmpty()) {
                Text(
                    text = parentText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SectionHeader("Identity")
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("What is your goal?") },
                placeholder = { Text("e.g. Master Android Compose") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Title, contentDescription = null) },
                trailingIcon = if (title.isNotEmpty()) {
                    { IconButton(onClick = { title = "" }) { Icon(Icons.Default.Close, contentDescription = "Clear") } }
                } else null,
                shape = MaterialTheme.shapes.medium,
                singleLine = true
            )
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (Optional)") },
                placeholder = { Text("Explain why this target matters...") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                minLines = 3,
                shape = MaterialTheme.shapes.medium
            )
        }
        
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            SectionHeader("Strategic Tier")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GoalTier.entries.forEach { tier ->
                    val isSelected = selectedTier == tier
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedTier = tier },
                        label = { Text(tier.name) },
                        modifier = Modifier.weight(1f),
                        leadingIcon = if (isSelected) {
                            { Icon(Icons.Default.Done, contentDescription = null, modifier = Modifier.size(16.dp)) }
                        } else null,
                        shape = MaterialTheme.shapes.medium
                    )
                }
            }
            Text(
                text = when(selectedTier) {
                    GoalTier.MASTER -> "A long-term life goal or major objective."
                    GoalTier.MILESTONE -> "A significant step towards a Master goal."
                    GoalTier.ACTION -> "A concrete, repeatable task or immediate step."
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = { 
                if (title.isNotBlank()) {
                    component.onIntent(
                        TargetContract.Intent.OnSaveGoal(
                            title = title,
                            description = description,
                            tier = selectedTier.name
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = title.isNotBlank(),
            shape = MaterialTheme.shapes.large
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text("Create Target", style = MaterialTheme.typography.titleMedium)
        }
    }
}

