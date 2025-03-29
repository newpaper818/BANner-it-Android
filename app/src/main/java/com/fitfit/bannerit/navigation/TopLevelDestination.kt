package com.fitfit.bannerit.navigation

import androidx.annotation.StringRes
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.core.ui.designsystem.icon.MyIcon
import com.fitfit.core.ui.designsystem.icon.NavigationBarIcon
import com.fitfit.bannerit.R

enum class TopLevelDestination(
    val selectedIcon: MyIcon,
    val unselectedIcon: MyIcon,
    @StringRes val labelTextId: Int,
    val route: String
) {
    REPORT(
        selectedIcon = NavigationBarIcon.reportFilled,
        unselectedIcon = NavigationBarIcon.reportOutlined,
        labelTextId = R.string.report,
        route = ScreenDestination.MAIN_REPORT.route
    ),
    LOOKUP(
        selectedIcon = NavigationBarIcon.lookupFilled,
        unselectedIcon = NavigationBarIcon.lookupOutlined,
        labelTextId = R.string.lookup,
        route = ScreenDestination.MAIN_LOOKUP.route
    ),
    MY_RECORDS(
        selectedIcon = NavigationBarIcon.myRecordsFilled,
        unselectedIcon = NavigationBarIcon.myRecordsOutlined,
        labelTextId = R.string.my_records,
        route = ScreenDestination.MAIN_MY_RECORDS.route
    ),
    MORE(
        selectedIcon = NavigationBarIcon.moreFilled,
        unselectedIcon = NavigationBarIcon.moreOutlined,
        labelTextId = R.string.more,
        route = ScreenDestination.MAIN_MORE.route
    ),
}