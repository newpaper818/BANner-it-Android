package com.fitfit.feature.more.setDateTimeFormat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun UpperDateTimeExampleBox(
    dateText: String,
    timeText: String,
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(120.dp)
            .padding(16.dp, 0.dp)
            .semantics(mergeDescendants = true) { }
    ) {
        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .padding(0.dp, 0.dp, 8.dp, 0.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dateText,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.widthIn(min = 190.dp),
                    textAlign = TextAlign.Center
                )
            }


            Box(
                modifier = Modifier.height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = timeText,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.widthIn(min = 100.dp),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.7.sp
                )
            }
        }
    }
}