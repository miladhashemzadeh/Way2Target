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
    data class Challenge(val challengeId: String,val goalId: String) : Screens()
    @Serializable
    data class DecisionMaking(val challengeId: String,val goalId: String) : Screens()
    @Serializable
    data object AddChallenge : Screens()
    @Serializable
    data object AddMood : Screens()
    @Serializable
    data object AddSolution : Screens()
    @Serializable
    data class Solution(val solutionId: String) : Screens()
    @Serializable
    data class Goal(val goalId: String?): Screens()
    @Serializable
    data object AddGoal : Screens()

}