package com.vampyreworld.w2t.targetft.ui.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.targetft.component.TargetComponent

@Composable
fun GoalStepsCreateScreen(
    component: TargetComponent,
    masterGoal: Goal,
    padding: PaddingValues
) {
    val colors = LocalAppColorScheme.current
    val scrollState = rememberScrollState()
    
    var milestoneTitle by remember { mutableStateOf("") }
    var actionTitle by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        W2THeader(
            title = "Define Steps",
            subtitle = "for \"${masterGoal.title}\"",
            avatarText = "Steps"
        )

        W2TCard {
            W2TSectionTitle("Milestones")
            OutlinedTextField(
                value = milestoneTitle,
                onValueChange = { milestoneTitle = it },
                placeholder = { Text("e.g., Complete Python Basics") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Button(
                onClick = { /* Add Milestone */ },
                modifier = Modifier.padding(top = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("+ Add Milestone")
            }
            
            Column(modifier = Modifier.padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                W2TRemovableItem(icon = "✨", title = "Complete Python Basics", onRemove = {})
                W2TRemovableItem(icon = "✨", title = "Master Data Structures", onRemove = {})
            }
        }

        W2TCard {
            W2TSectionTitle("Action Goals")
            OutlinedTextField(
                value = actionTitle,
                onValueChange = { actionTitle = it },
                placeholder = { Text("e.g., Build a simple calculator app") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            // Dropdown for Milestone assignment and Reminder picker would go here
            Button(
                onClick = { /* Add Action */ },
                modifier = Modifier.padding(top = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text("+ Add Action")
            }

            Column(modifier = Modifier.padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                W2TRemovableItem(icon = "✅", title = "Setup Python environment", onRemove = {}, iconColor = colors.success)
                W2TRemovableItem(icon = "✅", title = "Learn Python syntax", onRemove = {}, iconColor = colors.success)
            }
        }

        Button(
            onClick = { /* Finish setup */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.success),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Finish Goal Setup", fontWeight = FontWeight.Bold)
        }
    }
}
