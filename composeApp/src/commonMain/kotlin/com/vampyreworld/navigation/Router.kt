package com.vampyreworld.navigation

import kotlinx.coroutines.flow.SharedFlow


interface Router {

    val collector: SharedFlow<Screens>
    suspend fun goto(destination: Screens)
    fun currentDestination(): Screens
    suspend fun popBackStack() = goto(Screens.PopBackStack)
    suspend fun gotoAndClearBackStack(destination: Screens) =
        goto(Screens.ClearBackStack((destination)))

    suspend fun gotoAndKeepFirstBackStack(destination: Screens, popUpToTarget: Screens) =
        goto(Screens.KeepFirstBackStackAndClearOthers(destination, popUpToTarget))

    suspend fun openAndClearBackstack(destination: Screens, backDestination: Screens) =
        goto(Screens.OpenAndClearBackstack(destination, backDestination))


}

