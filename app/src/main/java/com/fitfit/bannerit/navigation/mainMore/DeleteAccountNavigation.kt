package com.fitfit.bannerit.navigation.mainMore

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.fitfit.core.ui.ui.ErrorScreen
import com.fitfit.feature.more.deleteAccount.DeleteAccountRoute

private val topLevelScreenDestination = TopLevelDestination.MORE
private val screenDestination = ScreenDestination.DELETE_ACCOUNT

fun NavController.navigateToDeleteAccount(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.deleteAccountScreen(
    appViewModel: AppViewModel,
    externalState: ExternalState,
    isDarkAppTheme: Boolean,

    navigateUp: () -> Unit,
    navigateToMainReportScreen: () -> Unit,
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


        if (appUiState.appUserData != null) {
            DeleteAccountRoute(
                isDarkAppTheme = isDarkAppTheme,
                userData = appUiState.appUserData!!,
                internetEnabled = externalState.internetEnabled,
                spacerValue = externalState.windowSizeClass.spacerValue,
                navigateUp = navigateUp,
                onDeleteAccountDone = {
                    appViewModel.updateUserData(null)
                    navigateToMainReportScreen()
                },
            )
        }
        else {
            ErrorScreen(onClickGoBack = navigateUp)
        }
    }
}