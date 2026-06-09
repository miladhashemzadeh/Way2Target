package com.vampyreworld.w2t.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.aboutus.AboutUsComponent
import com.vampyreworld.w2t.appraiseft.component.AppraiseComponent
import com.vampyreworld.w2t.decissionmakingft.DecisionMakingComponent
import com.vampyreworld.w2t.shomeft.HomeComponent
import com.vampyreworld.w2t.moodaddft.MoodAddComponent
import com.vampyreworld.w2t.onboarding.OnboardingComponent
import com.vampyreworld.w2t.prefrencesft.PrefrencesComponent
import com.vampyreworld.w2t.schallengeft.SChallengeComponent
import com.vampyreworld.w2t.solutionft.component.SolutionComponent
import com.vampyreworld.w2t.splash.SplashComponent
import com.vampyreworld.w2t.targetft.component.TargetComponent
import com.vampyreworld.w2t.targetft.presentation.component.TargetMasterComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>
    val isDarkMode: Value<Boolean>

    sealed class Child {
        data class Splash(val component: SplashComponent) : Child()
        data class Onboarding(val component: OnboardingComponent) : Child()
        data class Home(val component: HomeComponent) : Child()
        data class Target(val component: TargetComponent) : Child()
        data class TargetMaster(val component: TargetMasterComponent) : Child()
        data class MoodAdd(val component: MoodAddComponent) : Child()
        data class SChallenge(val component: SChallengeComponent) : Child()
        data class DecisionMaking(val component: DecisionMakingComponent) : Child()
        data class Solution(val component: SolutionComponent) : Child()
        data class Preferences(val component: PrefrencesComponent) : Child()
        data class AboutUs(val component: AboutUsComponent) : Child()
        data class Appraise(val component: AppraiseComponent) : Child()
    }
}
