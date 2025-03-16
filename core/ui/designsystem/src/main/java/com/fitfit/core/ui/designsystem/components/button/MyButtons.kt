package com.fitfit.core.ui.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.ClickableBox
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.IconTextButtonIcon
import com.fitfit.core.ui.designsystem.icon.MyIcon
import com.fitfit.core.ui.designsystem.theme.BannerItTheme

@Composable
internal fun MyTextButton(
    text: String,
    onClick: () -> Unit,

    modifier: Modifier = Modifier,

    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
){
    if (containerColor == Color.Transparent){
        TextButton(
            enabled = enabled,
            onClick = onClick,
            modifier = modifier
        ) {
            Text(
                text = text,
                style = textStyle,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    else {
        Button(
            enabled = enabled,
            onClick = onClick,
            modifier = modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = MaterialTheme.colorScheme.contentColorFor(containerColor)
            )
        ) {
            Text(
                text = text,
                style = textStyle,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MyTextRippleButton(
    text: String,
    onClick: () -> Unit,

    modifier: Modifier = Modifier,

    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    textColor: Color = MaterialTheme.colorScheme.primary,
    enabled: Boolean = true
){
    val textStyle1 =
        if (enabled) textStyle.copy(color = textColor)
        else textStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)

    val rippleConfiguration = RippleConfiguration(
        color = textColor
    )

    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        TextButton(
            onClick = onClick,
            enabled = enabled,
            modifier = modifier
        ) {
            Text(
                text = text,
                style = textStyle1,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
internal fun MyIconButton(
    icon: MyIcon,
    onClick: () -> Unit,

    modifier: Modifier = Modifier,

    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
){
    ClickableBox(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        containerColor = containerColor,
        shape = CircleShape,
        contentAlignment = Alignment.Center
    ) {
        DisplayIcon(
            icon = icon,
            color = MaterialTheme.colorScheme.contentColorFor(containerColor)
        )
    }
}

@Composable
internal fun MyIconTextButton(
    icon: MyIcon,
    text: String,
    onClick: () -> Unit,

    modifier: Modifier = Modifier,

    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
){
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = MaterialTheme.colorScheme.contentColorFor(containerColor)
        ),
        contentPadding = PaddingValues(16.dp, 0.dp, 20.dp, 0.dp),
        enabled = enabled,
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            DisplayIcon(
                icon = icon
            )

            MySpacerRow(8.dp)

            Text(
                text = text,
                style = textStyle,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
internal fun MyIconTextButtonColumn(
    icon: MyIcon,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
){
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceDim,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(14.dp, 8.dp),
        enabled = enabled,
        onClick = onClick,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DisplayIcon(icon = icon)

            MySpacerColumn(6.dp)

            Text(
                text = text,
                style = if (enabled) textStyle
                else textStyle.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}















@Composable
@PreviewLightDark
private fun Preview_MyTextButton(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .width(190.dp)
        ) {
            MyTextButton(
                text = "text button",
                onClick = {}
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun Preview_MyTextButtonDisabled(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .width(190.dp)
        ) {
            MyTextButton(
                text = "text button",
                onClick = {},
                enabled = false
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun Preview_MyTextButtonTransparent(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .width(190.dp)
        ) {
            MyTextButton(
                text = "text button",
                onClick = {},
                containerColor = Color.Transparent
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun Preview_MyTextButtonTransparentDisabled(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .width(190.dp)
        ) {
            MyTextButton(
                text = "text button",
                onClick = {},
                containerColor = Color.Transparent,
                enabled = false
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun Preview_MyIconTextButton(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .width(190.dp)
        ) {
            MyIconTextButton(
                icon = IconTextButtonIcon.add,
                text = "Icon text button",
                onClick = {}
            )
        }
    }
}