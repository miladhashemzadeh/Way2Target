package com.vampyreworld.w2t.appraiseft.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.appraiseft.AppraiseContract
import com.vampyreworld.w2t.appraiseft.component.AppraiseComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppraiseScreen(component: AppraiseComponent) {
    val state by component.state.subscribeAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Appraisal") },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(AppraiseContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (state.challengeId != null) "Appraising Challenge" else "Appraising Target",
                style = MaterialTheme.typography.titleLarge
            )
            
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.appraisalResult.isNotEmpty()) {
                Text(text = state.appraisalResult)
            } else {
                Button(onClick = { component.onIntent(AppraiseContract.Intent.OnAppraiseClicked) }) {
                    Text("Start AI Appraisal")
                }
            }
        }
    }
}
