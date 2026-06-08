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

@Composable
fun TargetCreateScreen(
    component: TargetComponent,
    padding: PaddingValues
) {
    val state by component.state.subscribeAsState()
    
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
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val parentText = if (state.parentId != null) " as child of #${state.parentId}" else ""
        Text("Create New ${selectedTier.name} Goal$parentText", style = MaterialTheme.typography.headlineMedium)
        
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        
        Text("Goal Tier", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            GoalTier.entries.forEach { tier ->
                FilterChip(
                    selected = selectedTier == tier,
                    onClick = { selectedTier = tier },
                    label = { Text(tier.name) }
                )
            }
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
            modifier = Modifier.fillMaxWidth(),
            enabled = title.isNotBlank()
        ) {
            Icon(Icons.Default.Done, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Create Target")
        }
    }
}
