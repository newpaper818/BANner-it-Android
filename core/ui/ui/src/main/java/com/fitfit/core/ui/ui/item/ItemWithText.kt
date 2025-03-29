package com.fitfit.core.ui.ui.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.ClickableBox
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.MyIcon
import com.fitfit.core.ui.designsystem.icon.MyIcons
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import com.fitfit.core.utils.listItemHeight

@Composable
fun ItemWithText(
    isSelected: Boolean = false,
    icon: MyIcon? = null,
    text: String?= null,
    subText: String? = null,

    isOpenInNew: Boolean = false,
    showClickableIcon: Boolean = false,
    onItemClick: (() -> Unit)? = null
){
    val textStyle = MaterialTheme.typography.bodyLarge
    val subTextStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)


    ClickableBox(
        containerColor = if (isSelected) MaterialTheme.colorScheme.outlineVariant
                            else Color.Transparent,
        modifier = Modifier.fillMaxWidth(),
        enabled = onItemClick != null,
        onClick = onItemClick ?: { }
    ) {
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(listItemHeight)
                    .padding(16.dp, 0.dp)
            ) {
                if (icon != null) {
                    DisplayIcon(icon = icon)

                    MySpacerRow(width = 10.dp)
                }

                if (text != null) {
                    Text(
                        text = text,
                        style = textStyle
                    )

                    MySpacerRow(width = 8.dp)
                }

                if (subText != null) {
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = subText,
                        style = subTextStyle,
                        textAlign = TextAlign.End,
                        modifier = Modifier.padding(6.dp, 0.dp, 0.dp, 0.dp)
                    )
                }

                if (isOpenInNew) {
                    Spacer(modifier = Modifier.weight(1f))

                    DisplayIcon(icon = MyIcons.openInNew)
                }
                else if (showClickableIcon) {
                    Spacer(modifier = Modifier.weight(1f))

                    DisplayIcon(icon = MyIcons.clickableItem)
                }
            }
        }
    }
}

















@Composable
@PreviewLightDark
private fun Preview(){
    BannerItTheme {
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .width(250.dp)
        ) {
            ListGroupCard {
                ItemWithText(
                    text = "item text"
                )

                ItemDivider()

                ItemWithText(
                    text = "item text",
                    subText = "subtext"
                )

                ItemDivider()

                ItemWithText(
                    isSelected = true,
                    text = "selected item",
                    subText = "subtext"
                )

                ItemDivider()

                ItemWithText(
                    text = "item",
                    subText = "subtext",
                    isOpenInNew = true
                )
            }
        }
    }
}