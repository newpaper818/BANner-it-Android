package com.fitfit.feature.more.editProfile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.UserData
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.feature.more.R

@Composable
internal fun AccountInfo(
    userData: UserData,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.semantics(mergeDescendants = true) { }
    ) {
        Text(
            text = userData.email ?: "no email",
            style = textStyle
        )

        MySpacerColumn(height = 6.dp)

        var connectedWithInfoText = stringResource(id = R.string.connected_with) + " "

        userData.providerIds.forEachIndexed { index, providerId ->
            if (index != 0) {
                connectedWithInfoText += ", "
            }
            connectedWithInfoText += providerId.providerName
        }

        Text(
            text = connectedWithInfoText,
            style = textStyle
        )
    }
}