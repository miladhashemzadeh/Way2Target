package com.vampyreworld.w2t.targetft.milestone

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.vampyreworld.w2t.targetft.milestone.milestoneCreate.MilestoneCreateScreen
import com.vampyreworld.w2t.targetft.milestone.milestoneDetail.MilestoneDetailScreen

@Composable
fun MilestoneScreen(component: MilestoneComponent) {
    Children(
        stack = component.childStack,
        modifier = Modifier.fillMaxSize(),
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is MilestoneComponent.Child.Create -> MilestoneCreateScreen(child.component, PaddingValues(0.dp))
            is MilestoneComponent.Child.Detail -> MilestoneDetailScreen(child.component, PaddingValues(0.dp))
        }
    }
}
