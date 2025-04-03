package com.fitfit.feature.more.deleteAccount.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.enums.ProviderId
import com.fitfit.core.ui.designsystem.components.button.AuthWithButton
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn

@Composable
internal fun AuthButtons(
    providerIds: List<ProviderId>,
    enabled: Boolean,
    onClick: (providerId: ProviderId) -> Unit,
    authingWith: ProviderId?,
    isDarkAppTheme: Boolean,
    isAuthDone: Boolean
){
    providerIds.forEach { providerId ->
        MySpacerColumn(height = 16.dp)

        AuthWithButton(
            providerId = providerId,
            authingWithThis = authingWith == providerId,
            onClick = { onClick(providerId) },
            enabled = enabled,
            isDarkAppTheme = isDarkAppTheme,
            isAuthDone = isAuthDone
        )
    }
}