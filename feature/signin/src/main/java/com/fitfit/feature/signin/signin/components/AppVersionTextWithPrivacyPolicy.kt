package com.fitfit.feature.signin.signin.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitfit.core.ui.designsystem.components.button.PrivacyPolicyButton

@Composable
internal fun AppVersionTextWithPrivacyPolicy(
    appVersionName: String,
    onClickPrivacyPolicy: () -> Unit
){
    Column(
        modifier  = Modifier.height(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrivacyPolicyButton(onClick = onClickPrivacyPolicy)

        Text(
            text = "v $appVersionName",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 1.sp
        )
    }
}