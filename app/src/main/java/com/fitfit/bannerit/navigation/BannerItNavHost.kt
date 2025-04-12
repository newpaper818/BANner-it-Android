package com.fitfit.bannerit.navigation

import android.util.Log
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.fitfit.bannerit.navigation.image.imageScreen
import com.fitfit.bannerit.navigation.image.navigateToImage
import com.fitfit.bannerit.navigation.mainLogs.mainLookupScreen
import com.fitfit.bannerit.navigation.mainLogs.mainMyRecordsScreen
import com.fitfit.bannerit.navigation.mainLogs.navigateToReportRecordDetail
import com.fitfit.bannerit.navigation.mainLogs.reportRecordDetailScreen
import com.fitfit.bannerit.navigation.mainMore.aboutScreen
import com.fitfit.bannerit.navigation.mainMore.accountScreen
import com.fitfit.bannerit.navigation.mainMore.deleteAccountScreen
import com.fitfit.bannerit.navigation.mainMore.editProfileScreen
import com.fitfit.bannerit.navigation.mainMore.mainMoreScreen
import com.fitfit.bannerit.navigation.mainMore.navigateToDeleteAccount
import com.fitfit.bannerit.navigation.mainMore.navigateToEditProfile
import com.fitfit.bannerit.navigation.mainMore.navigateToMainMore
import com.fitfit.bannerit.navigation.mainMore.navigateToOpenSourceLicense
import com.fitfit.bannerit.navigation.mainMore.openSourceLicenseScreen
import com.fitfit.bannerit.navigation.mainMore.setDateTimeFormatScreen
import com.fitfit.bannerit.navigation.mainMore.setThemeScreen
import com.fitfit.bannerit.navigation.mainReport.cameraScreen
import com.fitfit.bannerit.navigation.mainReport.mainReportScreen
import com.fitfit.bannerit.navigation.mainReport.navigateToCamera
import com.fitfit.bannerit.navigation.mainReport.navigateToMainReport
import com.fitfit.bannerit.navigation.mainReport.navigateToReport
import com.fitfit.bannerit.navigation.mainReport.reportScreen
import com.fitfit.bannerit.navigation.signin.navigateToSignIn
import com.fitfit.bannerit.navigation.signin.signInScreen
import com.fitfit.bannerit.navigationUi.ScreenWithNavigationBar
import com.fitfit.bannerit.ui.AppViewModel
import com.fitfit.bannerit.ui.CommonReportRecordsViewModel
import com.fitfit.bannerit.ui.ExternalState
import com.fitfit.core.model.enums.ScreenDestination
import java.util.UUID

