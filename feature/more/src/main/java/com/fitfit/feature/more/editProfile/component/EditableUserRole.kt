package com.fitfit.feature.more.editProfile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.enums.UserRole
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.ui.UserRoleSegmentedButtons
import com.fitfit.feature.more.R

@Composable
internal fun EditableUserRole(
    userRole: UserRole,
    onUserRoleChanged: (UserRole) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Row {
            MySpacerRow(width = 16.dp)
            Text(
                text = stringResource(id = R.string.admin_settings),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        MySpacerColumn(height = 6.dp)

        UserRoleSegmentedButtons(
            isAdmin = userRole == UserRole.ADMIN,
            setUserRole = onUserRoleChanged
        )
    }
}