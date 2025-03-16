package com.fitfit.feature.report.mianReport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.UserData
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.button.ReportBannerButton
import com.fitfit.core.ui.designsystem.components.button.SignInButton
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.feature.report.R

@Composable
fun MainReportRoute(
    appUserData: UserData?,

    use2Panes: Boolean,
    spacerValue: Dp,

    navigateToSignIn: () -> Unit,
    navigateToReport: () -> Unit,

    modifier: Modifier = Modifier,
) {

    MainWorkoutScreen(
        appUserData = appUserData,
        spacerValue = spacerValue,
        navigateToSignIn = navigateToSignIn,
        navigateToReport = navigateToReport
    )
}

@Composable
private fun MainWorkoutScreen(
    appUserData: UserData?,
    spacerValue: Dp,

    navigateToSignIn: () -> Unit,
    navigateToReport: () -> Unit,
){
    MyScaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .displayCutoutPadding()
            .imePadding(),

        //top app bar
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.report),
                startPadding = spacerValue
            )
        }
    ){ paddingValues ->

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(spacerValue, 16.dp, spacerValue, 200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .navigationBarsPadding()

        ){
            item {
                MySpacerColumn(300.dp)

                if (appUserData != null){
                    ReportBannerButton(
                        onClick = navigateToSignIn
                    )
                }
                else{
                    SignInButton(
                        onClick = navigateToSignIn
                    )

                    MySpacerColumn(16.dp)

                    Text(
                        text = stringResource(R.string.you_should_sign_in),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}