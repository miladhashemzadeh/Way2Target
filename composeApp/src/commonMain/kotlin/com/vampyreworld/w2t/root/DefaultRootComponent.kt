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
import com.vampyreworld.navigation.Screens
import com.vampyreworld.w2t.aboutus.DefaultAboutUsComponent
import com.vampyreworld.w2t.appraiseft.component.DefaultAppraiseComponent
import com.vampyreworld.w2t.decissionmakingft.DefaultDecisionMakingComponent
import com.vampyreworld.w2t.shomeft.DefaultHomeComponent
import com.vampyreworld.w2t.moodaddft.DefaultMoodAddComponent
import com.vampyreworld.w2t.onboarding.DefaultOnboardingComponent
import com.vampyreworld.w2t.prefrencesft.DefaultPrefrencesComponent
import com.vampyreworld.w2t.schallengeft.DefaultSChallengeComponent
import com.vampyreworld.w2t.solutionft.component.DefaultSolutionComponent
import com.vampyreworld.w2t.splash.DefaultSplashComponent
import com.vampyreworld.w2t.targetft.component.MVITargetComponent
import com.vampyreworld.w2t.targetft.presentation.component.DefaultTargetMasterComponent
import com.vampyreworld.w2t.targetft.presentation.component.TargetMasterComponent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.vampyreworld.navigation.Router
import com.vampyreworld.w2t.domain.usecase.onboarding.IsOnboardingCompletedUseCase
import com.vampyreworld.w2t.domain.usecase.onboarding.SetOnboardingCompletedUseCase
import com.vampyreworld.w2t.domain.usecase.prefrences.GetThemeUseCase
import com.vampyreworld.w2t.sharedui.arch.componentScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, KoinComponent, ComponentContext by componentContext {

    private val getThemeUseCase: GetThemeUseCase = get()
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase = get()
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase = get()
    private val router: Router = get()

    private val _isDarkMode = MutableValue(true)
    override val isDarkMode: Value<Boolean> = _isDarkMode

    private val navigation = StackNavigation<Screens>()

    init {
        getThemeUseCase()
            .onEach { isDark ->
                _isDarkMode.update { isDark }
            }
            .launchIn(componentScope())

        router.collector
            .onEach { destination ->
                @OptIn(DelicateDecomposeApi::class)
                when (destination) {
                    is Screens.PopBackStack -> navigation.pop()
                    is Screens.ClearBackStack -> navigation.replaceAll(destination.target)
                    is Screens.OpenAndClearBackstack -> {
                        navigation.replaceAll(destination.backDestination, destination.target)
                    }
                    is Screens.KeepFirstBackStackAndClearOthers -> {
                        // Custom stack manipulation: keep only popUpToTarget and push target
                        // Decompose's navigate is used for this
                        // navigation.navigate { it.filter { config -> config == destination.popUpToTarget } + destination.target }
                        // For simplicity, if popUpToTarget is not found, it might clear everything.
                        // Here we just use replaceAll if we want to clear everything and go to target
                        navigation.replaceAll(destination.target)
                    }
                    else -> navigation.push(destination)
                }
            }
            .launchIn(componentScope())
    }

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
                    onFinished = { 
                        if (isOnboardingCompletedUseCase()) {
                            navigation.replaceAll(Screens.Home)
                        } else {
                            navigation.replaceAll(Screens.OnBoarding)
                        }
                    }
                )
            )

            is Screens.OnBoarding -> RootComponent.Child.Onboarding(
                DefaultOnboardingComponent(
                    componentContext = componentContext,
                    onFinish = { 
                        setOnboardingCompletedUseCase(true)
                        navigation.replaceAll(Screens.Home) 
                    }
                )
            )

            is Screens.Home -> RootComponent.Child.Home(
                DefaultHomeComponent(
                    componentContext = componentContext,
                    getGoalsUseCase = get(),
                    deleteGoalUseCase = get(),
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
                    deleteGoalUseCase = get(),
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
                MVITargetComponent(
                    componentContext = componentContext,
                    goalId = null,
                    initialTier = config.tier,
                    parentId = config.parentId,
                    storeFactory = get(),
                    getGoalsUseCase = get(),
                    saveGoalUseCase = get(),
                    deleteGoalUseCase = get(),
                    getChallengesUseCase = get(),
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
                    },
                    navigateToAppraise = { goalId, challengeId ->
                        if (challengeId != null && goalId != null) {
                            navigation.push(Screens.AppraiseChallenge(goalId, challengeId))
                        } else if (goalId != null) {
                            navigation.push(Screens.AppraiseTarget(goalId))
                        }
                    }
                )
            )

            is Screens.TargetDetail -> RootComponent.Child.Target(
                MVITargetComponent(
                    componentContext = componentContext,
                    goalId = config.goalId,
                    initialTier = null,
                    parentId = null,
                    storeFactory = get(),
                    getGoalsUseCase = get(),
                    saveGoalUseCase = get(),
                    deleteGoalUseCase = get(),
                    getChallengesUseCase = get(),
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
                    },
                    navigateToAppraise = { goalId, challengeId ->
                        if (challengeId != null && goalId != null) {
                            navigation.push(Screens.AppraiseChallenge(goalId, challengeId))
                        } else if (goalId != null) {
                            navigation.push(Screens.AppraiseTarget(goalId))
                        }
                    }
                )
            )

            is Screens.ListOfChallenges -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    storeFactory = get { parametersOf(config.goalId, null) },
                    onBack = { navigation.pop() },
                    navigateToAddSolution = { challengeId -> 
                        navigation.push(Screens.AddSolution(config.goalId, challengeId)) 
                    },
                    navigateToDecision = { challengeId ->
                        navigation.push(Screens.DecisionForChallenge(config.goalId, challengeId))
                    }
                )
            )

            is Screens.AddChallenge -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    storeFactory = get { parametersOf(config.goalId, null) },
                    onBack = { navigation.pop() },
                    navigateToAddSolution = { challengeId -> 
                        navigation.push(Screens.AddSolution(config.goalId, challengeId)) 
                    },
                    navigateToDecision = { challengeId ->
                        navigation.push(Screens.DecisionForChallenge(config.goalId, challengeId))
                    }
                )
            )

            is Screens.DetailOfChallenge -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    storeFactory = get { parametersOf(config.goalId, config.challengeId) },
                    onBack = { navigation.pop() },
                    navigateToAddSolution = { challengeId -> 
                        navigation.push(Screens.AddSolution(config.goalId, challengeId)) 
                    },
                    navigateToDecision = { challengeId ->
                        navigation.push(Screens.DecisionForChallenge(config.goalId, challengeId))
                    }
                )
            )

            is Screens.ListOfSolutions -> RootComponent.Child.Solution(
                DefaultSolutionComponent(
                    componentContext = componentContext,
                    storeFactory = get { parametersOf(config.goalId, config.challengeId) },
                    onBack = { navigation.pop() }
                )
            )

            is Screens.AddSolution -> RootComponent.Child.Solution(
                DefaultSolutionComponent(
                    componentContext = componentContext,
                    storeFactory = get { parametersOf(config.goalId, config.challengeId) },
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

            is Screens.DecisionForChallenge -> RootComponent.Child.DecisionMaking(
                DefaultDecisionMakingComponent(
                    componentContext = componentContext,
                    saveDecisionUseCase = get(),
                    onBack = { navigation.pop() }
                )
            )
            
            is Screens.AppraiseTarget -> RootComponent.Child.Appraise(
                DefaultAppraiseComponent(
                    componentContext = componentContext,
                    storeFactory = get { parametersOf(config.goalId, null) },
                    onBack = { navigation.pop() }
                )
            )

            is Screens.AppraiseChallenge -> RootComponent.Child.Appraise(
                DefaultAppraiseComponent(
                    componentContext = componentContext,
                    storeFactory = get { parametersOf(config.goalId, config.challengeId) },
                    onBack = { navigation.pop() }
                )
            )

            is Screens.AddMood -> RootComponent.Child.MoodAdd(
                DefaultMoodAddComponent(
                    componentContext = componentContext,
                    addMoodUseCase = get(),
                    getMoodHistoryUseCase = get(),
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

            // Handling the "command" screens by returning a Splash (though ideally they shouldn't reach here)
            is Screens.PopBackStack,
            is Screens.ClearBackStack,
            is Screens.KeepFirstBackStackAndClearOthers,
            is Screens.OpenAndClearBackstack -> RootComponent.Child.Splash(
                DefaultSplashComponent(
                    componentContext = componentContext,
                    onFinished = { navigation.pop() }
                )
            )
        }
}
