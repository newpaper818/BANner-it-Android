package com.fitfit.bannerit.navigation.mainMore

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fitfit.bannerit.BuildConfig
import com.fitfit.bannerit.navigation.TopLevelDestination
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.feature.more.about.AboutRoute
import com.fitfit.bannerit.navigation.enterTransition
import com.fitfit.bannerit.navigation.exitTransition
import com.fitfit.bannerit.navigation.popEnterTransition
import com.fitfit.bannerit.navigation.popExitTransition
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.ExternalState

private val topLevelScreenDestination = TopLevelDestination.MORE
private val screenDestination = ScreenDestination.ABOUT

fun NavController.navigateToAbout(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.aboutScreen(
    appViewModel: AppViewModel,
    externalState: ExternalState,

    navigateUp: () -> Unit,
    navigateToOpenSourceLicense: () -> Unit,

    modifier: Modifier = Modifier
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

        val appUiState by appViewModel.appUiState.collectAsState()

        val widthSizeClass = externalState.windowSizeClass.widthSizeClass
        val heightSizeClass = externalState.windowSizeClass.heightSizeClass

        AboutRoute(
            use2Panes = externalState.windowSizeClass.use2Panes,
            spacerValue = externalState.windowSizeClass.spacerValue,
            currentAppVersionCode = BuildConfig.VERSION_CODE,
            currentAppVersionName = BuildConfig.VERSION_NAME,
            isDebugMode = BuildConfig.DEBUG,
            navigateToOpenSourceLicense = navigateToOpenSourceLicense,
            navigateUp = navigateUp,
            modifier = modifier
        )
    }
}