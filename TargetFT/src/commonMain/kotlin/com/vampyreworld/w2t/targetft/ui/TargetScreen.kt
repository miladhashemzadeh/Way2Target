package com.vampyreworld.w2t.targetft.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.targetft.component.TargetComponent
import com.vampyreworld.w2t.targetft.store.TargetStore

@Composable
fun TargetScreen(component: TargetComponent) {
    val state by component.state.subscribeAsState()

    Column {
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        Button(onClick = { component.onIntent(TargetStore.Intent.Refresh) }) {
            Text("Refresh")
        }

        LazyColumn {
            items(state.targets) { target ->
                Text(text = target)
            }
        }
    }
}
