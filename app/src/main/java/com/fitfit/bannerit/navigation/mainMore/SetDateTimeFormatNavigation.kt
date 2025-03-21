package com.fitfit.bannerit.navigation.mainMore

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fitfit.bannerit.navigation.TopLevelDestination
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.feature.more.setDateTimeFormat.SetDateTimeFormatRoute
import com.fitfit.bannerit.navigation.enterTransition
import com.fitfit.bannerit.navigation.exitTransition
import com.fitfit.bannerit.navigation.popEnterTransition
import com.fitfit.bannerit.navigation.popExitTransition
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.ExternalState
import kotlinx.coroutines.launch

private val topLevelScreenDestination = TopLevelDestination.MORE
private val screenDestination = ScreenDestination.SET_DATE_TIME_FORMAT

fun NavController.navigateToSetDateTimeFormat(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.setDateTimeFormatScreen(
    appViewModel: AppViewModel,
    externalState: ExternalState,

    navigateUp: () -> Unit,
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

        val coroutineScope = rememberCoroutineScope()

        val appUiState by appViewModel.appUiState.collectAsState()

        SetDateTimeFormatRoute(
            use2Panes = externalState.windowSizeClass.use2Panes,
            spacerValue = externalState.windowSizeClass.spacerValue,
            dateTimeFormat = appUiState.appPreferences.dateTimeFormat,
            updatePreferencesValue = {
                coroutineScope.launch{
                    appViewModel.getAppPreferencesValue()
                }
            },
            navigateUp = navigateUp,
        )

    }
}