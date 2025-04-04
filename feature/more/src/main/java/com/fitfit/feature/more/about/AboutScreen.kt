package com.fitfit.feature.more.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.ui.card.AppIconCard
import com.fitfit.core.ui.ui.card.ContactCard
import com.fitfit.core.ui.ui.card.DeveloperCard
import com.fitfit.core.ui.ui.card.VersionCard
import com.fitfit.core.ui.ui.item.ItemDivider
import com.fitfit.core.ui.ui.item.ItemWithText
import com.fitfit.core.ui.ui.item.ListGroupCard
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.core.utils.onClickPrivacyPolicy
import com.fitfit.feature.more.R

@Composable
fun AboutRoute(
    use2Panes: Boolean,
    spacerValue: Dp,

    currentAppVersionCode: Int,
    currentAppVersionName: String,
    isDebugMode: Boolean,

    navigateToOpenSourceLicense: () -> Unit,
    navigateUp: () -> Unit,

    modifier: Modifier = Modifier,
) {

    val snackBarHostState = remember { SnackbarHostState() }

    AboutScreen(
        use2Panes = use2Panes,
        startSpacerValue = if (use2Panes) spacerValue / 2 else spacerValue,
        endSpacerValue = spacerValue,
        currentAppVersionName = currentAppVersionName,
        isDebugMode = isDebugMode,
        navigateToOpenSourceLicense = navigateToOpenSourceLicense,
        navigateUp = navigateUp,
        snackBarHostState = snackBarHostState,
        modifier = modifier
    )
}

@Composable
private fun AboutScreen(
    use2Panes: Boolean,
    startSpacerValue: Dp,
    endSpacerValue: Dp,

    currentAppVersionName: String,
    isDebugMode: Boolean,

    navigateToOpenSourceLicense: () -> Unit,
    navigateUp: () -> Unit,

    snackBarHostState: SnackbarHostState,

    modifier: Modifier = Modifier
){
    val uriHandler = LocalUriHandler.current

    val scaffoldModifier = if (use2Panes) modifier
                            else modifier.navigationBarsPadding()

    MyScaffold(
        modifier = scaffoldModifier,
        contentWindowInsets = WindowInsets(bottom = 0),

        topBar = {
            MyTopAppBar(
                startPadding = startSpacerValue,
                title = stringResource(id = R.string.about),
                navigationIcon = TopAppBarIcon.back,
                onClickNavigationIcon = { navigateUp() }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.width(500.dp),
                snackbar = {
                    Snackbar(
                        snackbarData = it,
                        shape = MaterialTheme.shapes.medium
                    )
                }
            )
        }
    ){ paddingValues ->

        val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(startSpacerValue, 16.dp, endSpacerValue, 200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item{
                //app icon image
                MySpacerColumn(height = 16.dp)
                AppIconCard(
                    modifier = itemModifier
                )
                MySpacerColumn(height = 24.dp)
            }

            item{
                //app version
                VersionCard(
                    currentAppVersionName = currentAppVersionName,
                    isLatestAppVersion = true,
                    onClickUpdate = {
//                        uriHandler.openUri(BANNERIT_PLAY_STORE_URL)
                    },
                    modifier = itemModifier
                )
            }

            item{
                //developer info
                DeveloperCard(
                    modifier = itemModifier
                )
            }

//            item{
//                //contact
//                ContactCard(
//                    modifier = itemModifier
//                )
//            }

            //feedback and bug report
//            item {
//                ListGroupCard(
//                    modifier = itemModifier
//                ) {
//                    //send feedback - open web browser to google form
//                    ItemWithText(
//                        text = stringResource(id = R.string.send_feedback),
//                        onItemClick = { uriHandler.openUri(FEEDBACK_URL) },
//                        isOpenInNew = true
//                    )
//
//                    ItemDivider()
//
//                    //bug report - open web browser to google form
//                    ItemWithText(
//                        text = stringResource(id = R.string.bug_report),
//                        onItemClick = { uriHandler.openUri(BUG_REPORT_URL) },
//                        isOpenInNew = true
//                    )
//                }
//            }

            item{
                ListGroupCard(
                    modifier = itemModifier
                ) {
                    //privacy policy
                    ItemWithText(
                        text = stringResource(id = R.string.privacy_policy),
                        isOpenInNew = true,
                        onItemClick = { onClickPrivacyPolicy(uriHandler)}
                    )

                    ItemDivider()

                    //open source license
                    ItemWithText(
                        text = stringResource(id = R.string.open_source_license),
                        onItemClick = {
                            navigateToOpenSourceLicense()
                        }
                    )
                }
            }

//            item {
//                MySpacerColumn(height = 64.dp)
//
//                //copy link / qr code
//                ShareAppCard(
//                    modifier = itemModifier.fillMaxWidth(),
//                    onClickShareApp = onClickShareApp,
//                )
//            }

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