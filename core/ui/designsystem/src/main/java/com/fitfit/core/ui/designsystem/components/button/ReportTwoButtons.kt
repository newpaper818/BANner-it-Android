package com.fitfit.core.ui.designsystem.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.R
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.IconButtonIcon
import com.fitfit.core.ui.designsystem.icon.MyIcon
import com.fitfit.core.utils.itemMaxWidthSmall


@Composable
fun GetPhotosButtons(
    enabled: Boolean,
    onClickTakePhotos: () -> Unit,
    onClickSelectPhotos: () -> Unit
){

    AutoLayoutButtons(
        modifier = Modifier.widthIn(max = itemMaxWidthSmall),
        button1Content = {
            IconButton(
                onClick = onClickTakePhotos,
                enabled = enabled,
                icon = IconButtonIcon.camera,
                text = stringResource(R.string.take_photos),
                modifier = it
            )
        },
        button2Content = {
            IconButton(
                onClick = onClickSelectPhotos,
                enabled = enabled,
                icon = IconButtonIcon.gallery,
                text = stringResource(R.string.select_photos),
                modifier = it
            )
        }
    )
}

@Composable
fun LocationButtons(
    onClickSelectLocation: () -> Unit,
    onClickCurrentLocation: () -> Unit
){
    AutoLayoutButtons(
        modifier = Modifier.widthIn(max = itemMaxWidthSmall),
        button1Content = {
            IconButton(
                onClick = onClickSelectLocation,
                enabled = false,
                icon = IconButtonIcon.map,
                text = stringResource(R.string.select_location),
                modifier = it
            )
        },
        button2Content = {
            IconButton(
                onClick = onClickCurrentLocation,
                enabled = false,
                icon = IconButtonIcon.currentLocation,
                text = stringResource(R.string.current_location),
                modifier = it
            )
        }
    )
}

@Composable
private fun AutoLayoutButtons(
    button1Content: @Composable ((modifier: Modifier) -> Unit)? = null,
    button2Content: @Composable ((modifier: Modifier) -> Unit)? = null,
    modifier: Modifier = Modifier
){
    val configuration = LocalConfiguration.current
    val isNarrowWidth = configuration.screenWidthDp.dp < 320.dp

    val buttonContents = listOfNotNull(button1Content, button2Content)

    if (isNarrowWidth){
        Column(
            modifier = modifier
        ) {
            buttonContents.forEach {
                it(Modifier.fillMaxWidth())

                if (it != buttonContents.last())
                    MySpacerColumn(height = 16.dp)
            }
        }
    }
    else {
        Row(
            modifier = modifier
        ) {
            buttonContents.forEach {
                it(Modifier.weight(1f))

                if (it != buttonContents.last())
                    MySpacerRow(width = 16.dp)
            }
        }
    }
}

@Composable
private fun IconButton(
    onClick: () -> Unit,
    enabled: Boolean,
    icon: MyIcon,
    text: String,

    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
){
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceBright,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier.heightIn(min = 60.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            //icon
            DisplayIcon(icon)

            MySpacerRow(8.dp)

            //text
            Text(
                text = text,
                style = textStyle,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}