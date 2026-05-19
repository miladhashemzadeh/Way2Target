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
import com.vampyreworld.w2t.shomeft.DefaultHomeComponent
import com.vampyreworld.w2t.moodaddft.DefaultMoodAddComponent
import com.vampyreworld.w2t.onboarding.DefaultOnboardingComponent
import com.vampyreworld.w2t.prefrencesft.DefaultPrefrencesComponent
import com.vampyreworld.w2t.schallengeft.DefaultSChallengeComponent
import com.vampyreworld.w2t.solutionft.DefaultSolutionComponent
import com.vampyreworld.w2t.splash.DefaultSplashComponent
import com.vampyreworld.w2t.targetft.component.DefaultTargetComponent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, KoinComponent, ComponentContext by componentContext {

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

            is Screens.TargetMaster -> RootComponent.Child.Target(
                DefaultTargetMasterComponent(
                    componentContext = componentContext,
                    storeFactory = get(),
                    getGoalsUseCase = get(),
                    onOutput = { label ->
                        when (label) {
                            TargetMasterComponent.Label.Back -> navigation.pop()
                            is TargetMasterComponent.Label.NavigateToDetail -> 
                                navigation.push(Screens.TargetDetail(label.goalId))
                            TargetMasterComponent.Label.NavigateToAddMilestone -> 
                                navigation.push(Screens.AddTargetMilestone)
                        }
                    }
                )
            )

            is Screens.TargetDetail -> RootComponent.Child.Target(
                DefaultTargetComponent(
                    componentContext = componentContext,
                    getGoalsUseCase = get(),
                    saveGoalUseCase = get(),
                    onBack = { navigation.pop() }
                )
            )

            is Screens.ListOfChallenges -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    addChallengeUseCase = get(),
                    getChallengesUseCase = get(),
                    onBack = { navigation.pop() }
                )
            )

            is Screens.ListOfSolutions -> RootComponent.Child.Solution(
                DefaultSolutionComponent(
                    componentContext = componentContext,
                    addSolutionUseCase = get(),
                    onBack = { navigation.pop() }
                )
            )

            is Screens.DecisionForTarget -> RootComponent.Child.DecisionMaking(
                DefaultDecisionMakingComponent(
                    componentContext = componentContext,
                    makeDecisionUseCase = get(),
                    onBack = { navigation.pop() }
                )
            )
            
            // AppraiseFT - Placeholder mapping
            is Screens.AppraiseTarget -> RootComponent.Child.Splash(
                DefaultSplashComponent(componentContext, { navigation.pop() })
            )
            
            // Add other cases as I implement them...

            is Screens.AddMood -> RootComponent.Child.MoodAdd(
                DefaultMoodAddComponent(
                    componentContext = componentContext,
                    addMoodUseCase = get(),
                    getMoodHistoryUseCase = get(),
                    onBack = { navigation.pop() }
                )
            )

            is Screens.AddChallenge -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    addChallengeUseCase = get(),
                    getChallengesUseCase = get(),
                    onBack = { navigation.pop() }
                )
            )

            is Screens.DecisionMaking -> RootComponent.Child.DecisionMaking(
                DefaultDecisionMakingComponent(
                    componentContext = componentContext,
                    makeDecisionUseCase = get(),
                    onBack = { navigation.pop() }
                )
            )

            is Screens.AddSolution -> RootComponent.Child.Solution(
                DefaultSolutionComponent(
                    componentContext = componentContext,
                    addSolutionUseCase = get(),
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
