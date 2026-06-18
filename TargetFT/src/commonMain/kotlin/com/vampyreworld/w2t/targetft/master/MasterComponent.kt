package com.vampyreworld.w2t.targetft.master

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
import com.vampyreworld.w2t.targetft.master.masterCreate.DefaultMasterCreateComponent
import com.vampyreworld.w2t.targetft.master.masterCreate.MasterCreateContract
import com.vampyreworld.w2t.targetft.master.masterDetail.DefaultMasterDetailComponent
import com.vampyreworld.w2t.targetft.master.masterDetail.MasterDetailContract
import kotlinx.serialization.Serializable

interface MasterComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Create(val component: MasterCreateContract.Component) : Child()
        data class Detail(val component: MasterDetailContract.Component) : Child()
    }
}

class DefaultMasterComponent(
    componentContext: ComponentContext,
    private val goalId: Long?,
    private val storeFactory: StoreFactory,
    private val getGoalsUseCase: GetGoalsUseCase,
    private val saveGoalUseCase: SaveGoalUseCase,
    private val deleteGoalUseCase: DeleteGoalUseCase,
    private val getChallengesUseCase: GetChallengesUseCase,
    private val onBack: () -> Unit,
    private val navigateToDecision: (Long) -> Unit,
    private val navigateToMood: () -> Unit,
    private val navigateToGoal: (Long, String) -> Unit,
    private val navigateToCreateMilestone: (parentId: Long) -> Unit,
    private val navigateToChallenge: (goalId: Long) -> Unit,
    private val navigateToAppraise: (goalId: Long) -> Unit
) : MasterComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, MasterComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = if (goalId == null) Config.Create else Config.Detail(goalId),
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, componentContext: ComponentContext): MasterComponent.Child =
        when (config) {
            Config.Create -> MasterComponent.Child.Create(
                DefaultMasterCreateComponent(
                    componentContext = componentContext,
                    storeFactory = storeFactory,
                    getGoalsUseCase = getGoalsUseCase,
                    saveGoalUseCase = saveGoalUseCase,
                    deleteGoalUseCase = deleteGoalUseCase,
                    getChallengesUseCase = getChallengesUseCase,
                    onBack = onBack
                )
            )
            is Config.Detail -> MasterComponent.Child.Detail(
                DefaultMasterDetailComponent(
                    componentContext = componentContext,
                    goalId = config.goalId,
                    storeFactory = storeFactory,
                    getGoalsUseCase = getGoalsUseCase,
                    saveGoalUseCase = saveGoalUseCase,
                    deleteGoalUseCase = deleteGoalUseCase,
                    getChallengesUseCase = getChallengesUseCase,
                    onBack = onBack,
                    navigateToDecision = navigateToDecision,
                    navigateToMood = navigateToMood,
                    navigateToGoal = navigateToGoal,
                    navigateToCreateMilestone = navigateToCreateMilestone,
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
