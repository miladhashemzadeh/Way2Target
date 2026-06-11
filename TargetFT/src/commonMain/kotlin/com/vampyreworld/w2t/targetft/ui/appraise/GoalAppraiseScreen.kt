package com.vampyreworld.w2t.targetft.ui.appraise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalStatus
import com.vampyreworld.w2t.sharedui.catalog.*
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.targetft.TargetContract
import com.vampyreworld.w2t.targetft.component.TargetComponent

@Composable
fun GoalAppraiseScreen(
    component: TargetComponent,
    goal: Goal,
    padding: PaddingValues
) {
    val colors = LocalAppColorScheme.current
    val scrollState = rememberScrollState()
    var reflection by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        W2THeader(
            title = "Appraise Goal",
            subtitle = "\"${goal.title}\"",
            avatarText = "A"
        )

        W2TCard {
            W2TSectionTitle("Goal Summary")
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = goal.title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                W2TProgressBar(progress = 0.7f, color = colors.accent)
                Text(
                    text = "70% Complete",
                    modifier = Modifier.align(androidx.compose.ui.Alignment.End),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.accent
                )
                Text(
                    text = "You've made significant progress. Keep up the momentum!",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.muted
                )
            }
        }

        W2TCard {
            W2TSectionTitle("Your Reflection")
            OutlinedTextField(
                value = reflection,
                onValueChange = { reflection = it },
                placeholder = { Text("What did you learn? What went well or poorly?") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.accent,
                    unfocusedBorderColor = colors.border,
                    unfocusedContainerColor = colors.bgLight.copy(alpha = 0.5f)
                )
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { 
                    component.onIntent(TargetContract.Intent.UpdateGoal(goal.withStatus(GoalStatus.COMPLETED)))
                },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.success),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text("Complete", fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { 
                    component.onIntent(TargetContract.Intent.UpdateGoal(goal.withStatus(GoalStatus.CANCELLED)))
                },
                modifier = Modifier.weight(1f).height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = colors.muted),
                shape = RoundedCornerShape(28.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.border)
            ) {
                Text("Archive", fontWeight = FontWeight.Bold)
            }
        }
    }
}
