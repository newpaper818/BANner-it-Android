package com.fitfit.bannerit.navigation.mainLogs

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
import com.fitfit.bannerit.ui.CommonReportLogsViewModel
import com.fitfit.bannerit.ui.ExternalState
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.core.ui.ui.ErrorScreen
import com.fitfit.feature.logs.reportLogDetail.ReportLogDetailRoute

private val topLevelScreenDestination = TopLevelDestination.LOGS
private val screenDestination = ScreenDestination.REPORT_LOG_DETAIL

fun NavController.navigateToReportLogDetail(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.reportLogDetailScreen(
    appViewModel: AppViewModel,
    commonReportLogsViewModel: CommonReportLogsViewModel,
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

        val appUiState by appViewModel.appUiState.collectAsState()
        val commonReportLogsUiState by commonReportLogsViewModel.reportUiState.collectAsState()

        val widthSizeClass = externalState.windowSizeClass.widthSizeClass
        val heightSizeClass = externalState.windowSizeClass.heightSizeClass

        if (
            commonReportLogsUiState.currentReportLogIndex != null
            && appUiState.appUserData != null
        ) {
            ReportLogDetailRoute(
                appUserData = appUiState.appUserData!!,
                use2Panes = externalState.windowSizeClass.use2Panes,
                spacerValue = externalState.windowSizeClass.spacerValue,
                dateTimeFormat = appUiState.appPreferences.dateTimeFormat,
                internetEnabled = externalState.internetEnabled,
                reportLog = commonReportLogsUiState.appUserReportLogs[commonReportLogsUiState.currentReportLogIndex!!],
                navigateUp = navigateUp
            )
        }
        else {
            ErrorScreen()
        }
    }
}