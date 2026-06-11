package com.vampyreworld.w2t.targetft.master

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.vampyreworld.w2t.targetft.master.masterCreate.MasterCreateScreen
import com.vampyreworld.w2t.targetft.master.masterDetail.MasterDetailScreen

@Composable
fun MasterScreen(component: MasterComponent) {
    Children(
        stack = component.childStack,
        modifier = Modifier.fillMaxSize(),
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is MasterComponent.Child.Create -> MasterCreateScreen(child.component, PaddingValues(0.dp))
            is MasterComponent.Child.Detail -> MasterDetailScreen(child.component, PaddingValues(0.dp))
        }
    }
}
