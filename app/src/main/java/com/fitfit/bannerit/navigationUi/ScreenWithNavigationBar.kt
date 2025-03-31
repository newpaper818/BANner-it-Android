package com.fitfit.bannerit.navigationUi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fitfit.bannerit.navigation.TopLevelDestination
import com.fitfit.bannerit.utils.WindowHeightSizeClass
import com.fitfit.bannerit.utils.WindowSizeClass
import com.fitfit.bannerit.utils.WindowWidthSizeClass
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.core.ui.designsystem.components.MyNavigationBottomBar
import com.fitfit.core.ui.designsystem.components.MyNavigationBottomBarItem
import com.fitfit.core.ui.designsystem.components.MyNavigationDrawer
import com.fitfit.core.ui.designsystem.components.MyNavigationDrawerItem
import com.fitfit.core.ui.designsystem.components.MyNavigationRailBar
import com.fitfit.core.ui.designsystem.components.MyNavigationRailBarItem

@Composable
fun ScreenWithNavigationBar(
    windowSizeClass: WindowSizeClass,
    currentTopLevelDestination: TopLevelDestination,
    currentScreenDestination: ScreenDestination,
    showNavigationBar: Boolean,

    onClickNavBarItem: (TopLevelDestination) -> Unit,
    onClickNavBarItemAgain: (TopLevelDestination) -> Unit,

    modifier: Modifier = Modifier,
    topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries,
    content: @Composable () -> Unit = {}
) {

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        //phone vertical ===========================================================================
        if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxSize()
            ) {
                //content
                content()

                //bottom navigation bar
                AnimatedVisibility(
                    visible = showNavigationBar,
                    enter = slideInVertically(tween(300), initialOffsetY = { it }),
                    exit = slideOutVertically(tween(300), targetOffsetY = { it })
                ) {
                    MyNavigationBottomBar{
                        topLevelDestinations.forEach {
                            MyNavigationBottomBarItem(
                                selected = it == currentTopLevelDestination,
                                onClick = {
                                    if (it != currentTopLevelDestination)
                                        onClickNavBarItem(it)
                                    else
                                        onClickNavBarItemAgain(it)
                                },
                                selectedIcon = it.selectedIcon,
                                unSelectedIcon = it.unselectedIcon,
                                labelText = stringResource(id = it.labelTextId)
                            )
                        }
                    }
                }
            }
        }

        //phone horizontal (height compact) ========================================================
        //or foldable vertical or tablet vertical (width medium)
        else if (
            windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact
            || windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium
        ) {
            val modifier = if (currentScreenDestination == ScreenDestination.IMAGE)
                Modifier.fillMaxSize()
            else
                Modifier.displayCutoutPadding().fillMaxSize()

            Box(
                modifier = modifier
            ) {
                //content
                content()

                //navigation rail bar
                AnimatedVisibility(
                    visible = showNavigationBar,
                    enter = slideInHorizontally(tween(300), initialOffsetX = { -it*2 }),
                    exit = slideOutHorizontally(tween(300), targetOffsetX = { -it*2 })
                ) {
                    MyNavigationRailBar {
                        topLevelDestinations.forEach {
                            MyNavigationRailBarItem(
                                selected = it == currentTopLevelDestination,
                                onClick = {
                                    if (it != currentTopLevelDestination)
                                        onClickNavBarItem(it)
                                    else
                                        onClickNavBarItemAgain(it)
                                },
                                selectedIcon = it.selectedIcon,
                                unSelectedIcon = it.unselectedIcon,
                                labelText = stringResource(id = it.labelTextId)
                            )
                        }
                    }
                }
            }
        }

        //foldable horizontal or tablet horizontal =================================================
        else if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                //content
                content()

                //navigation drawer bar
                AnimatedVisibility(
                    visible = showNavigationBar,
                    enter = slideInHorizontally(tween(300), initialOffsetX = { -it }),
                    exit = slideOutHorizontally(tween(300), targetOffsetX = { -it })
                ) {
                    MyNavigationDrawer {
                        topLevelDestinations.forEach {
                            MyNavigationDrawerItem(
                                selected = it == currentTopLevelDestination,
                                onClick = {
                                    if (it != currentTopLevelDestination)
                                        onClickNavBarItem(it)
                                    else
                                        onClickNavBarItemAgain(it)
                                },
                                selectedIcon = it.selectedIcon,
                                unSelectedIcon = it.unselectedIcon,
                                labelText = stringResource(id = it.labelTextId)
                            )
                        }
                    }
                }
            }
        }
    }
}

