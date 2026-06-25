package com.vampyreworld.w2t.targetft.milestone

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.vampyreworld.w2t.domain.usecase.DeleteGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetGoalsUseCase
import com.vampyreworld.w2t.domain.usecase.SaveGoalUseCase
import com.vampyreworld.w2t.domain.usecase.GetChallengesUseCase
import com.vampyreworld.w2t.targetft.milestone.milestoneCreate.DefaultMilestoneCreateComponent
import com.vampyreworld.w2t.targetft.milestone.milestoneCreate.MilestoneCreateContract
import com.vampyreworld.w2t.targetft.milestone.milestoneDetail.DefaultMilestoneDetailComponent
import com.vampyreworld.w2t.targetft.milestone.milestoneDetail.MilestoneDetailContract
import kotlinx.serialization.Serializable

interface MilestoneComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Create(val component: MilestoneCreateContract.Component) : Child()
        data class Detail(val component: MilestoneDetailContract.Component) : Child()
    }
}

class DefaultMilestoneComponent(
    componentContext: ComponentContext,
    private val goalId: Long?,
    private val parentId: Long?,
    private val storeFactory: StoreFactory,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit,
    private val navigateToDecision: (Long) -> Unit,
    private val navigateToMood: () -> Unit,
    private val navigateToGoal: (Long, String) -> Unit,
    private val navigateToCreateAction: (parentId: Long) -> Unit,
    private val navigateToChallenge: (goalId: Long) -> Unit,
    private val navigateToAppraise: (goalId: Long) -> Unit,
    private val navigateToSolution: (goalId: Long, challengeId: Long) -> Unit
) : MilestoneComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, MilestoneComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = if (goalId == null) Config.Create else Config.Detail(goalId),
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, componentContext: ComponentContext): MilestoneComponent.Child =
        when (config) {
            Config.Create -> MilestoneComponent.Child.Create(
                DefaultMilestoneCreateComponent(
                    componentContext = componentContext,
                    parentId = parentId,
                    storeFactory = storeFactory,
                    getGoalsUseCase = getGoalsUseCase,
                    saveGoalUseCase = saveGoalUseCase,
                    deleteGoalUseCase = deleteGoalUseCase,
                    getChallengesUseCase = getChallengesUseCase,
                    onBack = onBack
                )
            )
            is Config.Detail -> MilestoneComponent.Child.Detail(
                DefaultMilestoneDetailComponent(
                    componentContext = componentContext,
                    goalId = config.goalId,
                    parentId = parentId,
                    storeFactory = storeFactory,
                    getGoalsUseCase = getGoalsUseCase,
                    saveGoalUseCase = saveGoalUseCase,
                    deleteGoalUseCase = deleteGoalUseCase,
                    getChallengesUseCase = getChallengesUseCase,
                    onBack = onBack,
                    navigateToDecision = navigateToDecision,
                    navigateToMood = navigateToMood,
                    navigateToGoal = navigateToGoal,
                    navigateToCreateAction = navigateToCreateAction,
                    navigateToChallenge = navigateToChallenge,
                    navigateToAppraise = navigateToAppraise,
                    navigateToSolution = navigateToSolution
                )
            )
        }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Create : Config()
        @Serializable
        data class Detail(val goalId: Long) : Config()
    }
}