@Composable
fun BannerItNavHost(
    externalState: ExternalState,
    appViewModel: AppViewModel,

    isDarkAppTheme: Boolean,
    startDestination: String,

    commonReportRecordsViewModel: CommonReportRecordsViewModel = hiltViewModel(),

    modifier: Modifier = Modifier,
) {
    val mainNavController = rememberNavController()
//    val moreNavController = rememberNavController()

    var moreNavKey by rememberSaveable(
        stateSaver = Saver({ it.toString() }, UUID::fromString),
    ) {
        mutableStateOf(UUID.randomUUID())
    }
    val moreNavController = key(moreNavKey) {
        rememberNavController()
    }

    val appUiState by appViewModel.appUiState.collectAsState()

    val context = LocalContext.current

    val navigateUp = {
        if (mainNavController.previousBackStackEntry != null) {
            mainNavController.navigateUp()
        }
    }

    val isOnTopLevel = appUiState.screenDestination.currentScreenDestination == ScreenDestination.MAIN_REPORT
            || appUiState.screenDestination.currentScreenDestination == ScreenDestination.MAIN_LOOKUP
            || appUiState.screenDestination.currentScreenDestination == ScreenDestination.MAIN_MY_RECORDS
            || appUiState.screenDestination.currentScreenDestination == ScreenDestination.MAIN_MORE

//    val isOnMoreList = appUiState.screenDestination.currentScreenDestination == ScreenDestination.MORE

//    val isOnMoreDetail = appUiState.screenDestination.currentScreenDestination == ScreenDestination.SET_DATE_TIME_FORMAT
//            || appUiState.screenDestination.currentScreenDestination == ScreenDestination.SET_THEME
//            || appUiState.screenDestination.currentScreenDestination == ScreenDestination.ACCOUNT
//            || appUiState.screenDestination.currentScreenDestination == ScreenDestination.ABOUT
//
//    val isOnMore3rd = appUiState.screenDestination.currentScreenDestination == ScreenDestination.EDIT_PROFILE
//            || appUiState.screenDestination.currentScreenDestination == ScreenDestination.DELETE_ACCOUNT
//            || appUiState.screenDestination.currentScreenDestination == ScreenDestination.OPEN_SOURCE_LICENSE


    val showNavigationBar =
        if (!externalState.windowSizeClass.use2Panes) isOnTopLevel
        else isOnTopLevel
//                || isOnMoreDetail


    var beforeUse2Panes by rememberSaveable {
        mutableStateOf(externalState.windowSizeClass.use2Panes)
    }


    val tripsLazyListState = rememberLazyListState()
    val profileLazyListState = rememberLazyListState()
    val moreLazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()


//    val onSignOutDone = {
//        mainNavController.navigateToSignIn(
//            navOptions = navOptions {
//                popUpTo(0) { inclusive = true }
//                launchSingleTop = true
//            }
//        )
//        appViewModel.updateCurrentTopLevelDestination(TopLevelDestination.TRIPS)
//    }

    mainNavController.addOnDestinationChangedListener { controller, _, _ ->
        val routes = controller
            .currentBackStack.value
            .map { it.destination.route }
            .joinToString(", ")

        Log.d("mainBackStackLog", "main BackStack: $routes")
    }


    ScreenWithNavigationBar(
        windowSizeClass = externalState.windowSizeClass,
        currentTopLevelDestination = appUiState.screenDestination.currentTopLevelDestination,
        currentScreenDestination = appUiState.screenDestination.currentScreenDestination,
        showNavigationBar = showNavigationBar,
        topLevelDestinations =
            if (appUiState.appUserData == null) listOf(TopLevelDestination.REPORT, TopLevelDestination.LOOKUP, TopLevelDestination.MORE)
            else TopLevelDestination.entries,
        onClickNavBarItem = {
            val prevTopLevelDestination = appUiState.screenDestination.currentTopLevelDestination
            val currentMoreDetailScreenDestination = appUiState.screenDestination.currentScreenDestination

            moreNavKey = UUID.randomUUID()
            mainNavController.navigate(
                route = it.route,
                navOptions = navOptions{
                    popUpTo(TopLevelDestination.REPORT.route) { inclusive = it == TopLevelDestination.REPORT }
                    launchSingleTop = true
                }
            )

//            if (it != TopLevelDestination.TRIPS)
//                tripsViewModel.setLoadingTrips(true)

            if (
                externalState.windowSizeClass.use2Panes
                && prevTopLevelDestination == TopLevelDestination.MORE
                && it != TopLevelDestination.MORE
            ){
                appViewModel.updateMoreDetailCurrentScreenDestination(currentMoreDetailScreenDestination)
            }
        },
        onClickNavBarItemAgain = {
//            coroutineScope.launch {
//                when (it) {
//                    TopLevelDestination.TRIPS -> tripsLazyListState.animateScrollToItem(0)
//                    TopLevelDestination.PROFILE -> profileLazyListState.animateScrollToItem(0)
//                    TopLevelDestination.MORE -> moreLazyListState.animateScrollToItem(0)
//                }
//            }
        }
    ) {

        NavHost(
            navController = mainNavController,
            startDestination = startDestination,
            modifier = modifier
        ) {



            //signIn ===============================================================================
            signInScreen(
                appViewModel = appViewModel,
                externalState = externalState,
                isDarkAppTheme = isDarkAppTheme,
                navigateUp = navigateUp,
                navigateToMain = {
                    mainNavController.navigate(
                        route = ScreenDestination.MAIN_REPORT.route,
                        navOptions = navOptions {
                            popUpTo(ScreenDestination.SIGN_IN.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    )
                }
            )

            //image ================================================================================
            imageScreen(
                appViewModel = appViewModel,
                commonReportRecordsViewModel = commonReportRecordsViewModel,
                externalState = externalState,
                navigateUp = navigateUp
            )

            //top level screen =====================================================================
            mainReportScreen(
                appViewModel = appViewModel,
                externalState = externalState,
                navigateToSignIn = {
                    mainNavController.navigateToSignIn(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                },
                navigateToReport = {
                    mainNavController.navigateToReport(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                }
            )

            mainLookupScreen(
                appViewModel = appViewModel,
                commonReportRecordsViewModel = commonReportRecordsViewModel,
                externalState = externalState,
                navigateToReportRecordDetail = {
                    mainNavController.navigateToReportRecordDetail(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                }
            )

            mainMyRecordsScreen(
                appViewModel = appViewModel,
                commonReportRecordsViewModel = commonReportRecordsViewModel,
                externalState = externalState,
                navigateToReportRecordDetail = {
                    mainNavController.navigateToReportRecordDetail(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                }
            )

            mainMoreScreen(
                appViewModel = appViewModel,
                externalState = externalState,
                navigateTo = {
                    mainNavController.navigate(
                        route = it.route,
                        navOptions = navOptions { launchSingleTop = true }
                    )
                }
            )



            //from main report ====================================================================
            reportScreen(
                appViewModel = appViewModel,
                commonReportRecordsViewModel = commonReportRecordsViewModel,
                externalState = externalState,
                navigateUp = navigateUp,
                navigateToCamera = {
                    mainNavController.navigateToCamera(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                },
                navigateToImage = {
                    mainNavController.navigateToImage(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                }
            )

            cameraScreen(
                appViewModel = appViewModel,
                navigateUp = navigateUp
            )



            //from main logs  ======================================================================
            reportRecordDetailScreen(
                appViewModel = appViewModel,
                commonReportRecordsViewModel = commonReportRecordsViewModel,
                externalState = externalState,
                navigateUp = navigateUp,
                navigateToImage = {
                    mainNavController.navigateToImage(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                }
            )



            //from main more  ======================================================================
            accountScreen(
                appViewModel = appViewModel,
                commonReportRecordsViewModel = commonReportRecordsViewModel,
                externalState = externalState,
                navigateUp = navigateUp,
                navigateToEditProfile = {
                    mainNavController.navigateToEditProfile(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                },
                navigateToDeleteAccount = {
                    mainNavController.navigateToDeleteAccount(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                },
                navigateToMainMore = {
                    mainNavController.navigateToMainMore(
                        navOptions = navOptions {
                            popUpTo(ScreenDestination.ACCOUNT.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    )
                }
            )

            setDateTimeFormatScreen(
                appViewModel = appViewModel,
                externalState = externalState,
                navigateUp = navigateUp,
            )

            setThemeScreen(
                appViewModel = appViewModel,
                externalState = externalState,
                navigateUp = navigateUp,
            )

            aboutScreen(
                appViewModel = appViewModel,
                externalState = externalState,
                navigateUp = navigateUp,
                navigateToOpenSourceLicense = {
                    mainNavController.navigateToOpenSourceLicense(
                        navOptions = navOptions { launchSingleTop = true }
                    )
                }
            )

            editProfileScreen(
                appViewModel = appViewModel,
                externalState = externalState,
                navigateUp = navigateUp,
                navigateToSomeScreen = { }
            )

            deleteAccountScreen(
                appViewModel = appViewModel,
                externalState = externalState,
                isDarkAppTheme = isDarkAppTheme,
                navigateUp = navigateUp,
                navigateToMainReportScreen = {
                    mainNavController.navigateToMainReport(
                        navOptions = navOptions {
                            popUpTo(mainNavController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    )
                }
            )

            openSourceLicenseScreen(
                externalState = externalState,
                appViewModel = appViewModel,
                navigateUp = navigateUp
            )
        }
    }
}




