package com.fitfit.core.ui.designsystem.components.topAppBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.fitfit.core.ui.designsystem.components.utils.MyPlainTooltipBox
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import com.fitfit.core.ui.designsystem.theme.CustomColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageTopAppBar(
    visible: Boolean,
    title: String,

    navigationIconOnClick: () -> Unit = {},
    actionIcon1Onclick: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(420)),
        exit = fadeOut(tween(420))
    ) {


        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CustomColor.imageForeground,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(color = CustomColor.white, fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                MyPlainTooltipBox(tooltipText = stringResource(id = TopAppBarIcon.closeImageScreen.descriptionTextId!!)) {
                    IconButton(onClick = navigationIconOnClick) {
                        DisplayIcon(icon = TopAppBarIcon.closeImageScreen)
                    }
                }

            },
//            actions = {
//                MyPlainTooltipBox(tooltipText = stringResource(id = TopAppBarIcon.downloadImage.descriptionTextId!!)) {
//                    IconButton(onClick = actionIcon1Onclick) {
//                        DisplayIcon(icon = TopAppBarIcon.downloadImage)
//                    }
//                }
//            }
        )
    }
}







@Composable
@PreviewLightDark
private fun ImageTopAppBarPreview(){
    BannerItTheme {
        ImageTopAppBar(
            visible = true,
            title = "1 / 5"
        )
    }
}
