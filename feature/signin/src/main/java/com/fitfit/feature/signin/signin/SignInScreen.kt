package com.fitfit.feature.signin.signin

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.ProviderId
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.button.SignInWithButton
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.utils.onClickPrivacyPolicy
import com.fitfit.feature.signin.R
import com.fitfit.feature.signin.signin.components.AppVersionTextWithPrivacyPolicy
import com.fitfit.feature.signin.signin.components.InternetUnavailableIconWithTextForSignIn
import com.fitfit.feature.signin.signin.components.SigningInIconWithText
import com.fitfit.feature.signin.signin.components.WelcomeText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignInRoute(
    isDarkAppTheme: Boolean,
    internetEnabled: Boolean,
    appVersionName: String,

    updateUserData: (userData: UserData) -> Unit,
    navigateToMain: () -> Unit,

    signInViewModel: SignInViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = context as ComponentActivity

    val signInUiState by signInViewModel.signInUiState.collectAsState()
    val isSigningIn = signInUiState.isSigningIn
    val signInButtonEnabled = signInUiState.signInButtonEnabled

    //snackbar
    val snackBarHostState = remember { SnackbarHostState() }
    val signInErrorText = stringResource(id = R.string.sign_in_error)

    val signInErrorSnackbar = {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = signInErrorText
            )
        }
    }

    LaunchedEffect(Unit) {
        signInViewModel.setIsSigningIn(false)
    }

    //set signInButtonEnabled
    LaunchedEffect(internetEnabled, isSigningIn) {
        if (!isSigningIn && !signInButtonEnabled){
            delay(1200)
            signInViewModel.setSignInButtonEnabled(internetEnabled)
        }
        else {
            signInViewModel.setSignInButtonEnabled(internetEnabled && !isSigningIn)
        }
    }







    val uriHandler = LocalUriHandler.current

    SignInScreen(
        isDarkAppTheme = isDarkAppTheme,
        internetEnabled = internetEnabled,
        appVersionName = appVersionName,

        isSigningIn = isSigningIn,
        signInButtonEnabled = signInButtonEnabled,

        onSignInClick = {providerId ->
            when (providerId){
                ProviderId.GOOGLE -> {
                    coroutineScope.launch {
                        signInViewModel.signInWithGoogle(
                            context = context,
                            onResult = { userData ->
                                updateUserData(userData)
                            },
                            onError = { signInErrorSnackbar() }
                        )
                    }
                }
            }
        },
        onClickPrivacyPolicy = { onClickPrivacyPolicy(uriHandler) },
        setSignInButtonEnabled = signInViewModel::setSignInButtonEnabled,
        snackBarHostState = snackBarHostState
    )
}

@Composable
private fun SignInScreen(
    isDarkAppTheme: Boolean,
    internetEnabled: Boolean,
    appVersionName: String,

    isSigningIn: Boolean,
    signInButtonEnabled: Boolean,

    onSignInClick: (providerId: ProviderId) -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    setSignInButtonEnabled: (signInButtonEnabled: Boolean) -> Unit,
    snackBarHostState: SnackbarHostState,

    modifier: Modifier = Modifier
){
    MyScaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .width(500.dp)
                    .navigationBarsPadding(),
                snackbar = {
                    Snackbar(
                        snackbarData = it,
                        shape = MaterialTheme.shapes.medium
                    )
                }
            )
        }
    ) { _ ->

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .displayCutoutPadding(),
        ){
            item{
                MySpacerColumn(height = 32.dp)

                //app icon with text
//                AppIconWithAppNameCard(
//                    modifier = Modifier.padding(16.dp, 0.dp)
//                )
                MySpacerColumn(height = 32.dp)
            }

            item {
                Box(
                    modifier = Modifier.height(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    //signing in...
                    SigningInIconWithText(isSigningIn)

                    //internet unavailable
                    InternetUnavailableIconWithTextForSignIn(!isSigningIn && !internetEnabled)

                    //welcome message
                    WelcomeText(!isSigningIn && internetEnabled)
                }

                MySpacerColumn(height = 32.dp)
            }

            item {
                //sign in buttons
                val providerIds = ProviderId.entries.toTypedArray()
                val providerIdCnt = providerIds.size

                providerIds.forEachIndexed { index, providerId ->
                    SignInWithButton(
                        providerId = providerId,
                        onClick = {
                            if (signInButtonEnabled) {
                                setSignInButtonEnabled(false)
                                onSignInClick(providerId)
                            }
                        },
                        buttonEnabled = signInButtonEnabled,
                        isDarkAppTheme = isDarkAppTheme,
                        modifier = Modifier.padding(16.dp, 0.dp)
                    )

                    if (index + 1 != providerIdCnt)
                        MySpacerColumn(height = 16.dp)
                }

                MySpacerColumn(height = 32.dp)
            }

            item {
                //App version with privacy policy button
                AppVersionTextWithPrivacyPolicy(
                    appVersionName = appVersionName,
                    onClickPrivacyPolicy = onClickPrivacyPolicy
                )

                MySpacerColumn(height = 16.dp)
            }
        }
    }
}