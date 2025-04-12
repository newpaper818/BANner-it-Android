package com.fitfit.feature.more.mainMore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.ScreenDestination
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.ui.card.UserProfileCard
import com.fitfit.core.ui.ui.item.ItemDivider
import com.fitfit.core.ui.ui.item.ItemWithText
import com.fitfit.core.ui.ui.item.ListGroupCard
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.more.R

@Composable
fun MainMoreRoute(
    isDebugMode: Boolean,
    appUserData: UserData?,
    internetEnabled: Boolean,

    use2Panes: Boolean,
    spacerValue: Dp,
    lazyListState: LazyListState,

    navigateTo: (ScreenDestination) -> Unit,

    modifier: Modifier = Modifier,
) {


    MainMoreScreen(
        isDebugMode = isDebugMode,
        appUserData = appUserData,
        internetEnabled = internetEnabled,

        startSpacerValue = spacerValue,
        endSpacerValue = if (use2Panes) spacerValue / 2 else spacerValue,
        lazyListState = lazyListState,
        navigateTo = navigateTo
    )
}

@Composable
private fun MainMoreScreen(
    isDebugMode: Boolean,
    appUserData: UserData?,
    internetEnabled: Boolean,

    startSpacerValue: Dp,
    endSpacerValue: Dp,
    lazyListState: LazyListState,

    navigateTo: (ScreenDestination) -> Unit,

    modifier: Modifier = Modifier,
){
    val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)

    MyScaffold(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding(),

        topBar = {
            MyTopAppBar(
                startPadding = startSpacerValue,
                title = stringResource(R.string.more)
            )
        }
    ){ paddingValues ->

        LazyColumn(
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(startSpacerValue, 16.dp, endSpacerValue, 200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            item {
                if(appUserData != null){
                    UserProfileCard(
                        userData = appUserData,
                        internetEnabled = internetEnabled,
                        showSignInWithInfo = false,
                        onProfileClick = { navigateTo(ScreenDestination.ACCOUNT) },
                        modifier = itemModifier
                    )
                }
                else {
                    ListGroupCard(
                        modifier = itemModifier
                    ) {
                        ItemWithText(
                            text = stringResource(id = R.string.sign_in),
                            showClickableIcon = true,
                            onItemClick = { navigateTo(ScreenDestination.SIGN_IN) }
                        )
                    }
                }
            }

            //setting
            item {
                ListGroupCard(
                    title = stringResource(id = R.string.settings),
                    modifier = itemModifier
                ) {
                    //date time format
                    ItemWithText(
                        text = stringResource(id = R.string.date_time_format),
                        showClickableIcon = true,
                        onItemClick = { navigateTo(ScreenDestination.SET_DATE_TIME_FORMAT) }
                    )

                    ItemDivider()

                    //app theme
                    ItemWithText(
                        text = stringResource(id = R.string.theme),
                        showClickableIcon = true,
                        onItemClick = { navigateTo(ScreenDestination.SET_THEME) }
                    )
                }
            }



            //about
            item {
                ListGroupCard(
                    modifier = itemModifier
                ) {
                    ItemWithText(
                        text = stringResource(id = R.string.about),
                        showClickableIcon = true,
                        onItemClick = {
                            navigateTo(ScreenDestination.ABOUT)
                        }
                    )
                }
            }

            if (isDebugMode)
                item{
                    Text(
                        text = "Debug Mode",
                        style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary)
                    )
                }
        }
    }
}