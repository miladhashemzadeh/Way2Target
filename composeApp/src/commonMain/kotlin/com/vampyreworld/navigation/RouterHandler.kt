package com.vampyreworld.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController


@Composable
fun RouterHandler(
    navigator: NavHostController,
    router: Router
) {
    LaunchedEffect(Unit) {
        router.collector.collect { destination ->
            when (destination) {
                Screens.PopBackStack -> {
                    navigator.popBackStack()
                }

                is Screens.ClearBackStack -> {
                    navigator.navigate(destination.target) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                }

                is Screens.KeepFirstBackStackAndClearOthers -> {
                    println("RouterHandler: ${destination.target}")
                    navigator.navigate(destination.target) {
                        launchSingleTop = true
                        popUpTo(destination.popUpToTarget) { inclusive = true }
                    }
                }

                is Screens.OpenAndClearBackstack -> {
                    println("RouterHandler OpenAndClearBackstack: ${destination.target}")
                    navigator.navigate(destination.backDestination) {
                        launchSingleTop = true
                        popUpTo(0) { inclusive = true }
                    }
                    navigator.navigate(destination.target) {
                        launchSingleTop = true
                    }
                }

                else -> {
                    navigator.navigate(destination)
                }
            }
        }
    }
}