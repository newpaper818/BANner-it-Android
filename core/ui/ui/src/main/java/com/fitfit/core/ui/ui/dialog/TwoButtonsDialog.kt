package com.fitfit.core.ui.ui.dialog

import androidx.compose.runtime.Composable
import com.fitfit.core.ui.ui.dialog.component.CancelDialogButton
import com.fitfit.core.ui.ui.dialog.component.DangerDialogButton
import com.fitfit.core.ui.ui.dialog.component.DialogButtonLayout
import com.fitfit.core.ui.ui.dialog.component.DialogButtons
import com.fitfit.core.ui.ui.dialog.component.MyDialog
import com.fitfit.core.ui.ui.dialog.component.PositiveDialogButton

@Composable
fun TwoButtonsDialog(
    positiveButtonText: String,
    onDismissRequest: () -> Unit,
    onClickPositive: () -> Unit,

    titleText: String? = null,
    bodyText: String? = null,
    subBodyText: String? = null,
    dialogButtonLayout: DialogButtonLayout = DialogButtonLayout.AUTO,
    positiveIsDangerButton: Boolean = true

//    bodyContent: @Composable() (() -> Unit)? = null,
){
    MyDialog(
        onDismissRequest = onDismissRequest,
        titleText = titleText,
        bodyText = bodyText,
        subBodyText = subBodyText,
        bodyContent = null,
        buttonContent = {
            DialogButtons(
                negativeButtonContent = {
                    CancelDialogButton(
                        onClick = onDismissRequest,
                        modifier = it
                    )
                },
                positiveButtonContent = {
                    if (positiveIsDangerButton) {
                        DangerDialogButton(
                            text = positiveButtonText,
                            onClick = onClickPositive,
                            modifier = it
                        )
                    }
                    else {
                        PositiveDialogButton(
                            text = positiveButtonText,
                            onClick = onClickPositive,
                            modifier = it
                        )
                    }
                },
                dialogButtonLayout = dialogButtonLayout
            )
        }
    )
}