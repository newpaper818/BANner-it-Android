package com.fitfit.bannerit.navigation.mainMore

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fitfit.bannerit.navigation.TopLevelDestination
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.feature.more.account.AccountRoute
import com.fitfit.bannerit.navigation.enterTransition
import com.fitfit.bannerit.navigation.exitTransition
import com.fitfit.bannerit.navigation.popEnterTransition
import com.fitfit.bannerit.navigation.popExitTransition
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.ExternalState

private val topLevelScreenDestination = TopLevelDestination.MORE
private val screenDestination = ScreenDestination.ACCOUNT

fun NavController.navigateToAccount(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.accountScreen(
    appViewModel: AppViewModel,
    externalState: ExternalState,

    navigateUp: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToDeleteAccount: () -> Unit,
    navigateToMainMore: () -> Unit,

    modifier: Modifier = Modifier,
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


        AccountRoute(
            use2Panes = externalState.windowSizeClass.use2Panes,
            userData = appUiState.appUserData,
            internetEnabled = externalState.internetEnabled,
            spacerValue = externalState.windowSizeClass.spacerValue,
            updateUserDataToNull = {
                appViewModel.updateUserData(null)
            },
            navigateUp = navigateUp,
            navigateToEditProfile = navigateToEditProfile,
            navigateToDeleteAccount = navigateToDeleteAccount,
            navigateToMainMore = navigateToMainMore,
            modifier = modifier
        )
    }
}