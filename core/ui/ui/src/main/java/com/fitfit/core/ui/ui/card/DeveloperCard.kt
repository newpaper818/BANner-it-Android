package com.fitfit.core.ui.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import com.fitfit.core.ui.ui.R

@Composable
fun DeveloperCard(
    modifier: Modifier = Modifier
){
    MyCard(
        modifier = modifier.semantics(mergeDescendants = true) { }
    ) {
        Column(
            Modifier.fillMaxWidth().padding(16.dp, 14.dp)
        ) {
            Text(
                text = stringResource(id = R.string.developer),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )

            MySpacerColumn(height = 8.dp)

            Text(
                text = stringResource(id = R.string.developer_info),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}






















@Composable
@PreviewLightDark
private fun DeveloperCardPreview(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            DeveloperCard()
        }
    }
}