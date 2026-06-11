package com.vampyreworld.w2t.targetft.action

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.vampyreworld.w2t.targetft.action.actionCreate.ActionCreateScreen
import com.vampyreworld.w2t.targetft.action.actionDetail.ActionDetailScreen

@Composable
fun ActionScreen(component: ActionComponent) {
    Children(
        stack = component.childStack,
        modifier = Modifier.fillMaxSize(),
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is ActionComponent.Child.Create -> ActionCreateScreen(child.component, PaddingValues(0.dp))
            is ActionComponent.Child.Detail -> ActionDetailScreen(child.component, PaddingValues(0.dp))
        }
    }
}
