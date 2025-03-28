package com.fitfit.feature.logs.mainLogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.report.ReportLog
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.MyIcons
import com.fitfit.core.ui.ui.card.report.ReportLogCard
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.logs.R

@Composable
fun MainLogsRoute(
    use2Panes: Boolean,
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,

    appUserReportLogs: List<ReportLog>,
    onClickReportLog: (reportLogIndex: Int) -> Unit,

    modifier: Modifier = Modifier
) {


    MainLogsScreen(
        spacerValue = spacerValue,
        dateTimeFormat = dateTimeFormat,
        appUserReportLogs = appUserReportLogs,
        onClickReportLog = onClickReportLog
    )
}

@Composable
private fun MainLogsScreen(
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,
    appUserReportLogs: List<ReportLog>,
    onClickReportLog: (reportLogIndex: Int) -> Unit,
){
    val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)

    val isReportLogsEmpty = appUserReportLogs.isEmpty()

    MyScaffold(
        modifier = Modifier,

        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.report_logs)
            )
        }
    ){ paddingValues ->

        AnimatedVisibility(
            visible = !isReportLogsEmpty,
            enter = fadeIn(tween(400)),
            exit = fadeOut(tween(400))
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(spacerValue, 16.dp, spacerValue, 200.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ){
                itemsIndexed(appUserReportLogs) { index, appUserReportLog ->
                    ReportLogCard(
                        reportLog = appUserReportLog,
                        dateTimeFormat = dateTimeFormat,
                        onClick = {
                            onClickReportLog(index)
                        },
                        modifier = itemModifier
                    )
                }
            }
        }


        AnimatedVisibility(
            visible = isReportLogsEmpty,
            enter = fadeIn(tween(400)),
            exit = fadeOut(tween(400))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                NoReportLog()
            }
        }
    }
}

@Composable
private fun NoReportLog(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(16.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        //icon
        DisplayIcon(
            icon = MyIcons.noReportLog,
            contentDescriptionIsNull = true
        )

        MySpacerColumn(height = 16.dp)

        //text
        Text(
            text = stringResource(id = R.string.no_report_log),
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            textAlign = TextAlign.Center
        )
    }
}