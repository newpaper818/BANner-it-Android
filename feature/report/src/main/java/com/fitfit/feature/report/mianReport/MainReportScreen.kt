package com.fitfit.feature.report.mianReport

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.feature.report.R

@Composable
fun MainReportRoute(
    navigateToWorkout: () -> Unit,

    use2Panes: Boolean,
    spacerValue: Dp,

    modifier: Modifier = Modifier,
) {

    MainWorkoutScreen(
        spacerValue = spacerValue,
        navigateToWorkout = navigateToWorkout
    )
}

@Composable
private fun MainWorkoutScreen(
    spacerValue: Dp,

    navigateToWorkout: () -> Unit,
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
            //select take a picture or get picture from gallery


        }
    }
}