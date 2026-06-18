package com.vampyreworld.navigation

import kotlinx.serialization.Serializable
@Serializable
sealed class Screens {
    @Serializable
    data object PopBackStack : Screens()

    @Serializable
    data class KeepFirstBackStackAndClearOthers(
        val target: Screens,
        val popUpToTarget: Screens,
    ) : Screens()

    @Serializable
    data class OpenAndClearBackstack(
        val target: Screens,
        val backDestination: Screens,
    ) : Screens()

    @Serializable
    data class ClearBackStack(val target: Screens) : Screens()

    @Serializable
    data object TargetMaster : Screens()
    @Serializable
    data class AddGoal(val parentId: Long?, val tier: String) : Screens()
    @Serializable
    data class TargetDetail(val goalId: Long, val tier: String = "MASTER", val parentId: Long? = null) : Screens()

    @Serializable
    data class ListOfChallenges(val goalId: Long?) : Screens()
    @Serializable
    data class AddChallenge(val goalId: Long?) : Screens()
    @Serializable
    data class DetailOfChallenge(val goalId: Long, val challengeId: Long) : Screens()

    @Serializable
    data class ListOfSolutions(val goalId: Long?, val challengeId: Long?) : Screens()
    @Serializable
    data class AddSolution(val goalId: Long?, val challengeId: Long?) : Screens()

    @Serializable
    data class DecisionForTarget(val goalId: Long?) : Screens()
    @Serializable
    data class DecisionForChallenge(val goalId: Long?, val challengeId: Long) : Screens()

    @Serializable
    data class AppraiseTarget(val goalId: Long) : Screens()
    @Serializable
    data class AppraiseChallenge(val goalId: Long, val challengeId: Long) : Screens()

    @Serializable
    data object Preferences : Screens()
    @Serializable
    data object Profile : Screens()
    @Serializable
    data object OnBoarding : Screens()
    @Serializable
    data object Splash : Screens()
    @Serializable
    data object Home : Screens()
    @Serializable
    data object AddMood : Screens()
    @Serializable
    data object AboutUs : Screens()
}
