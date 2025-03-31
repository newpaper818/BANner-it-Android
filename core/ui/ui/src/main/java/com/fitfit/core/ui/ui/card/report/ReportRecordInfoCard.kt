package com.fitfit.core.ui.ui.card.report

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.report.BannerInfo
import com.fitfit.core.model.report.ReportRecord
import com.fitfit.core.model.report.ReportStatus
import com.fitfit.core.ui.designsystem.components.button.EditBannerStatusButton
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.ui.R
import com.fitfit.core.ui.ui.item.ItemDivider
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
    showEditBannerStatusButton: Boolean,
    bannerInfo: BannerInfo,
    onClickEditBannerStatus: () -> Unit,
    modifier: Modifier = Modifier
){
    MyCard(
        enabled = false,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.padding(bottom = 16.dp)
        ) {
            BannerStatusRow(
                showEditBannerStatusButton = showEditBannerStatusButton,
                bannerStatus = bannerInfo.status,
                onClick = onClickEditBannerStatus,
                modifier = Modifier.padding(16.dp, 0.dp, 4.dp, 0.dp)
            )

            MySpacerColumn(2.dp)

            TextRow(
                text1 = stringResource(R.string.banner_id),
                text2 = bannerInfo.bannerId.toString(),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            MySpacerColumn(10.dp)
            ItemDivider()
            MySpacerColumn(10.dp)

            TextRow(
                text1 = stringResource(R.string.company_name),
                text2 = bannerInfo.companyName,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            MySpacerColumn(16.dp)

            TextRow(
                text1 = stringResource(R.string.phone_number),
                text2 = bannerInfo.phoneNumber,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun BannerStatusRow(
    showEditBannerStatusButton: Boolean,
    bannerStatus: ReportStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.padding(vertical = 14.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(bannerStatus.color)
            ) {
                Text(
                    text = stringResource(bannerStatus.textId),
                    style = MaterialTheme.typography.bodyMedium,
                    color = bannerStatus.textColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(10.dp, 2.dp)
                )
            }
        }


        Spacer(modifier = Modifier.weight(1f))


        AnimatedVisibility(
            visible = showEditBannerStatusButton,
            enter = fadeIn(tween(500)),
            exit = fadeOut(tween(500))
        ) {
            EditBannerStatusButton(
                onClick = onClick
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
        modifier = modifier,
//        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text1,
            style = MaterialTheme.typography.bodyMedium.copy(
                MaterialTheme.colorScheme.onSurfaceVariant
            )
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