package com.fitfit.feature.more.deleteAccount.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.ProviderId
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.feature.more.R
import com.fitfit.feature.more.deleteAccount.DeleteAccountUiState

@Composable
internal fun AuthButtonsWithText(
    internetEnabled: Boolean,
    isDarkAppTheme: Boolean,
    userData: UserData,
    deleteAccountUiState: DeleteAccountUiState,
    onClickAuthButton: (providerId: ProviderId) -> Unit,
    modifier: Modifier = Modifier
){
    MyCard(
        modifier = modifier
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .widthIn(min = 350.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //auth with currently ...
            Text(
                text = stringResource(id = R.string.re_auth_match_user),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            //auth button
            AuthButtons(
                providerIds = userData.providerIds,
                enabled = false,
//                internetEnabled
//                        && deleteAccountUiState.authButtonEnabled
//                        && !deleteAccountUiState.isAuthDone,
                onClick = onClickAuthButton,
                authingWith = deleteAccountUiState.authingWith,
                isDarkAppTheme = isDarkAppTheme,
                isAuthDone = deleteAccountUiState.isAuthDone
            )
        }
    }
}