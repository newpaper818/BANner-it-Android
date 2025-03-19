package com.fitfit.core.ui.designsystem.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.R
import com.fitfit.core.ui.designsystem.components.utils.ClickableBox
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.IconButtonIcon
import com.fitfit.core.ui.designsystem.icon.MyIcon

@Composable
fun GetPhotosButtons(
    onClickTakePhotos: () -> Unit,
    onClickSelectPhotos: () -> Unit
){
    Row(
        modifier = Modifier.widthIn(max = 400.dp)
    ) {
        GetPhotosButton(
            onClick = onClickTakePhotos,
            icon = IconButtonIcon.camera,
            text = stringResource(R.string.take_photos),
            modifier = Modifier.weight(1f)
        )

        MySpacerRow(16.dp)

        GetPhotosButton(
            onClick = onClickSelectPhotos,
            icon = IconButtonIcon.gallery,
            text = stringResource(R.string.select_photos),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun GetPhotosButton(
    onClick: () -> Unit,
    icon: MyIcon,
    text: String,

    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
){
    ClickableBox(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.surfaceBright,
        modifier = modifier
            .height(60.dp)
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