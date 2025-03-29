package com.fitfit.core.ui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.button.BottomGoBackButton
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.MyIcons
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import kotlinx.coroutines.delay

@Composable
fun ErrorScreen(
    onClickGoBack: () -> Unit
){
    var visible by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(200)
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        ErrorScreenUi(
            onClickGoBack = onClickGoBack
        )
    }
}

@Composable
private fun ErrorScreenUi(
    onClickGoBack: () -> Unit
){
    Column(
        modifier = Modifier
        .navigationBarsPadding()
        .displayCutoutPadding()
        .imePadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DisplayIcon(icon = MyIcons.error)

                MySpacerColumn(height = 12.dp)

                Text(
                    text = stringResource(id = R.string.error_occurred),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )

//            MySpacerColumn(height = 8.dp)
//
//            Text(
//                text = stringResource(id = R.string.turning_the_app_off_and_on),
//                textAlign = TextAlign.Center,
//                style = MaterialTheme.typography.bodyLarge
//            )
            }
        }

        //go back button
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            BottomGoBackButton(
                onClick = onClickGoBack
            )
        }
    }
}




@PreviewLightDark
@Composable
fun ErrorScreenPreview(){
    BannerItTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ErrorScreenUi(
                onClickGoBack = {}
            )
        }
    }
}