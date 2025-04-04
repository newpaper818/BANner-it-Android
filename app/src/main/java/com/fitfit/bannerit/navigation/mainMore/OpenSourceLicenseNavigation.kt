package com.fitfit.bannerit.navigation.mainMore

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fitfit.bannerit.navigation.TopLevelDestination
import com.fitfit.bannerit.navigation.enterTransition
import com.fitfit.bannerit.navigation.exitTransition
import com.fitfit.bannerit.navigation.popEnterTransition
import com.fitfit.bannerit.navigation.popExitTransition
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.ExternalState
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.feature.more.openSourceLicense.OpenSourceLicenseRoute

private val topLevelScreenDestination = TopLevelDestination.MORE
private val screenDestination = ScreenDestination.OPEN_SOURCE_LICENSE

fun NavController.navigateToOpenSourceLicense(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.openSourceLicenseScreen(
    externalState: ExternalState,
    appViewModel: AppViewModel,
    navigateUp: () -> Unit
) {
    composable(
        route = screenDestination.route,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition }
    ) {
        LaunchedEffect(Unit) {
            appViewModel.updateCurrentScreenDestination(screenDestination)
        }

        OpenSourceLicenseRoute(
            startSpacerValue = externalState.windowSizeClass.spacerValue,
            navigateUp = navigateUp
        )
    }
}