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
import com.vampyreworld.w2t.targetft.presentation.component.DefaultTargetMasterComponent
import com.vampyreworld.w2t.targetft.presentation.component.TargetMasterComponent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.vampyreworld.w2t.domain.usecase.prefrences.GetThemeUseCase
import com.vampyreworld.w2t.sharedui.arch.componentScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, KoinComponent, ComponentContext by componentContext {

    private val getThemeUseCase: GetThemeUseCase = get()

    private val _isDarkMode = MutableValue(true)
    override val isDarkMode: Value<Boolean> = _isDarkMode

    init {
        getThemeUseCase()
            .onEach { isDark ->
                _isDarkMode.update { isDark }
            }
            .launchIn(componentScope())
    }

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
                    navigateToTarget = { id -> 
                        if (id == null) {
                            navigation.push(Screens.AddGoal(null, "MASTER"))
                        } else {
                            navigation.push(Screens.TargetDetail(id))
                        }
                    },
                    navigateToMoodAdd = { navigation.push(Screens.AddMood) },
                    navigateToSChallenge = { id -> navigation.push(Screens.AddChallenge(id)) },
                    navigateToDecisionMaking = { id -> navigation.push(Screens.DecisionForTarget(id)) },
                    navigateToSolution = { navigation.push(Screens.AddSolution(null, null)) },
                    navigateToPreferences = { navigation.push(Screens.Preferences) },
                    navigateToAboutUs = { navigation.push(Screens.AboutUs) }
                )
            )

            is Screens.TargetMaster -> RootComponent.Child.TargetMaster(
                DefaultTargetMasterComponent(
                    componentContext = componentContext,
                    storeFactory = get(),
                    getGoalsUseCase = get(),
                    onOutput = { label ->
                        when (label) {
                            TargetMasterComponent.Label.Back -> navigation.pop()
                            is TargetMasterComponent.Label.NavigateToDetail -> {
                                val goalId = label.goalId
                                if (goalId == null) {
                                    navigation.push(Screens.AddGoal(null, "MASTER"))
                                } else {
                                    navigation.push(Screens.TargetDetail(goalId))
                                }
                            }
                            TargetMasterComponent.Label.NavigateToAddMilestone -> 
                                navigation.push(Screens.AddGoal(null, "MILESTONE"))
                        }
                    }
                )
            )

            is Screens.AddGoal -> RootComponent.Child.Target(
                DefaultTargetComponent(
                    componentContext = componentContext,
                    goalId = null,
                    initialTier = config.tier,
                    parentId = config.parentId,
                    getGoalsUseCase = get(),
                    saveGoalUseCase = get(),
                    onBack = { navigation.pop() },
                    navigateToDecision = { id -> navigation.push(Screens.DecisionForTarget(id)) },
                    navigateToMood = { navigation.push(Screens.AddMood) },
                    navigateToChildTarget = { parentId, tier -> 
                        navigation.push(Screens.AddGoal(parentId, tier))
                    },
                    navigateToChallenge = { goalId ->
                        navigation.push(Screens.AddChallenge(goalId))
                    },
                    navigateToChallengeDetail = { goalId, challengeId ->
                        navigation.push(Screens.DetailOfChallenge(goalId, challengeId))
                    }
                )
            )

            is Screens.TargetDetail -> RootComponent.Child.Target(
                DefaultTargetComponent(
                    componentContext = componentContext,
                    goalId = config.goalId,
                    initialTier = null,
                    parentId = null,
                    getGoalsUseCase = get(),
                    saveGoalUseCase = get(),
                    onBack = { navigation.pop() },
                    navigateToDecision = { id -> navigation.push(Screens.DecisionForTarget(id)) },
                    navigateToMood = { navigation.push(Screens.AddMood) },
                    navigateToChildTarget = { parentId, tier -> 
                        navigation.push(Screens.AddGoal(parentId, tier))
                    },
                    navigateToChallenge = { goalId ->
                        navigation.push(Screens.AddChallenge(goalId))
                    },
                    navigateToChallengeDetail = { goalId, challengeId ->
                        navigation.push(Screens.DetailOfChallenge(goalId, challengeId))
                    }
                )
            )

            is Screens.ListOfChallenges -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    goalId = config.goalId,
                    challengeId = null,
                    addChallengeUseCase = get(),
                    getChallengesUseCase = get(),
                    onBack = { navigation.pop() },
                    navigateToAddSolution = { challengeId -> 
                        navigation.push(Screens.AddSolution(null, challengeId)) 
                    }
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
                    saveDecisionUseCase = get(),
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
                    goalId = config.goalId,
                    challengeId = null,
                    addChallengeUseCase = get(),
                    getChallengesUseCase = get(),
                    onBack = { navigation.pop() },
                    navigateToAddSolution = { challengeId -> 
                        navigation.push(Screens.AddSolution(null, challengeId)) 
                    }
                )
            )

            is Screens.DetailOfChallenge -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    goalId = config.goalId,
                    challengeId = config.challengeId,
                    addChallengeUseCase = get(),
                    getChallengesUseCase = get(),
                    onBack = { navigation.pop() },
                    navigateToAddSolution = { challengeId -> 
                        navigation.push(Screens.AddSolution(null, challengeId)) 
                    }
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
                    getThemeUseCase = get(),
                    setThemeUseCase = get(),
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
