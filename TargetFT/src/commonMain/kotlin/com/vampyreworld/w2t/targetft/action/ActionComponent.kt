package com.vampyreworld.w2t.targetft.action

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
import com.vampyreworld.w2t.targetft.action.actionCreate.ActionCreateContract
import com.vampyreworld.w2t.targetft.action.actionCreate.DefaultActionCreateComponent
import com.vampyreworld.w2t.targetft.action.actionDetail.ActionDetailContract
import com.vampyreworld.w2t.targetft.action.actionDetail.DefaultActionDetailComponent
import kotlinx.serialization.Serializable

interface ActionComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Create(val component: ActionCreateContract.Component) : Child()
        data class Detail(val component: ActionDetailContract.Component) : Child()
    }
}

class DefaultActionComponent(
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
    private val navigateToChallenge: (goalId: Long) -> Unit,
    private val navigateToAppraise: (goalId: Long) -> Unit
) : ActionComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, ActionComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = if (goalId == null) Config.Create else Config.Detail(goalId),
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, componentContext: ComponentContext): ActionComponent.Child =
        when (config) {
            Config.Create -> ActionComponent.Child.Create(
                DefaultActionCreateComponent(
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
            is Config.Detail -> ActionComponent.Child.Detail(
                DefaultActionDetailComponent(
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
                    navigateToChallenge = navigateToChallenge,
                    navigateToAppraise = navigateToAppraise
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
