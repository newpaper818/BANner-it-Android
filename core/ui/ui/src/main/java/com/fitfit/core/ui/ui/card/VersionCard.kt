package com.fitfit.core.ui.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import com.fitfit.core.ui.ui.R

@Composable
fun VersionCard(
    currentAppVersionName: String,
    isLatestAppVersion: Boolean?,
    onClickUpdate: () -> Unit,
    modifier: Modifier = Modifier
){
    MyCard(
        modifier = modifier.semantics(mergeDescendants = true) { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.version),
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )

                MySpacerColumn(height = 8.dp)

                VersionText(
                    currentAppVersionName = currentAppVersionName,
                    isLatestAppVersion = isLatestAppVersion
                )
            }

//            if (isLatestAppVersion == false) {
//                Spacer(modifier = Modifier.weight(1f))
//
//                MySpacerRow(width = 6.dp)
//
//                UpdateButton(
//                    onClick = onClickUpdate
//                )
//            }
        }
    }
}

@Composable
private fun VersionText(
    currentAppVersionName: String,
    isLatestAppVersion: Boolean?,
){
    val latestText =
        when (isLatestAppVersion){
            true -> stringResource(id = R.string.latest)
            false -> stringResource(id = R.string.old_version)
            else -> ""
        }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.semantics {
            contentDescription = "$currentAppVersionName $latestText"
        }
    ) {
        currentAppVersionName.forEach {
            Text(
                text = it.toString(),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
//                modifier = if (it.toString() == ".") Modifier
//                            else Modifier.width(10.4.dp)
            )
        }

//        MySpacerRow(width = 4.dp)
//
//        Text(
//            text = latestText,
//            style = MaterialTheme.typography.bodyLarge
//        )
    }
}






























@Composable
@PreviewLightDark
private fun VersionCardPreview(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            VersionCard(
                currentAppVersionName = "1.6.0",
                isLatestAppVersion = true,
                onClickUpdate = { }
            )
        }
    }
}

@Composable
@PreviewLightDark
private fun VersionCardOldPreview(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            VersionCard(
                currentAppVersionName = "1.6.0",
                isLatestAppVersion = false,
                onClickUpdate = { }
            )
        }
    }
}