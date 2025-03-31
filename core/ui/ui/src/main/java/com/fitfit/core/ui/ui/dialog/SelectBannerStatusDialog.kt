package com.fitfit.core.ui.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.report.ReportStatus
import com.fitfit.core.ui.designsystem.components.utils.ClickableBox
import com.fitfit.core.ui.ui.R
import com.fitfit.core.ui.ui.dialog.component.CancelDialogButton
import com.fitfit.core.ui.ui.dialog.component.DialogButtonLayout
import com.fitfit.core.ui.ui.dialog.component.DialogButtons
import com.fitfit.core.ui.ui.dialog.component.MyDialog
import com.fitfit.core.ui.ui.dialog.component.OkDialogButton
import com.fitfit.core.ui.ui.dialog.component.SaveDialogButton

@Composable
fun SelectBannerStatusDialog(
    initialBannerStatus: ReportStatus,

    onOkClick: (bannerStatus: ReportStatus) -> Unit,
    onDismissRequest: () -> Unit
){
    val bannerStatusList = enumValues<ReportStatus>()

    var currentBannerStatus by rememberSaveable { mutableStateOf(initialBannerStatus) }

    MyDialog(
        onDismissRequest = onDismissRequest,

        titleText = stringResource(R.string.set_banner_status),
        bodyContent = {
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier
                    .heightIn(min = 0.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surfaceBright)
            ) {
                items(bannerStatusList) { bannerStatus ->
                    ExerciseCard(
                        bannerStatus = bannerStatus,
                        isSelected = bannerStatus == currentBannerStatus,
                        onClick = { it ->
                            currentBannerStatus = it
                        }
                    )
                }
            }
        },
        buttonContent = {
            DialogButtons(
                dialogButtonLayout = DialogButtonLayout.HORIZONTAL,
                negativeButtonContent = {
                    //cancel button
                    CancelDialogButton(
                        onClick = onDismissRequest,
                        modifier = it
                    )
                },
                positiveButtonContent = {
                    //ok button
                    SaveDialogButton(
                        onClick = { onOkClick(currentBannerStatus) },
                        modifier = it
                    )
                }
            )
        }
    )
}


@Composable
private fun ExerciseCard(
    bannerStatus: ReportStatus,
    isSelected: Boolean,
    onClick: (exercise: ReportStatus) -> Unit
){
    val borderStroke = if (isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    else null

    val cardColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                    else Color.Transparent

    val cardTextStyle = if (isSelected) MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        else MaterialTheme.typography.bodyMedium

    ClickableBox(
        containerColor = cardColor,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth(),
        onClick = { onClick(bannerStatus) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(bannerStatus.textId),
                style = cardTextStyle
            )
        }
    }
}