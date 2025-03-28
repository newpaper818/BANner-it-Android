package com.fitfit.feature.logs.mainLogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.report.ReportLog
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.ui.card.ReportLogCard
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.logs.R

@Composable
fun MainLogsRoute(
    use2Panes: Boolean,
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,

    appUserReportLogs: List<ReportLog>,

    modifier: Modifier = Modifier
) {


    MainLogsScreen(
        spacerValue = spacerValue,
        dateTimeFormat = dateTimeFormat,
        appUserReportLogs = appUserReportLogs,
    )
}

@Composable
private fun MainLogsScreen(
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,
    appUserReportLogs: List<ReportLog>
){
    val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)

    MyScaffold(
        modifier = Modifier,

        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.logs)
            )
        }
    ){ paddingValues ->

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(spacerValue, 16.dp, spacerValue, 200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            items(appUserReportLogs) { appUserReportLog ->
                ReportLogCard(
                    reportLog = appUserReportLog,
                    dateTimeFormat = dateTimeFormat,
                    modifier = itemModifier
                )
            }
        }
    }
}