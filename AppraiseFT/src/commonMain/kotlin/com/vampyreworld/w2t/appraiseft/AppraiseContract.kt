package com.vampyreworld.w2t.appraiseft

interface AppraiseContract {
    data class State(
        val isLoading: Boolean = false,
        val targetId: Long? = null,
        val challengeId: Long? = null,
        val appraisalResult: String = ""
    )

    sealed interface SideEffect {
        data object Back : SideEffect
    }

    sealed interface Intent {
        data object Appraise : Intent
        data object Back : Intent
    }
}
