package com.fitfit.core.ui.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.R
import com.fitfit.core.ui.designsystem.icon.MyIcons
import com.fitfit.core.ui.designsystem.theme.BannerItTheme

@Composable
fun DeleteAccountButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    MyTextButton(
        text = stringResource(id = R.string.delete_account),
        enabled = enabled,
        onClick = onClick,
        modifier = modifier.width(180.dp),
        containerColor =  MaterialTheme.colorScheme.error,
        textStyle = if (enabled) MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onError)
        else MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
    )
}

@Composable
fun ChangeProfileImageButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
){
    MyIconTextButtonColumn(
        icon = MyIcons.changeProfileImage,
        text = stringResource(id = R.string.change_image),
        onClick = onClick,
        enabled = enabled,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

@Composable
fun DeleteProfileImageButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
){
    MyIconTextButtonColumn(
        icon = MyIcons.deleteProfileImage,
        text = stringResource(id = R.string.delete_image),
        onClick = onClick,
        enabled = enabled,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}











@Composable
@PreviewLightDark
private fun DeleteAccountButtonPreview(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .width(190.dp)
        ) {
            DeleteAccountButton(
                enabled = true,
                onClick = {}
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun DeleteAccountButtonDisabledPreview(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .width(190.dp)
        ) {
            DeleteAccountButton(
                enabled = false,
                onClick = {}
            )
        }
    }
}