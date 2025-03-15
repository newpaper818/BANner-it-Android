package com.fitfit.core.ui.designsystem.components.topAppBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.R
import com.fitfit.core.ui.designsystem.components.utils.MyPlainTooltipBox
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.IconTextButtonIcon
import com.fitfit.core.ui.designsystem.icon.MyIcon
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.designsystem.theme.BannerItTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    internetEnabled: Boolean = true,
    subtitle: String? = null,

    navigationIcon: MyIcon? = null,
    onClickNavigationIcon: () -> Unit = {},

    actionIcon1: MyIcon? = null,
    actionIcon1Onclick: () -> Unit = {},
    actionIcon1Visible: Boolean = true,

    actionIcon2: MyIcon? = null,
    actionIcon2Onclick: () -> Unit = {},
    actionIcon2Visible: Boolean = true,

    dropdownMenuContent: @Composable () -> Unit = {},

    useHorizontalLayoutTitles: Boolean = false,
    startPadding: Dp = 16.dp
) {

    //app bar
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        title = {
            Row {
                if (navigationIcon == null)
                    MySpacerRow(width = startPadding)

                if (!useHorizontalLayoutTitles) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (subtitle != null) {
                            MySpacerColumn(height = 2.dp)
                            TopAppBarSubtitle(subtitle = subtitle)
                        }
                        else {
                            AnimatedVisibility(
                                visible = !internetEnabled,
                                enter = expandVertically(tween(500)) + fadeIn(tween(500, 200)),
                                exit = shrinkVertically(tween(500, 200)) + fadeOut(tween(500))
                            ) {
                                MySpacerColumn(height = 2.dp)
                                InternetUnavailable()
                            }
                        }
                    }
                }
                else {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (subtitle != null) {
                            MySpacerRow(width = 24.dp)
                            TopAppBarSubtitle(subtitle = subtitle)
                        }
                        else {
                            AnimatedVisibility(
                                visible = !internetEnabled,
                                enter = expandVertically(tween(500)) + fadeIn(tween(500, 200)),
                                exit = shrinkVertically(tween(500, 200)) + fadeOut(tween(500))
                            ) {
                                Row {
                                    MySpacerRow(width = 24.dp)
                                    InternetUnavailable()
                                }
                            }
                        }
                    }
                }
            }

        },
        navigationIcon = {
            if (navigationIcon != null) {
                MyPlainTooltipBox(
                    tooltipText = stringResource(id = navigationIcon.descriptionTextId!!)
                ) {
                    IconButton(onClick = onClickNavigationIcon) {
                        DisplayIcon(icon = navigationIcon)
                    }
                }
            }
        },
        actions = {
            //action1
            AnimatedVisibility(
                visible = actionIcon1Visible,
                enter = fadeIn(tween(350)),
                exit = fadeOut(tween(350))
            ) {
                if (actionIcon1 != null) {
                    MyPlainTooltipBox(
                        tooltipText = stringResource(id = actionIcon1.descriptionTextId!!)
                    ) {
                        IconButton(onClick = actionIcon1Onclick) {
                            DisplayIcon(icon = actionIcon1)
                        }
                    }
                }
            }

            //action2
            AnimatedVisibility(
                visible = actionIcon2Visible,
                enter = fadeIn(tween(350)),
                exit = fadeOut(tween(350))
            ) {
                if (actionIcon2 != null) {
                    MyPlainTooltipBox(
                        tooltipText = stringResource(id = actionIcon2.descriptionTextId!!)
                    ) {
                        IconButton(onClick = actionIcon2Onclick) {
                            DisplayIcon(icon = actionIcon2)
                        }
                    }
                }
            }

            //dropdown menu
            dropdownMenuContent()
        }
    )
}

@Composable
private fun TopAppBarSubtitle(
    subtitle: String
){
    Text(
        text = subtitle,
        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun InternetUnavailable(

){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(20.dp)
    ) {

        Text(
            text = stringResource(id = R.string.example),
            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
    }
}










@Composable
@PreviewLightDark
private fun FitfitTopAppBarPreview(){
    BannerItTheme {
        MyTopAppBar(
            title = "Top app bar title"
        )
    }
}

@Composable
@PreviewLightDark
private fun FitfitTopAppBarPreview2(){
    BannerItTheme {
        MyTopAppBar(
            title = "Top app bar title",
            subtitle = "this is subtitle"
        )
    }
}

@Composable
@PreviewLightDark
private fun FitfitTopAppBarPreview3(){
    BannerItTheme {
        MyTopAppBar(
            title = "Top app bar title",
            subtitle = "this is subtitle",
            navigationIcon = TopAppBarIcon.back,
            onClickNavigationIcon = { },
            actionIcon1 = TopAppBarIcon.edit,
            actionIcon2 = IconTextButtonIcon.add
        )
    }
}

@Composable
@PreviewLightDark
private fun FitfitTopAppBarPreview4(){
    BannerItTheme {
        MyTopAppBar(
            internetEnabled = false,
            title = "Top app bar title",
            navigationIcon = TopAppBarIcon.back,
            onClickNavigationIcon = { },
            actionIcon1 = TopAppBarIcon.edit,
            actionIcon2 = IconTextButtonIcon.add
        )
    }
}

@Composable
@Preview(
    widthDp = 600
)
private fun FitfitTopAppBarLarge_Dark(){
    BannerItTheme(darkTheme = true) {
        MyTopAppBar(
            title = "This is for large width",
            subtitle = "this is subtitle",
            navigationIcon = TopAppBarIcon.back,
            onClickNavigationIcon = { },
            actionIcon1 = TopAppBarIcon.edit,
            actionIcon2 = IconTextButtonIcon.add,
            useHorizontalLayoutTitles = true
        )
    }
}

@Composable
@Preview(
    widthDp = 600
)
private fun FitfitTopAppBarLarge(){
    BannerItTheme {
        MyTopAppBar(
            title = "This is for large width",
            subtitle = "this is subtitle",
            navigationIcon = TopAppBarIcon.back,
            onClickNavigationIcon = { },
            actionIcon1 = TopAppBarIcon.edit,
            actionIcon2 = IconTextButtonIcon.add,
            useHorizontalLayoutTitles = true
        )
    }
}

@Composable
@Preview(
    widthDp = 600
)
private fun FitfitTopAppBarLarge2_Dark(){
    BannerItTheme(darkTheme = true) {
        MyTopAppBar(
            internetEnabled = false,
            title = "This is for large width",
            navigationIcon = TopAppBarIcon.back,
            onClickNavigationIcon = { },
            actionIcon1 = TopAppBarIcon.edit,
            actionIcon2 = IconTextButtonIcon.add,
            useHorizontalLayoutTitles = true
        )
    }
}

@Composable
@Preview(
    widthDp = 600
)
private fun FitfitTopAppBarLarge2(){
    BannerItTheme {
        MyTopAppBar(
            internetEnabled = false,
            title = "This is for large width",
            navigationIcon = TopAppBarIcon.back,
            onClickNavigationIcon = { },
            actionIcon1 = TopAppBarIcon.edit,
            actionIcon2 = IconTextButtonIcon.add,
            useHorizontalLayoutTitles = true
        )
    }
}