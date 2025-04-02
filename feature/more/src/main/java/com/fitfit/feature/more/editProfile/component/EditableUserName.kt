package com.fitfit.feature.more.editProfile.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.MyIcons
import com.fitfit.core.ui.designsystem.theme.CustomColor
import com.fitfit.core.ui.ui.MyTextField
import com.fitfit.feature.more.R

@Composable
internal fun EditableUserName(
    userName: String?,
    isInvalidText: Boolean,
    onUserNameChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    val borderColor = if (isInvalidText) CustomColor.outlineError
    else Color.Transparent

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
    ) {
        Row {
            MySpacerRow(width = 16.dp)
            Text(
                text = stringResource(id = R.string.name),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        MySpacerColumn(height = 6.dp)

        MyCard(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, borderColor, RoundedCornerShape(16.dp)),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                MyTextField(
                    inputText = userName,
                    inputTextStyle = MaterialTheme.typography.bodyLarge,
                    placeholderText = stringResource(id = R.string.no_name),
                    placeholderTextStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    onValueChange = {
                        onUserNameChanged(it)
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    }),
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp, 16.dp, 0.dp, 16.dp)
                )

                if (userName == "" || userName == null) {
                    MySpacerRow(width = 16.dp)
                } else {
                    IconButton(onClick = { onUserNameChanged("") }) {
                        DisplayIcon(icon = MyIcons.clearInputText)
                    }
                }
            }
        }
    }
}