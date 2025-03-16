package com.fitfit.bannerit.navigation.signin

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fitfit.bannerit.navigation.enterTransition
import com.fitfit.bannerit.navigation.exitTransition
import com.fitfit.bannerit.navigation.popEnterTransition
import com.fitfit.bannerit.navigation.popExitTransition
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.ExternalState
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.feature.signin.signin.SignInRoute

private val screenDestination = ScreenDestination.SIGN_IN

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.signInScreen(
    appViewModel: AppViewModel,
    externalState: ExternalState,
    isDarkAppTheme: Boolean,

    navigateUp: () -> Unit,
    navigateToMain: () -> Unit
){
    composable(
        route = screenDestination.route,
        enterTransition = { enterTransition },
        exitTransition = { exitTransition },
        popEnterTransition = { popEnterTransition },
        popExitTransition = { popExitTransition }
    ) {
        LaunchedEffect(Unit) {
            appViewModel.updateCurrentScreenDestination(screenDestination)
            appViewModel.initAppUiState()
        }

        SignInRoute(
            isDarkAppTheme = isDarkAppTheme,
            internetEnabled = externalState.internetEnabled,
            appVersionName = com.fitfit.bannerit.BuildConfig.VERSION_NAME,
            updateUserData = {userData ->
                appViewModel.updateUserData(userData = userData)
            },
            navigateToMain = navigateToMain,
            navigateUp = navigateUp
        )
    }
}