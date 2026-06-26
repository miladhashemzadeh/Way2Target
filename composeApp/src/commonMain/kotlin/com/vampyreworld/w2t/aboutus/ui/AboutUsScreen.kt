package com.vampyreworld.w2t.aboutus.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vampyreworld.w2t.aboutus.AboutUsComponent
import com.vampyreworld.w2t.aboutus.AboutUsContract
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.localization.LocalAppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(component: AboutUsComponent) {
    val colors = LocalAppColorScheme.current
    val strings = LocalAppStrings.current
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(strings.aboutUs, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(AboutUsContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = strings.goBack)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { component.onIntent(AboutUsContract.Intent.OnBackClicked) },
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent)
            ) {
                Text(strings.aboutUsScreenGoBack)
            }
        }
    }
}

