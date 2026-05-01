package com.vampyreworld.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AppRouterImpl : Router {

    // Use replay = 1 so late collectors (common on Android due to lifecycle) receive the latest event
    private val channel = MutableSharedFlow<Screens>(replay = 1)
    private var lastEmitted: Screens = Screens.Splash

    override val collector: SharedFlow<Screens>
        get() = channel.asSharedFlow()

    override suspend fun goto(destination: Screens) {
        // Keep track of the latest destination without changing SharedFlow replay behavior
        lastEmitted = destination
        channel.emit(destination)
    }

    override fun currentDestination(): Screens {
        return lastEmitted
    }

}