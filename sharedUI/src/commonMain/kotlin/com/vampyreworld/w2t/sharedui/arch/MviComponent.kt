package com.vampyreworld.w2t.sharedui.arch

import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow

/**
 * Base interface for all Decompose Components that follow MVI pattern.
 */
interface MviComponent<out State : Any, in Intent : Any, out Label : Any> {
    /**
     * The current state of the component, observed by the UI.
     */
    val state: Value<State>

    /**
     * One-time events (Side Effects) like navigation, toast, etc.
     */
    val labels: Flow<Label>

    /**
     * Dispatches an intent to the component.
     */
    fun onIntent(intent: Intent)
}
