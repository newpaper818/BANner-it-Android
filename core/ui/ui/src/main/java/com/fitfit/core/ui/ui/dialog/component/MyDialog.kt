package com.fitfit.core.ui.ui.dialog.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MyPlainTooltipBox
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.designsystem.theme.BannerItTheme

internal val DIALOG_DEFAULT_WIDTH = 360.dp
internal val DIALOG_DEFAULT_MAX_HEIGHT = 570.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDialog(
    onDismissRequest: () -> Unit,

    modifier: Modifier = Modifier,
    width: Dp? = DIALOG_DEFAULT_WIDTH,
    setMaxHeight: Boolean = false,
    maxHeight: Dp = DIALOG_DEFAULT_MAX_HEIGHT,
    titleText: String? = null,
    bodyText: String? = null,
    subBodyText: String? = null,
    bodyContent: @Composable() (() -> Unit)? = null,
    buttonContent: @Composable() (() -> Unit)? = null,
    setBodySpacer: Boolean = true,
    closeIcon: Boolean = false
){
    var columnModifier =
        if (width != null) modifier
            .width(width)
            .padding(12.dp, 16.dp, 12.dp, 12.dp)
        else               modifier.padding(12.dp, 16.dp, 12.dp, 12.dp)

    columnModifier =
        if (setMaxHeight) columnModifier.heightIn(max = maxHeight)
        else              columnModifier



    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = columnModifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                //title text / align center
                if (titleText != null) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = titleText,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        if (closeIcon) {
                            Box(
                                modifier.fillMaxWidth(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                MyPlainTooltipBox(tooltipText = stringResource(id = TopAppBarIcon.close.descriptionTextId!!)) {
                                    IconButton(onClick = onDismissRequest) {
                                        DisplayIcon(icon = TopAppBarIcon.close)
                                    }
                                }
                            }
                        }
                    }

                    MySpacerColumn(height = 16.dp)
                }



                //body text / align left(fillMaxWidth)
                if (bodyText != null) {
                    Text(
                        text = bodyText,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                    )
                    MySpacerColumn(height = 16.dp)
                }



                //sub body text / align center
                if (subBodyText != null){
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = subBodyText,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.outline
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                    MySpacerColumn(height = 16.dp)
                }



                //body content
                if (bodyContent != null) {
                    Column(
                        modifier = if (setMaxHeight) Modifier.weight(1f)
                                    else Modifier
                    ) {
                        bodyContent()
                    }

                    if (setBodySpacer) {
                        MySpacerColumn(height = 12.dp)
                    }
                }



                //buttons
                if (buttonContent != null) {
                    buttonContent()
                }
            }
        }
    }
}


























@Composable
@PreviewLightDark
private fun MyDialogPreview(){
    BannerItTheme {
        MyScaffold {
            MyDialog(
                titleText = "Dailog title",
                bodyText = "body text some text....... some textttttt",
                bodyContent = {
                    MyCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {

                    }

                },
                buttonContent = {
                    Row {
                        //cancel button
                        CancelDialogButton(onClick = { })

                        MySpacerRow(width = 16.dp)

                        //delete button
                        OkDialogButton(onClick = { })
                    }
                },
                onDismissRequest = {}
            )
        }
    }
}