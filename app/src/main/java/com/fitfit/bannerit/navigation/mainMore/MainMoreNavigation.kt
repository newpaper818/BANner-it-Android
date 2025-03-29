package com.fitfit.bannerit.navigation.mainMore

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.core.ui.designsystem.components.NAVIGATION_DRAWER_BAR_WIDTH
import com.fitfit.core.ui.designsystem.components.NAVIGATION_RAIL_BAR_WIDTH
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.feature.more.mainMore.MainMoreRoute
import com.fitfit.bannerit.navigation.TopEnterTransition
import com.fitfit.bannerit.navigation.TopExitTransition
import com.fitfit.bannerit.navigation.TopLevelDestination
import com.fitfit.bannerit.navigation.TopPopEnterTransition
import com.fitfit.bannerit.navigation.TopPopExitTransition
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.ExternalState
import com.fitfit.bannerit.utils.WindowHeightSizeClass
import com.fitfit.bannerit.utils.WindowWidthSizeClass
import kotlinx.coroutines.delay


private val topLevelScreenDestination = TopLevelDestination.MORE
private val screenDestination = ScreenDestination.MAIN_MORE

fun NavController.navigateToMainMore(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.mainMoreScreen(
    appViewModel: AppViewModel,
    externalState: ExternalState,

    navigateTo: (ScreenDestination) -> Unit,
) {
    composable(
        route = screenDestination.route,
        enterTransition = { TopEnterTransition },
        exitTransition = { TopExitTransition },
        popEnterTransition = { TopPopEnterTransition },
        popExitTransition = { TopPopExitTransition }
    ) {
        LaunchedEffect(Unit) {
            appViewModel.updateCurrentTopLevelDestination(topLevelScreenDestination)
            delay(100)
            appViewModel.updateCurrentScreenDestination(screenDestination)
        }

        val appUiState by appViewModel.appUiState.collectAsState()

        val widthSizeClass = externalState.windowSizeClass.widthSizeClass
        val heightSizeClass = externalState.windowSizeClass.heightSizeClass

        Row {
            if (widthSizeClass == WindowWidthSizeClass.Compact) {
                MySpacerRow(width = 0.dp)
            } else if (
                heightSizeClass == WindowHeightSizeClass.Compact
                || widthSizeClass == WindowWidthSizeClass.Medium
            ) {
                MySpacerRow(width = NAVIGATION_RAIL_BAR_WIDTH)
            } else if (widthSizeClass == WindowWidthSizeClass.Expanded) {
                MySpacerRow(width = NAVIGATION_DRAWER_BAR_WIDTH)
            }

            MainMoreRoute(
                isDebugMode = com.fitfit.bannerit.BuildConfig.DEBUG,
                appUserData = appUiState.appUserData,
                internetEnabled = externalState.internetEnabled,
                use2Panes = externalState.windowSizeClass.use2Panes,
                spacerValue = externalState.windowSizeClass.spacerValue,
                navigateTo = navigateTo
            )
        }
    }
}