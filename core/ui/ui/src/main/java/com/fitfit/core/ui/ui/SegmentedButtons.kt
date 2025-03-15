package com.fitfit.core.ui.ui

import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fitfit.core.model.enums.TimeFormat
import com.fitfit.core.ui.designsystem.components.utils.MySegmentedButtons
import com.fitfit.core.ui.designsystem.components.utils.SegmentedButtonItem
import com.fitfit.core.utils.itemMaxWidth

@Composable
fun TimeFormatSegmentedButtons(
    is24h: Boolean,
    setTimeFormat: (timeFormat: TimeFormat) -> Unit
){

    MySegmentedButtons(
        modifier = Modifier.widthIn(max = itemMaxWidth),
        initSelectedItemIndex = if (!is24h) 0 else 1,
        itemList = listOf(
            SegmentedButtonItem(
                text = stringResource(id = R.string._12h),
                onClick = { setTimeFormat(TimeFormat.T12H) }
            ),
            SegmentedButtonItem(
                text = stringResource(id = R.string._24h),
                onClick = { setTimeFormat(TimeFormat.T24H) }
            )
        )
    )
}