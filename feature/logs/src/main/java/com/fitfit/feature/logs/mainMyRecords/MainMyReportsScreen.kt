package com.fitfit.feature.logs.mainMyRecords

import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.report.ReportRecord
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.logs.R
import com.fitfit.feature.logs.component.ReportRecordList

@Composable
fun MainMyReportsRoute(
    use2Panes: Boolean,
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,

    appUserReportRecords: List<ReportRecord>,
    onClickReportRecord: (reportRecord: ReportRecord) -> Unit,

    modifier: Modifier = Modifier
) {
    MainMyReportsScreen(
        spacerValue = spacerValue,
        dateTimeFormat = dateTimeFormat,
        appUserReportRecords = appUserReportRecords,
        onClickReportRecord = onClickReportRecord
    )
}

@Composable
private fun MainMyReportsScreen(
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,
    appUserReportRecords: List<ReportRecord>,
    onClickReportRecord: (reportRecord: ReportRecord) -> Unit,
){
    val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)

    MyScaffold(
        modifier = Modifier,

        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.my_report_records)
            )
        }
    ){ paddingValues ->

        ReportRecordList(
            spacerValue = spacerValue,
            paddingValues = paddingValues,
            dateTimeFormat = dateTimeFormat,
            reportRecords = appUserReportRecords,
            onClickReportRecord = onClickReportRecord,
            itemModifier = itemModifier
        )
    }
}