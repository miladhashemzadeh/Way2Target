package com.vampyreworld.w2t

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.vampyreworld.w2t.aboutus.ui.AboutUsScreen
import com.vampyreworld.w2t.decissionmakingft.ui.DecisionMakingScreen
import com.vampyreworld.w2t.home.ui.HomeScreen
import com.vampyreworld.w2t.moodaddft.ui.MoodAddScreen
import com.vampyreworld.w2t.onboarding.ui.OnboardingScreen
import com.vampyreworld.w2t.prefrencesft.ui.PrefrencesScreen
import com.vampyreworld.w2t.root.RootComponent
import com.vampyreworld.w2t.schallengeft.ui.SChallengeScreen
import com.vampyreworld.w2t.sharedui.theme.W2TTheme
import com.vampyreworld.w2t.solutionft.ui.SolutionScreen
import com.vampyreworld.w2t.splash.ui.SplashScreen
import com.vampyreworld.w2t.targetft.ui.TargetScreen

@Composable
fun App(root: RootComponent) {
    W2TTheme {
        Children(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            stack = root.childStack,
            animation = stackAnimation(fade()),
        ) {
            when (val child = it.instance) {
                is RootComponent.Child.Splash -> SplashScreen(child.component)
                is RootComponent.Child.Onboarding -> OnboardingScreen(child.component)
                is RootComponent.Child.Home -> HomeScreen(child.component)
                is RootComponent.Child.Target -> TargetScreen(child.component)
                is RootComponent.Child.MoodAdd -> MoodAddScreen(child.component)
                is RootComponent.Child.SChallenge -> SChallengeScreen(child.component)
                is RootComponent.Child.DecisionMaking -> DecisionMakingScreen(child.component)
                is RootComponent.Child.Solution -> SolutionScreen(child.component)
                is RootComponent.Child.Preferences -> PrefrencesScreen(child.component)
                is RootComponent.Child.AboutUs -> AboutUsScreen(child.component)
            }
        }
    }
}
