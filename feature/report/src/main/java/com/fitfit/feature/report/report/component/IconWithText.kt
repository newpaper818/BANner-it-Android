package com.fitfit.feature.report.report.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.MyIcon

@Composable
internal fun IconWithText(
    icon: MyIcon,
    text: String
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DisplayIcon(
            icon = icon
        )

        MySpacerColumn(16.dp)

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}