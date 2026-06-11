package com.vampyreworld.w2t.prefrencesft.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.prefrencesft.PrefrencesComponent
import com.vampyreworld.w2t.prefrencesft.PrefrencesContract
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@Composable
fun PrefrencesScreen(component: PrefrencesComponent) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Dark Mode",
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = state.isDarkMode,
                onCheckedChange = { component.onIntent(PrefrencesContract.Intent.OnThemeChanged(it)) }
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(onClick = { component.onIntent(PrefrencesContract.Intent.OnBackClicked) }) {
            Text("Go Back")
        }
    }
}
