package com.vampyreworld.w2t.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.vampyreworld.navigation.Screens
import com.vampyreworld.w2t.aboutus.DefaultAboutUsComponent
import com.vampyreworld.w2t.decissionmakingft.DefaultDecisionMakingComponent
import com.vampyreworld.w2t.home.DefaultHomeComponent
import com.vampyreworld.w2t.moodaddft.DefaultMoodAddComponent
import com.vampyreworld.w2t.onboarding.DefaultOnboardingComponent
import com.vampyreworld.w2t.prefrencesft.DefaultPrefrencesComponent
import com.vampyreworld.w2t.schallengeft.DefaultSChallengeComponent
import com.vampyreworld.w2t.solutionft.DefaultSolutionComponent
import com.vampyreworld.w2t.splash.DefaultSplashComponent
import com.vampyreworld.w2t.targetft.component.DefaultTargetComponent

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Screens>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Screens.serializer(),
            initialConfiguration = Screens.Splash,
            handleBackButton = true,
            childFactory = ::createChild
        )

    @OptIn(DelicateDecomposeApi::class)
    private fun createChild(config: Screens, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Screens.Splash -> RootComponent.Child.Splash(
                DefaultSplashComponent(
                    componentContext = componentContext,
                    onFinished = { navigation.replaceAll(Screens.OnBoarding) }
                )
            )

            is Screens.OnBoarding -> RootComponent.Child.Onboarding(
                DefaultOnboardingComponent(
                    componentContext = componentContext,
                    onFinish = { navigation.replaceAll(Screens.Home) }
                )
            )

            is Screens.Home -> RootComponent.Child.Home(
                DefaultHomeComponent(
                    componentContext = componentContext,
                    navigateToTarget = { navigation.push(Screens.Goal(null)) },
                    navigateToMoodAdd = { navigation.push(Screens.AddMood) },
                    navigateToSChallenge = { navigation.push(Screens.AddChallenge) },
                    navigateToDecisionMaking = { navigation.push(Screens.DecisionMaking("", "")) },
                    navigateToSolution = { navigation.push(Screens.AddSolution) },
                    navigateToPreferences = { navigation.push(Screens.Preferences) },
                    navigateToAboutUs = { navigation.push(Screens.AboutUs) }
                )
            )

            is Screens.Goal -> RootComponent.Child.Target(
                DefaultTargetComponent(
                    componentContext = componentContext,
                    storeFactory = DefaultStoreFactory()
                )
            )

            is Screens.AddMood -> RootComponent.Child.MoodAdd(
                DefaultMoodAddComponent(
                    componentContext = componentContext,
                    onBack = { navigation.pop() }
                )
            )

            is Screens.AddChallenge -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    onBack = { navigation.pop() }
                )
            )

            is Screens.DecisionMaking -> RootComponent.Child.DecisionMaking(
                DefaultDecisionMakingComponent(
                    componentContext = componentContext,
                    onBack = { navigation.pop() }
                )
            )

            is Screens.AddSolution -> RootComponent.Child.Solution(
                DefaultSolutionComponent(
                    componentContext = componentContext,
                    onBack = { navigation.pop() }
                )
            )

            is Screens.Preferences -> RootComponent.Child.Preferences(
                DefaultPrefrencesComponent(
                    componentContext = componentContext,
                    onBack = { navigation.pop() }
                )
            )

            is Screens.AboutUs -> RootComponent.Child.AboutUs(
                DefaultAboutUsComponent(
                    componentContext = componentContext,
                    onBack = { navigation.pop() }
                )
            )

            else -> RootComponent.Child.Splash(
                DefaultSplashComponent(
                    componentContext = componentContext,
                    onFinished = { navigation.replaceAll(Screens.Home) }
                )
            )
        }
}
