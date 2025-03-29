package com.fitfit.core.ui.ui.card.report

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.report.ReportLog
import com.fitfit.core.model.report.sampleReportLog
import com.fitfit.core.ui.designsystem.components.ImageFromUrl
import com.fitfit.core.ui.designsystem.components.NoImage
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.ui.R
import com.fitfit.core.utils.getDateTimeText

private const val CARD_HEIGHT_DP = 120

@Composable
fun ReportLogCard(
    reportLog: ReportLog = sampleReportLog,
    dateTimeFormat: DateTimeFormat,
    onClick: () -> Unit,

    modifier: Modifier = Modifier
){
    MyCard(
        modifier = modifier
            .height(CARD_HEIGHT_DP.dp),
        shape = MaterialTheme.shapes.extraLarge,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //image
            Box(
                modifier = Modifier
                    .size((CARD_HEIGHT_DP - 24).dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                if (reportLog.images.isNotEmpty()){
                    ImageFromUrl(
                        imageUrl = reportLog.images[0],
                        contentDescription = stringResource(R.string.photo),
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else {
                    NoImage(
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            MySpacerRow(12.dp)

            Column(
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Row {
                    //report id
                    Text(
                        text = reportLog.reportId.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    //status
                    Text(
                        text = stringResource(reportLog.status.textId) ,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }

                MySpacerColumn(8.dp)

                //date time
                Text(
                    text = getDateTimeText(reportLog.reportTime, dateTimeFormat),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}