package com.vampyreworld.w2t.targetft.ui.appraise

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.targetft.master.MasterComponent

@Composable
fun ChallengeAppraiseScreen(
    component: MasterComponent,
    challenge: Challenges,
    padding: PaddingValues
) {
    val colors = LocalAppColorScheme.current
    val scrollState = rememberScrollState()
    
    var selectedStatus by remember { mutableStateOf("Ongoing") }
    var selectedSolutionIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        W2THeader(
            title = "Appraise Challenge",
            subtitle = "\"${challenge.title}\"",
            avatarText = "!"
        )

        W2TCard {
            W2TSectionTitle("Update Status")
            val statuses = listOf("Ongoing", "Finished", "Failed")
            var expanded by remember { mutableStateOf(false) }
            
            Box {
                OutlinedTextField(
                    value = selectedStatus,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { IconButton(onClick = { expanded = true }) { Icon(androidx.compose.material.icons.Icons.Default.ArrowDropDown, null) } },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colors.border
                    )
                )
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    statuses.forEach { status ->
                        DropdownMenuItem(text = { Text(status) }, onClick = { selectedStatus = status; expanded = false })
                    }
                }
            }
        }

        W2TCard {
            W2TSectionTitle("Select Preferred Solution")
            Text(
                text = "Which solution are you actively pursuing or found most helpful?",
                style = MaterialTheme.typography.bodySmall,
                color = colors.muted,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                W2TSelectableItem(
                    title = "Break down into smaller sub-problems",
                    subtitle = "(AI)",
                    selected = selectedSolutionIndex == 0,
                    onClick = { selectedSolutionIndex = 0 }
                )
                W2TSelectableItem(
                    title = "Explore visual tutorials on YouTube",
                    subtitle = "(User)",
                    selected = selectedSolutionIndex == 1,
                    onClick = { selectedSolutionIndex = 1 }
                )
            }
        }

        Button(
            onClick = { /* Update logic */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Update Challenge", fontWeight = FontWeight.Bold)
        }
    }
}
