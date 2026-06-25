package com.vampyreworld.w2t.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
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
import com.vampyreworld.w2t.profileft.ProfileContract
import com.vampyreworld.w2t.profileft.component.DefaultProfileComponent
import com.vampyreworld.w2t.schallengeft.component.DefaultSChallengeComponent
import com.vampyreworld.w2t.schallengeft.ui.create.DefaultChallengeCreateComponent
import com.vampyreworld.w2t.solutionft.component.DefaultSolutionComponent
import com.vampyreworld.w2t.splash.DefaultSplashComponent
import com.vampyreworld.w2t.targetft.master.DefaultMasterComponent
import com.vampyreworld.w2t.targetft.milestone.DefaultMilestoneComponent
import com.vampyreworld.w2t.targetft.action.DefaultActionComponent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.vampyreworld.navigation.Router
import com.vampyreworld.w2t.domain.usecase.onboarding.IsOnboardingCompletedUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.onboarding.SetOnboardingCompletedUseCase
import com.vampyreworld.w2t.domain.usecase.prefrences.GetThemeUseCase
import com.vampyreworld.w2t.domain.usecase.profile.GetUserProfileUseCase
import com.vampyreworld.w2t.sharedui.arch.componentScope
import com.vampyreworld.w2t.sharedui.theme.UserProfileInfo
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val onExit: () -> Unit = {}
) : RootComponent, KoinComponent, ComponentContext by componentContext {

    private val getThemeUseCase: GetThemeUseCase = get()
    private val getUserProfileUseCase: GetUserProfileUseCase = get()
    private val isOnboardingCompletedUseCase: IsOnboardingCompletedUseCase = get()
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase = get()
    private val router: Router = get()

    private val _isDarkMode = MutableValue(true)
    override val isDarkMode: Value<Boolean> = _isDarkMode

    private val _userProfile = MutableValue(UserProfileInfo())
    override val userProfile: Value<UserProfileInfo> = _userProfile

    private val navigation = StackNavigation<Screens>()

    init {
        getThemeUseCase()
            .onEach { isDark ->
                _isDarkMode.update { isDark }
            }
            .launchIn(componentScope())

        getUserProfileUseCase()
            .onEach { profile ->
                _userProfile.update { 
                    UserProfileInfo(
                        name = profile.name,
                        avatarUrl = profile.avatarUrl
                    )
                }
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
                    else -> navigation.bringToFront(destination)
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
                    getUserProfileUseCase = get(),
                    navigateToTarget = { id -> 
                        if (id == null) {
                            navigation.bringToFront(Screens.AddGoal(null, "MASTER"))
                        } else {
                            navigation.bringToFront(Screens.TargetDetail(id, "MASTER"))
                        }
                    },
                    navigateToProfile = { navigation.bringToFront(Screens.Profile) },
                    navigateToMoodAdd = { navigation.bringToFront(Screens.AddMood) },
                    navigateToSChallenge = { id -> navigation.bringToFront(Screens.ListOfChallenges(id)) },
                    navigateToDecisionMaking = { id -> navigation.bringToFront(Screens.DecisionForTarget(id)) },
                    navigateToSolution = { navigation.bringToFront(Screens.AddSolution(null, null)) },
                    navigateToPreferences = { navigation.bringToFront(Screens.Preferences) },
                    navigateToAboutUs = { navigation.bringToFront(Screens.AboutUs) },
                    onExit = onExit
                )
            )

            is Screens.AddGoal -> {
                when (config.tier) {
                    "MASTER" -> RootComponent.Child.Master(
                        DefaultMasterComponent(
                            componentContext = componentContext,
                            goalId = null,
                            storeFactory = get(),
                            getGoalsUseCase = get(),
                            saveGoalUseCase = get(),
                            deleteGoalUseCase = get(),
                            getChallengesUseCase = get(),
                            onBack = { navigation.pop() },
                            navigateToDecision = { id -> navigation.bringToFront(Screens.DecisionForTarget(id)) },
                            navigateToMood = { navigation.bringToFront(Screens.AddMood) },
                            navigateToGoal = { id, tier -> navigation.bringToFront(Screens.TargetDetail(id, tier)) },
                            navigateToCreateMilestone = { parentId ->
                                navigation.bringToFront(Screens.AddGoal(parentId, "MILESTONE"))
                            },
                            navigateToChallenge = { goalId ->
                                navigation.bringToFront(Screens.AddChallenge(goalId))
                            },
                            navigateToAppraise = { goalId ->
                                navigation.bringToFront(Screens.AppraiseTarget(goalId))
                            }
                        )
                    )
                    "MILESTONE" -> RootComponent.Child.Milestone(
                        DefaultMilestoneComponent(
                            componentContext = componentContext,
                            goalId = null,
                            parentId = config.parentId,
                            storeFactory = get(),
                            getGoalsUseCase = get(),
                            saveGoalUseCase = get(),
                            deleteGoalUseCase = get(),
                            getChallengesUseCase = get(),
                            onBack = { navigation.pop() },
                            navigateToDecision = { id -> navigation.bringToFront(Screens.DecisionForTarget(id)) },
                            navigateToMood = { navigation.bringToFront(Screens.AddMood) },
                            navigateToGoal = { id, tier -> navigation.bringToFront(Screens.TargetDetail(id, tier, config.parentId)) },
                            navigateToCreateAction = { parentId ->
                                navigation.bringToFront(Screens.AddGoal(parentId, "ACTION"))
                            },
                            navigateToChallenge = { goalId ->
                                navigation.bringToFront(Screens.AddChallenge(goalId))
                            },
                            navigateToAppraise = { goalId ->
                                navigation.bringToFront(Screens.AppraiseTarget(goalId))
                            }
                        )
                    )
                    else -> RootComponent.Child.Action(
                        DefaultActionComponent(
                            componentContext = componentContext,
                            goalId = null,
                            parentId = config.parentId,
                            storeFactory = get(),
                            getGoalsUseCase = get(),
                            saveGoalUseCase = get(),
                            deleteGoalUseCase = get(),
                            getChallengesUseCase = get(),
                            onBack = { navigation.pop() },
                            navigateToDecision = { id -> navigation.bringToFront(Screens.DecisionForTarget(id)) },
                            navigateToMood = { navigation.bringToFront(Screens.AddMood) },
                            navigateToGoal = { id, tier -> navigation.bringToFront(Screens.TargetDetail(id, tier, config.parentId)) },
                            navigateToChallenge = { goalId ->
                                navigation.bringToFront(Screens.AddChallenge(goalId))
                            },
                            navigateToAppraise = { goalId ->
                                navigation.bringToFront(Screens.AppraiseTarget(goalId))
                            }
                        )
                    )
                }
            }

            is Screens.TargetDetail -> {
                when (config.tier) {
                    "MASTER" -> RootComponent.Child.Master(
                        DefaultMasterComponent(
                            componentContext = componentContext,
                            goalId = config.goalId,
                            storeFactory = get(),
                            getGoalsUseCase = get(),
                            saveGoalUseCase = get(),
                            deleteGoalUseCase = get(),
                            getChallengesUseCase = get(),
                            onBack = { navigation.replaceAll(Screens.Home) },
                            navigateToDecision = { id -> navigation.bringToFront(Screens.DecisionForTarget(id)) },
                            navigateToMood = { navigation.bringToFront(Screens.AddMood) },
                            navigateToGoal = { id, tier -> navigation.bringToFront(Screens.TargetDetail(id, tier, config.goalId)) },
                            navigateToCreateMilestone = { parentId ->
                                navigation.bringToFront(Screens.AddGoal(parentId, "MILESTONE"))
                            },
                            navigateToChallenge = { goalId ->
                                navigation.bringToFront(Screens.AddChallenge(goalId))
                            },
                            navigateToAppraise = { goalId ->
                                navigation.bringToFront(Screens.AppraiseTarget(goalId))
                            }
                        )
                    )
                    "MILESTONE" -> RootComponent.Child.Milestone(
                        DefaultMilestoneComponent(
                            componentContext = componentContext,
                            goalId = config.goalId,
                            parentId = config.parentId,
                            storeFactory = get(),
                            getGoalsUseCase = get(),
                            saveGoalUseCase = get(),
                            deleteGoalUseCase = get(),
                            getChallengesUseCase = get(),
                            onBack = { 
                                if (config.parentId != null) {
                                    navigation.bringToFront(Screens.TargetDetail(config.parentId, "MASTER"))
                                } else {
                                    navigation.pop()
                                }
                            },
                            navigateToDecision = { id -> navigation.bringToFront(Screens.DecisionForTarget(id)) },
                            navigateToMood = { navigation.bringToFront(Screens.AddMood) },
                            navigateToGoal = { id, tier -> navigation.bringToFront(Screens.TargetDetail(id, tier, config.goalId)) },
                            navigateToCreateAction = { parentId ->
                                navigation.bringToFront(Screens.AddGoal(parentId, "ACTION"))
                            },
                            navigateToChallenge = { goalId ->
                                navigation.bringToFront(Screens.AddChallenge(goalId))
                            },
                            navigateToAppraise = { goalId ->
                                navigation.bringToFront(Screens.AppraiseTarget(goalId))
                            }
                        )
                    )
                    else -> RootComponent.Child.Action(
                        DefaultActionComponent(
                            componentContext = componentContext,
                            goalId = config.goalId,
                            parentId = config.parentId,
                            storeFactory = get(),
                            getGoalsUseCase = get(),
                            saveGoalUseCase = get(),
                            deleteGoalUseCase = get(),
                            getChallengesUseCase = get(),
                            onBack = { 
                                if (config.parentId != null) {
                                    navigation.bringToFront(Screens.TargetDetail(config.parentId, "MILESTONE"))
                                } else {
                                    navigation.pop()
                                }
                            },
                            navigateToDecision = { id -> navigation.bringToFront(Screens.DecisionForTarget(id)) },
                            navigateToMood = { navigation.bringToFront(Screens.AddMood) },
                            navigateToGoal = { id, tier -> navigation.bringToFront(Screens.TargetDetail(id, tier, config.goalId)) },
                            navigateToChallenge = { goalId ->
                                navigation.bringToFront(Screens.AddChallenge(goalId))
                            },
                            navigateToAppraise = { goalId ->
                                navigation.bringToFront(Screens.AppraiseTarget(goalId))
                            }
                        )
                    )
                }
            }

            is Screens.ListOfChallenges -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    storeFactory = get { parametersOf(config.goalId, null) },
                    onBack = { navigation.pop() },
                    navigateToHome = { navigation.bringToFront(Screens.Home) },
                    navigateToProfile = { navigation.bringToFront(Screens.Profile) },
                    navigateToSChallenge = { id -> navigation.bringToFront(Screens.ListOfChallenges(id)) },
                    navigateToPreferences = { navigation.bringToFront(Screens.Preferences) },
                    navigateToAddSolution = { challengeId -> 
                        navigation.bringToFront(Screens.AddSolution(config.goalId, challengeId)) 
                    },
                    navigateToSolutionsList = { challengeId ->
                        navigation.bringToFront(Screens.ListOfSolutions(config.goalId, challengeId))
                    },
                    navigateToDecision = { challengeId ->
                        navigation.bringToFront(Screens.DecisionForChallenge(config.goalId, challengeId))
                    }
                )
            )

            is Screens.AddChallenge -> RootComponent.Child.ChallengeCreate(
                DefaultChallengeCreateComponent(
                    componentContext = componentContext,
                    goalId = config.goalId,
                    getGoalsUseCase = get(),
                    addChallengeUseCase = get(),
                    onBack = { navigation.pop() }
                )
            )

            is Screens.DetailOfChallenge -> RootComponent.Child.SChallenge(
                DefaultSChallengeComponent(
                    componentContext = componentContext,
                    storeFactory = get { parametersOf(config.goalId, config.challengeId) },
                    onBack = { navigation.pop() },
                    navigateToHome = { navigation.bringToFront(Screens.Home) },
                    navigateToProfile = { navigation.bringToFront(Screens.Profile) },
                    navigateToSChallenge = { id -> navigation.bringToFront(Screens.ListOfChallenges(id)) },
                    navigateToPreferences = { navigation.bringToFront(Screens.Preferences) },
                    navigateToAddSolution = { challengeId -> 
                        navigation.bringToFront(Screens.AddSolution(config.goalId, challengeId)) 
                    },
                    navigateToSolutionsList = { challengeId ->
                        navigation.bringToFront(Screens.ListOfSolutions(config.goalId, challengeId))
                    },
                    navigateToDecision = { challengeId ->
                        navigation.bringToFront(Screens.DecisionForChallenge(config.goalId, challengeId))
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

            is Screens.Profile -> RootComponent.Child.Profile(
                DefaultProfileComponent(
                    componentContext = componentContext,
                    storeFactory = get(),
                    getUserProfileUseCase = get(),
                    saveUserProfileUseCase = get(),
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
