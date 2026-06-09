package com.vampyreworld.w2t.schallengeft.component

import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.schallengeft.SChallengeContract
import kotlinx.coroutines.flow.Flow

interface SChallengeComponent {
    val state: Value<SChallengeContract.State>
    val sideEffects: Flow<SChallengeContract.SideEffect>
    
    fun onIntent(intent: SChallengeContract.Intent)
}
