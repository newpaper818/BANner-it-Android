package com.fitfit.feature.report.camera.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.button.SettingsButton
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.MyIcons
import com.fitfit.feature.report.R

@Composable
internal fun CameraPermissionNotGrantedCard(
    onClickSettingsButton: () -> Unit
){

    MyCard(

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayIcon(icon = MyIcons.noCamera)

                MySpacerColumn(height = 12.dp)

                Text(
                    text = stringResource(id = R.string.camera_permission_denied),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

                MySpacerColumn(height = 8.dp)

                Text(
                    text = stringResource(id = R.string.please_allow_camera_permissions),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )

                MySpacerColumn(height = 16.dp)

                SettingsButton(
                    onClick = onClickSettingsButton
                )
            }
        }
    }
}