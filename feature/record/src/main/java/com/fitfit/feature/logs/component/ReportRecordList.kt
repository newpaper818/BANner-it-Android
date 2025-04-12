package com.fitfit.feature.logs.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.report.ReportRecord
import com.fitfit.core.ui.ui.card.report.ReportRecordCard

@Composable
internal fun ReportRecordList(
    spacerValue: Dp,
    paddingValues: PaddingValues,
    dateTimeFormat: DateTimeFormat,

    reportRecords: List<ReportRecord>,
    onClickReportRecord: (reportRecord: ReportRecord) -> Unit,
    itemModifier: Modifier
){
    val isReportRecordsEmpty = reportRecords.isEmpty()


    AnimatedVisibility(
        visible = !isReportRecordsEmpty,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(spacerValue, 16.dp, spacerValue, 200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            items(reportRecords) { reportRecord ->
                ReportRecordCard(
                    reportRecord = reportRecord,
                    dateTimeFormat = dateTimeFormat,
                    onClick = {
                        onClickReportRecord(reportRecord)
                    },
                    modifier = itemModifier
                )
            }
        }
    }


    AnimatedVisibility(
        visible = isReportRecordsEmpty,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NoReportRecord()
        }
    }
}