package com.vampyreworld.w2t.moodaddft.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings

@Composable
fun MoodSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    icon: ImageVector
) {
    val strings = LocalAppStrings.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(label, style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.weight(1f))
            Text(strings.percentFormat.format(value.toInt()), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
