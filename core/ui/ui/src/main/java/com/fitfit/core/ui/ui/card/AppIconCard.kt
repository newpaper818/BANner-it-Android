package com.fitfit.core.ui.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.ImageFromDrawable
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import com.fitfit.core.ui.ui.R

@Composable
fun AppIconCard(
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .semantics(mergeDescendants = true) { }
    ) {
        ImageFromDrawable(
            imageDrawable = R.drawable.bannerit_app_icon_small,
            contentDescription = stringResource(id = R.string.bannerit_app_icon),
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(32.dp))
                .clearAndSetSemantics {  }
        )
    }
}



















@Composable
@PreviewLightDark
private fun AppIconWithAppNameCardPreview(){
    BannerItTheme {
        MyScaffold {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                AppIconCard()
            }
        }
    }
}