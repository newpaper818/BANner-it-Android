package com.fitfit.feature.more.editProfile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow

@Composable
internal fun AutoLayoutTwoContents(
    content1: @Composable () -> Unit,
    content2: @Composable () -> Unit,
){
    val configuration = LocalConfiguration.current
    val isNarrowWidth = configuration.screenWidthDp.dp < 340.dp

    if (isNarrowWidth){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            content1()
            MySpacerColumn(height = 12.dp)
            content2()
        }
    }
    else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 8.dp, 8.dp, 8.dp)
        ) {
            content1()
            MySpacerRow(width = 12.dp)
            content2()
        }
    }
}