package com.vampyreworld.w2t.appraiseft.store

import com.arkivanov.mvikotlin.core.store.Store
import com.vampyreworld.w2t.appraiseft.AppraiseContract

interface AppraiseStore : Store<AppraiseStore.Intent, AppraiseContract.State, AppraiseStore.Label> {
    sealed interface Intent {
        data object Back : Intent
        data object Appraise : Intent
        data class ChangeChallengeStatus(val status: String) : Intent
        data class SelectSolution(val solutionId: Long) : Intent
        data class ChangeReflection(val reflection: String) : Intent
        data object UpdateChallenge : Intent
        data object CompleteGoal : Intent
        data object ArchiveGoal : Intent
    }

    sealed interface Label {
        data object Back : Label
    }
}
