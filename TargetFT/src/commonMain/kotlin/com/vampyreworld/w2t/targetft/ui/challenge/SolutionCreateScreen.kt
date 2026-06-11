package com.vampyreworld.w2t.targetft.ui.challenge

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Challenges
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.targetft.master.MasterComponent

@Composable
fun SolutionCreateScreen(
    component: MasterComponent,
    challenge: Challenges,
    padding: PaddingValues
) {
    val colors = LocalAppColorScheme.current
    val scrollState = rememberScrollState()
    
    var description by remember { mutableStateOf("") }
    var selectedSource by remember { mutableStateOf("My Idea") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        W2THeader(
            title = "Add New Solution",
            subtitle = "for \"${challenge.title}\"",
            avatarText = "💡"
        )

        W2TCard {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column {
                    Text(
                        text = "Solution Description",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("Suggest a new approach, resource, or strategy...") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Column {
                    Text(
                        text = "Source",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        OutlinedTextField(
                            value = selectedSource,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { IconButton(onClick = { expanded = true }) { Icon(androidx.compose.material.icons.Icons.Default.ArrowDropDown, null) } },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            listOf("My Idea", "AI Assistant", "External Resource").forEach { source ->
                                DropdownMenuItem(text = { Text(source) }, onClick = { selectedSource = source; expanded = false })
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = { /* Save solution */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.success),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Add Solution", fontWeight = FontWeight.Bold)
        }
    }
}
