package com.vampyreworld.w2t.appraiseft.store

import com.arkivanov.mvikotlin.core.store.Store
import com.vampyreworld.w2t.appraiseft.AppraiseContract

interface AppraiseStore : Store<AppraiseStore.Intent, AppraiseContract.State, AppraiseStore.Label> {
    sealed interface Intent {
        data object Appraise : Intent
    }

    sealed interface Label {
        data object Back : Label
    }
}
