package com.fitfit.core.ui.ui

import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fitfit.core.model.enums.TimeFormat
import com.fitfit.core.model.enums.UserRole
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
                text = stringResource(id = TimeFormat.T12H.textId),
                onClick = { setTimeFormat(TimeFormat.T12H) }
            ),
            SegmentedButtonItem(
                text = stringResource(id = TimeFormat.T24H.textId),
                onClick = { setTimeFormat(TimeFormat.T24H) }
            )
        )
    )
}

@Composable
fun UserRoleSegmentedButtons(
    isAdmin: Boolean,
    setUserRole: (userRole: UserRole) -> Unit
){

    MySegmentedButtons(
        modifier = Modifier.widthIn(max = itemMaxWidth),
        initSelectedItemIndex = if (!isAdmin) 0 else 1,
        itemList = listOf(
            SegmentedButtonItem(
                text = stringResource(id = UserRole.USER.textId),
                onClick = { setUserRole(UserRole.USER) }
            ),
            SegmentedButtonItem(
                text = stringResource(id = UserRole.ADMIN.textId),
                onClick = { setUserRole(UserRole.ADMIN) }
            )
        )
    )
}