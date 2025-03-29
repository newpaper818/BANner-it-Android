package com.fitfit.core.ui.ui.card.report

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.theme.CustomColor
import com.fitfit.core.ui.ui.MyTextField
import com.fitfit.core.ui.ui.R

const val MAX_CONTENT_LENGTH = 100

@Composable
fun ContentCard(
    isEditMode: Boolean,
    contentText: String?,
    onContentTextChange: (String) -> Unit,
    isLongText: (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    var isTextSizeLimit by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isEditMode){
        isTextSizeLimit = (contentText ?: "").length > MAX_CONTENT_LENGTH
    }

    val borderColor = if (isTextSizeLimit) CustomColor.outlineError
                        else Color.Transparent

    val viewContentText = contentText ?: stringResource(id = R.string.no_content)


    //content card
    MyCard(
        enabled = false,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 360.dp)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
    ) {
        Column(
            Modifier.padding(16.dp, 14.dp)
        ) {
            //view mode
            if (!isEditMode){
                SelectionContainer {
                    Text(
                        text = viewContentText,
                        style = if (contentText != null) MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface)
                        else MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }

            //edit mode
            else {
                val focusManager = LocalFocusManager.current

                MyTextField(
                    inputText = if (contentText == "") null else contentText,
                    inputTextStyle = MaterialTheme.typography.bodyLarge,

                    placeholderText = stringResource(id = R.string.add_content),
                    placeholderTextStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),

                    onValueChange = {
                        if (it.length > MAX_CONTENT_LENGTH && !isTextSizeLimit){
                            isTextSizeLimit = true
                            isLongText(true)
                        }
                        else if (it.length <= MAX_CONTENT_LENGTH && isTextSizeLimit) {
                            isTextSizeLimit = false
                            isLongText(false)
                        }
                        onContentTextChange(it)
                    },
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = true,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                    })
                )
            }
        }
    }
}