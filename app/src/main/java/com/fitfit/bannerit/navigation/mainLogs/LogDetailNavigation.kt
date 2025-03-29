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
import com.fitfit.bannerit.ui.CommonReportRecordsViewModel
import com.fitfit.bannerit.ui.ExternalState
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.core.ui.ui.ErrorScreen
import com.fitfit.feature.logs.reportRecordDetail.ReportRecordDetailRoute

private val topLevelScreenDestination = TopLevelDestination.MY_RECORDS
private val screenDestination = ScreenDestination.REPORT_RECORD_DETAIL

fun NavController.navigateToReportRecordDetail(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.reportRecordDetailScreen(
    appViewModel: AppViewModel,
    commonReportRecordsViewModel: CommonReportRecordsViewModel,
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
        val commonReportRecordsUiState by commonReportRecordsViewModel.reportUiState.collectAsState()

        val widthSizeClass = externalState.windowSizeClass.widthSizeClass
        val heightSizeClass = externalState.windowSizeClass.heightSizeClass

        if (
            commonReportRecordsUiState.currentReportRecord != null
            && appUiState.appUserData != null
        ) {
            ReportRecordDetailRoute(
                appUserData = appUiState.appUserData!!,
                use2Panes = externalState.windowSizeClass.use2Panes,
                spacerValue = externalState.windowSizeClass.spacerValue,
                dateTimeFormat = appUiState.appPreferences.dateTimeFormat,
                internetEnabled = externalState.internetEnabled,
                reportRecord = commonReportRecordsUiState.currentReportRecord!!,
                navigateUp = navigateUp
            )
        }
        else {
            ErrorScreen(
                onClickGoBack = navigateUp
            )
        }
    }
}