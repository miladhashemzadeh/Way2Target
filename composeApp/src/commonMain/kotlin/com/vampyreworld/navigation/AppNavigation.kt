package com.vampyreworld.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

/*
import org.koin.compose.koinInject
*/

/**
 * AppNavigation owns the navigation graph, declares typed destinations, and wires RouterHandler.
 */
@Composable
fun AppNavigation(
    /* appRouter: Router = koinInject(),*/
) {
    val navController = rememberNavController()

    // RouterHandler(navigator = navController, router = appRouter)

    NavHost(
        navController = navController,
        startDestination = Screens.Splash
    ) {

        /* composable<Screens.OnBoarding>(
             enterTransition = { NavigationAnimations.fadeIn() },
             exitTransition = { NavigationAnimations.fadeOut() }
         ) {
             OnboardingRoot()
         }

         composable<Screens.Splash>(
             enterTransition = { NavigationAnimations.fadeIn() },
             exitTransition = { NavigationAnimations.fadeOut() }
         ) {
             SplashScreenRoot()
         }

         composable<Screens.Home> {
             HomeRootScreen(
                 navigateToDashboard = { userDevice ->
                     userDevice.id?.let { userDeviceId ->
                         if (currentFlavor() == AppFlavor.LIGHT) {
                             navController.navigate(
                                 Screens.Dashboard(
                                     userDeviceId
                                 )
                             )
                         } else if (currentFlavor() == AppFlavor.PRO) {
                             navController.navigate(
                                 Screens.Message(userDevice.id)
                             )
                         }
                     }
                 },
                 navigateToDebugPanel = {
                     //navController.navigate(Screens.DebugPanel)
                 },
                 onNavigateToBugReportScreen = { filePath, message ->
                     if (filePath.isNullOrBlank() && !message.isNullOrBlank()) {
                         navController.navigate(
                             Screens.BugReport(
                                 message = message,
                                 filePath = null
                             )
                         )
                     } else
                         navController.navigate(
                             Screens.BugReport(
                                 message = null,
                                 filePath = filePath
                             )
                         )
                 },
                 navigateToNotifications = {
                     navController.navigate(Screens.Notification)
                 },
                 navigateToSettingsScreen = {
                     navController.navigate(Screens.Settings)
                 }
             )
         }


         composable<Screens.AboutUs> {
             AboutUsScreen({
                 navController.popBackStack()
             })
         }*/

    }
}