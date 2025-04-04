package com.fitfit.feature.more.deleteAccount

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.ProviderId
import com.fitfit.core.ui.designsystem.components.button.DeleteAccountButton
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.ui.InternetUnavailableText
import com.fitfit.core.ui.ui.card.UserProfileCard
import com.fitfit.core.ui.ui.dialog.TwoButtonsDialog
import com.fitfit.core.ui.ui.dialog.component.DialogButtonLayout
import com.fitfit.core.utils.itemMaxWidth
import com.fitfit.feature.more.R
import com.fitfit.feature.more.deleteAccount.component.AuthButtonsWithText
import com.fitfit.core.ui.ui.dialog.DeletingAccountDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DeleteAccountRoute(
    isDarkAppTheme: Boolean,
    userData: UserData,
    internetEnabled: Boolean,
    spacerValue: Dp,

    navigateUp: () -> Unit,
    onDeleteAccountDone: () -> Unit,

    modifier: Modifier = Modifier,
    deleteAccountViewModel: DeleteAccountViewModel = hiltViewModel()
){
    BackHandler {
        navigateUp()
    }

    val deleteAccountUiState by deleteAccountViewModel.deleteAccountUiState.collectAsState()

    LaunchedEffect(internetEnabled, deleteAccountUiState.isAuthing){
        deleteAccountViewModel.setAuthButtonEnabled(
            if (!deleteAccountUiState.isAuthing && !deleteAccountUiState.authButtonEnabled){
                //block click for 1.5 sec if user cancel sign in
                delay(1500)
                internetEnabled
            } else {
                internetEnabled && !deleteAccountUiState.isAuthing
            }
        )
    }


    //snackbar
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val reAuthErrorText = stringResource(id = R.string.re_auth_error)
    val reAuthErrorUserNotMatchText = stringResource(id = R.string.re_auth_match_user)
    val deleteAccountErrorText = stringResource(id = R.string.delete_account_error)

    val reAuthErrorSnackbar = {
        deleteAccountViewModel.setAuthingWith(null)
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = reAuthErrorText
            )
        }
    }

    val reAuthErrorUserNotMatchSnackbar = {
        deleteAccountViewModel.setAuthingWith(null)
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = reAuthErrorUserNotMatchText
            )
        }
    }

    val deleteAccountErrorSnackbar = {
        deleteAccountViewModel.setAuthingWith(null)
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = deleteAccountErrorText
            )
        }
    }







    //re auth google launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            deleteAccountViewModel.reAuthGoogleResult(
                result = result,
                showReAuthErrorSnackbar = { reAuthErrorSnackbar() },
                showReAuthErrorUserNotMatchSnackbar = { reAuthErrorUserNotMatchSnackbar() }
            )
        }
    )

    var showDeleteAccountDialog by rememberSaveable { mutableStateOf(false) }


    if (showDeleteAccountDialog) {
        TwoButtonsDialog(
            bodyText = stringResource(id = R.string.dialog_delete_account),
            positiveButtonText = stringResource(id = R.string.delete_account),
            dialogButtonLayout = DialogButtonLayout.AUTO,
            onDismissRequest = {
                showDeleteAccountDialog = false
            },
            onClickPositive = {
                //on click delete account

                showDeleteAccountDialog = false

                coroutineScope.launch {
                    //delete account
                    val isDeleteAccountSuccess = deleteAccountViewModel.deleteAccount(jwt = userData.jwt)

                    if (isDeleteAccountSuccess){
                        //sign out
                        deleteAccountViewModel.signOut(
                            signOutResult = { isSignOutSuccess ->
                                if (isSignOutSuccess){
                                    onDeleteAccountDone()
                                }
                                else {
                                    deleteAccountViewModel.setIsDeletingAccount(false)
                                    deleteAccountErrorSnackbar()
                                }
                            }
                        )
                    }
                    else {
                        deleteAccountViewModel.setIsDeletingAccount(false)
                        deleteAccountErrorSnackbar()
                    }
                }
            }
        )
    }








    //deleting account dialog
    if (deleteAccountUiState.isDeletingAccount) {
        BackHandler { /*do nothing*/ }

        DeletingAccountDialog()
    }






    val context = LocalContext.current

    DeleteAccountScreen(
        deleteAccountUiState = deleteAccountUiState,
        isDarkAppTheme = isDarkAppTheme,
        userData = userData,
        internetEnabled = internetEnabled,
        spacerValue = spacerValue,
        snackBarHostState = snackBarHostState,
        downloadImage = {_,_,_ -> },
        onClickAuthButton = { providerId ->
            deleteAccountViewModel.reAuthenticate(
                activity = context as Activity,
                providerId = providerId,
                launcher = launcher,
                showReAuthErrorSnackbar = { reAuthErrorSnackbar() },
                showReAuthErrorUserNotMatchSnackbar = { reAuthErrorUserNotMatchSnackbar() }
            )
        },
        onClickDeleteAccountButton = {
            showDeleteAccountDialog = true
        },
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
private fun DeleteAccountScreen(
    deleteAccountUiState: DeleteAccountUiState,
    isDarkAppTheme: Boolean,
    userData: UserData,
    internetEnabled: Boolean,
    spacerValue: Dp,
    snackBarHostState: SnackbarHostState,

    downloadImage: (imagePath: String, tripManagerId: String, (Boolean) -> Unit) -> Unit,
    onClickAuthButton: (providerId: ProviderId) -> Unit,
    onClickDeleteAccountButton: () -> Unit,

    navigateUp: () -> Unit,

    modifier: Modifier = Modifier
){
    Scaffold(
        modifier = modifier
            .imePadding()
            .navigationBarsPadding()
            .displayCutoutPadding(),
        contentWindowInsets = WindowInsets(bottom = 0),

        topBar = {
            MyTopAppBar(
                startPadding = spacerValue,
                title = stringResource(id = R.string.delete_account),
                navigationIcon = TopAppBarIcon.back,
                onClickNavigationIcon = { navigateUp() }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.width(500.dp),
                snackbar = {
                    Snackbar(
                        snackbarData = it,
                        shape = MaterialTheme.shapes.medium
                    )
                }
            )
        }
    ){ paddingValues ->

        val itemModifier = Modifier.widthIn(max = itemMaxWidth)

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(spacerValue, 8.dp, spacerValue, 200.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            //user profile
            item {
                UserProfileCard(
                    userData = userData,
                    internetEnabled = internetEnabled,
                    enabled = false,
                    modifier = itemModifier
                )
            }

            //text - delete account is ...
            item {
                Row(
                    modifier = itemModifier.padding(16.dp, 0.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.delete_account_explain),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            //authentication
            item {
                AuthButtonsWithText(
                    internetEnabled = internetEnabled,
                    isDarkAppTheme = isDarkAppTheme,
                    userData = userData,
                    deleteAccountUiState = deleteAccountUiState,
                    onClickAuthButton = onClickAuthButton,
                    modifier = modifier

                )

                AnimatedVisibility(
                    visible = !internetEnabled,
                    enter = expandVertically(tween(500)) + fadeIn(tween(500, 200)),
                    exit = shrinkVertically(tween(500, 200)) + fadeOut(tween(500))
                ) {
                    Column {
                        MySpacerColumn(height = 16.dp)

                        InternetUnavailableText()
                    }
                }
            }

            //delete account
            item {
                MySpacerColumn(height = 16.dp)
                DeleteAccountButton(
//                    enabled = internetEnabled && deleteAccountUiState.isAuthDone,
                    enabled = internetEnabled,
                    onClick = onClickDeleteAccountButton
                )
            }
        }
    }
}


