package com.fitfit.bannerit.navigation.mainLogs

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.fitfit.bannerit.navigation.TopEnterTransition
import com.fitfit.bannerit.navigation.TopExitTransition
import com.fitfit.bannerit.navigation.TopLevelDestination
import com.fitfit.bannerit.navigation.TopPopEnterTransition
import com.fitfit.bannerit.navigation.TopPopExitTransition
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.CommonReportRecordsViewModel
import com.fitfit.bannerit.ui.ExternalState
import com.fitfit.bannerit.utils.WindowHeightSizeClass
import com.fitfit.bannerit.utils.WindowWidthSizeClass
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.core.ui.designsystem.components.NAVIGATION_DRAWER_BAR_WIDTH
import com.fitfit.core.ui.designsystem.components.NAVIGATION_RAIL_BAR_WIDTH
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.feature.logs.mainMyRecords.MainMyReportsRoute
import kotlinx.coroutines.delay

private val topLevelScreenDestination = TopLevelDestination.MY_RECORDS
private val screenDestination = ScreenDestination.MAIN_MY_RECORDS

fun NavController.navigateToMainMyRecords(navOptions: NavOptions? = null) =
    navigate(screenDestination.route, navOptions)

fun NavGraphBuilder.mainMyRecordsScreen(
    appViewModel: AppViewModel,
    commonReportRecordsViewModel: CommonReportRecordsViewModel,
    externalState: ExternalState,

    navigateToReportRecordDetail: () -> Unit,
) {
    composable(
        route = screenDestination.route,
        enterTransition = { TopEnterTransition },
        exitTransition = { TopExitTransition },
        popEnterTransition = { TopPopEnterTransition },
        popExitTransition = { TopPopExitTransition }
    ) {

        val appUiState by appViewModel.appUiState.collectAsState()
        val commonReportRecordsUiState by commonReportRecordsViewModel.reportUiState.collectAsState()

        val widthSizeClass = externalState.windowSizeClass.widthSizeClass
        val heightSizeClass = externalState.windowSizeClass.heightSizeClass

        val jwt = appUiState.appUserData?.jwt

        LaunchedEffect(Unit) {
            if (jwt != null) {
                commonReportRecordsViewModel.getAppUserReportRecords(jwt = jwt)
            }
        }

        LaunchedEffect(Unit) {
            appViewModel.updateCurrentTopLevelDestination(topLevelScreenDestination)
            delay(100)
            appViewModel.updateCurrentScreenDestination(screenDestination)
            delay(500)
            commonReportRecordsViewModel.setCurrentReportRecord(null)
        }


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

            MainMyReportsRoute(
                use2Panes = externalState.windowSizeClass.use2Panes,
                spacerValue = externalState.windowSizeClass.spacerValue,
                dateTimeFormat = appUiState.appPreferences.dateTimeFormat,
                appUserReportRecords = commonReportRecordsUiState.appUserReportRecords,
                onClickReportRecord = {
                    commonReportRecordsViewModel.setCurrentReportRecord(it)
                    navigateToReportRecordDetail()
                }
            )
        }
    }
}