package com.fitfit.feature.report.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.UserData
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.button.BottomReportCancelButtons
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.ui.item.TitleText
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.report.R

@Composable
fun ReportRoute(
    appUserData: UserData?,

    use2Panes: Boolean,
    spacerValue: Dp,

    navigateUp: () -> Unit,
    navigateToSendReport: () -> Unit,

    modifier: Modifier = Modifier,
) {
    ReportScreen(
        appUserData = appUserData,
        spacerValue = spacerValue,
        navigateUp = navigateUp,
        navigateToSendReport = navigateToSendReport
    )
}

@Composable
private fun ReportScreen(
    appUserData: UserData?,
    spacerValue: Dp,

    navigateUp: () -> Unit,
    navigateToSendReport: () -> Unit,
){
    val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)

    MyScaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .displayCutoutPadding()
            .imePadding(),

        //top app bar
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.report),
                startPadding = spacerValue,
                navigationIcon = TopAppBarIcon.back,
                onClickNavigationIcon = navigateUp
            )
        }
    ){ paddingValues ->

        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            //content
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(spacerValue, 16.dp, spacerValue, 200.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .navigationBarsPadding()

            ){
                //photos
                item {
                    TitleText(
                        text = "Photos (0/3)",
                        modifier = itemModifier
                            .padding(start = 16.dp, bottom = 6.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(400.dp)
                            .background(Color.Blue)
                    )
                }

                //content
                item {
                    TitleText(
                        text = "Content",
                        modifier = itemModifier
                            .padding(start = 16.dp, bottom = 6.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(400.dp)
                            .background(Color.Blue)
                    )
                }

                //location
                item {
                    TitleText(
                        text = "Location",
                        modifier = itemModifier
                            .padding(start = 16.dp, bottom = 6.dp)
                    )

                    Box(
                        modifier = Modifier
                            .size(400.dp)
                            .background(Color.Blue)
                    )
                }
            }

            //bottom report cancel buttons
            BottomReportCancelButtons(
                onClickCancel = {
                    //show dialog
                },
                onClickReport = {
                    navigateToSendReport()
                },
                modifier = Modifier
            )
        }
    }
}