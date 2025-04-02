package com.fitfit.core.ui.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.theme.BannerItTheme

@Composable
fun ListGroupCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    cardColors: CardColors? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
    ) {
        if (title != null) {
            TitleText(
                text = title,
                modifier = Modifier.padding(start = 16.dp)
            )

            MySpacerColumn(height = 6.dp)
        }

        MyCard (
            modifier = Modifier.fillMaxWidth(),
            colors = cardColors
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                content()
            }
        }
    }
}

@Composable
fun ItemDivider(
    startPadding: Dp = 16.dp,
    endPadding: Dp = 16.dp
){
    Row {
        MySpacerRow(width = startPadding)
        HorizontalDivider(modifier = Modifier.weight(1f))
        MySpacerRow(width = endPadding)
    }
}
























@Composable
@PreviewLightDark
private fun Preview_ListGroupCard(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            ListGroupCard(
                title = "list group card title"
            ) {
                ItemWithText(
                    text = "item text",
                )

                ItemDivider()

                ItemWithText(
                    text = "item text",
                    subText = "sub text"
                )

                ItemDivider()

                ItemWithText(
                    isSelected = true,
                    text = "item text",
                    subText = "sub text"
                )

                ItemDivider()

                ItemWithText(
                    text = "item text",
                    subText = "sub text"
                )
            }
        }
    }
}