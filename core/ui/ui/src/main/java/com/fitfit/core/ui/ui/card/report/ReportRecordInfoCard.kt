package com.fitfit.core.ui.ui.card.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.report.BannerInfo
import com.fitfit.core.model.report.ReportRecord
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.ui.R
import com.fitfit.core.utils.getDateTimeText

@Composable
fun ReportRecordInfoCard(
    reportRecord: ReportRecord,
    dateTimeFormat: DateTimeFormat,
    modifier: Modifier = Modifier
){
    MyCard(
        enabled = false,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.padding(16.dp)
        ) {
            TextRow(
                text1 = stringResource(R.string.report_id),
                text2 = reportRecord.reportId.toString(),
            )

            MySpacerColumn(16.dp)

            TextRow(
                text1 = stringResource(R.string.status),
                text2 = stringResource(reportRecord.status.textId),
            )

            MySpacerColumn(16.dp)

            TextRow(
                text1 = stringResource(R.string.report_time),
                text2 = getDateTimeText(reportRecord.reportTime, dateTimeFormat),
            )
        }
    }
}

@Composable
fun ReportRecordBannerInfoCard(
    bannerInfo: BannerInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    MyCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.padding(16.dp)
        ) {
            TextRow(
                text1 = stringResource(R.string.banner_id),
                text2 = bannerInfo.bannerId.toString(),
            )

            MySpacerColumn(16.dp)

            TextRow(
                text1 = stringResource(R.string.status),
                text2 = stringResource(bannerInfo.status.textId),
            )

            MySpacerColumn(16.dp)

            TextRow(
                text1 = stringResource(R.string.company_name),
                text2 = bannerInfo.companyName,
            )

            MySpacerColumn(16.dp)

            TextRow(
                text1 = stringResource(R.string.phone_number),
                text2 = bannerInfo.phoneNumber,
            )
        }
    }
}

@Composable
private fun TextRow(
    text1: String,
    text2: String,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
//            .height(40.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text1,
            style = MaterialTheme.typography.bodyMedium.copy(
                MaterialTheme.colorScheme.onSurfaceVariant
            ),
        )

        MySpacerRow(8.dp)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = text2,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.End
        )
    }
}