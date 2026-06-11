package com.vampyreworld.w2t.solutionft.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.domain.data.model.Solution

@Composable
fun SolutionItem(solution: Solution, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = solution.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Type: ${solution.solutionType}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
