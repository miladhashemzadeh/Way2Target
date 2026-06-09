package com.vampyreworld.w2t.targetft.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Goal
import com.vampyreworld.w2t.domain.data.model.GoalTier

@Composable
fun GoalListItem(
    goal: Goal,
    onDelete: (Long) -> Unit,
    onReplace: (Long) -> Unit,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val icon = when (goal.tier) {
        GoalTier.MASTER -> Icons.Default.AccountTree
        GoalTier.MILESTONE -> Icons.Default.Flag
        GoalTier.ACTION -> Icons.AutoMirrored.Filled.DirectionsRun
    }
    
    val tierColor = when (goal.tier) {
        GoalTier.MASTER -> MaterialTheme.colorScheme.primary
        GoalTier.MILESTONE -> MaterialTheme.colorScheme.secondary
        GoalTier.ACTION -> MaterialTheme.colorScheme.tertiary
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(goal.id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = tierColor.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = tierColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = goal.title.ifEmpty { "${goal.tier.name} #${goal.id}" },
                    style = MaterialTheme.typography.titleMedium
                )
                if (goal.description.isNotEmpty()) {
                    Text(
                        text = goal.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                }
            }
            
            Row {
                IconButton(onClick = { onReplace(goal.id) }) {
                    Icon(
                        imageVector = Icons.Default.SwapHoriz,
                        contentDescription = "Replace",
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(onClick = { onDelete(goal.id) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
