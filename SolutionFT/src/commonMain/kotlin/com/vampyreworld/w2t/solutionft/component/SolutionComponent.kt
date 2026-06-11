package com.vampyreworld.w2t.solutionft.component

import com.arkivanov.decompose.value.Value
import com.vampyreworld.w2t.solutionft.SolutionContract
import kotlinx.coroutines.flow.Flow

interface SolutionComponent {
    val state: Value<SolutionContract.State>
    val sideEffects: Flow<SolutionContract.SideEffect>
    
    fun onIntent(intent: SolutionContract.Intent)
}
