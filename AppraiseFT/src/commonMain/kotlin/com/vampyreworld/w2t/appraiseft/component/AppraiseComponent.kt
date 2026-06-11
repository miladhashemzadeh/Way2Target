package com.vampyreworld.w2t.appraiseft.component

import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.appraiseft.AppraiseContract
import kotlinx.coroutines.flow.Flow

interface AppraiseComponent {
    val state: Value<AppraiseContract.State>
    val sideEffects: Flow<AppraiseContract.SideEffect>
    
    fun onIntent(intent: AppraiseContract.Intent)
}
