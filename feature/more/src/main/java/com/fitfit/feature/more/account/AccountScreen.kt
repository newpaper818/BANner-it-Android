package com.fitfit.feature.more.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.model.data.UserData
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.ui.card.UserProfileCard
import com.fitfit.core.ui.ui.dialog.TwoButtonsDialog
import com.fitfit.core.ui.ui.item.ItemDivider
import com.fitfit.core.ui.ui.item.ItemWithText
import com.fitfit.core.ui.ui.item.ListGroupCard
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.more.R
import kotlinx.coroutines.launch

@Composable
fun AccountRoute(
    use2Panes: Boolean,
    userData: UserData?,
    internetEnabled: Boolean,
    spacerValue: Dp,

    updateUserDataToNull: () -> Unit,

    navigateUp: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToDeleteAccount: () -> Unit,
    navigateToMainMore: () -> Unit,

    modifier: Modifier = Modifier,
    accountViewModel: AccountViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val snackBarHostState = remember { SnackbarHostState() }
    val signOutErrorText = stringResource(id = R.string.sign_out_error)

    val signOutErrorSnackbar = {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = signOutErrorText,
                duration = SnackbarDuration.Short
            )
        }
    }

    AccountScreen(
        userData = userData,
        onSignOut = {
            coroutineScope.launch {
                accountViewModel.signOut(
                    signOutResult = { isSignOutSuccess ->
                        if (isSignOutSuccess) {
                            navigateToMainMore()
                            updateUserDataToNull()
                        } else {
                            signOutErrorSnackbar()
                        }
                    }
                )
            }
        },
        navigateToEditProfile = navigateToEditProfile,
        navigateToDeleteAccount = navigateToDeleteAccount,
        internetEnabled = internetEnabled,
        startSpacerValue = if (use2Panes) spacerValue / 2 else spacerValue,
        endSpacerValue = spacerValue,
        navigateUp = navigateUp,
        snackBarHostState = snackBarHostState,
        modifier = modifier,
        use2Panes = use2Panes
    )
}

@Composable
private fun AccountScreen(
    use2Panes: Boolean,
    userData: UserData?,

    onSignOut: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToDeleteAccount: () -> Unit,

    internetEnabled: Boolean,
    startSpacerValue: Dp,
    endSpacerValue: Dp,
    navigateUp: () -> Unit,
    snackBarHostState: SnackbarHostState,

    modifier: Modifier = Modifier

){
    val scaffoldModifier = if (use2Panes) modifier
    else modifier.navigationBarsPadding()

    var showSignOutDialog by rememberSaveable { mutableStateOf(false) }

    if (showSignOutDialog) {
        TwoButtonsDialog(
            bodyText = stringResource(id = R.string.dialog_body_sign_out),
            positiveButtonText = stringResource(id = R.string.sign_out),
            onDismissRequest = {
                showSignOutDialog = false
            },
            onClickPositive = {
                showSignOutDialog = false
                onSignOut()
            }
        )
    }



    MyScaffold(
        modifier = scaffoldModifier,
        contentWindowInsets = WindowInsets(bottom = 0),

        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.account),
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

        val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(startSpacerValue, 8.dp, endSpacerValue, 200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            //user profile
            item {
                if(userData != null){
                    UserProfileCard(
                        userData = userData,
                        internetEnabled = internetEnabled,
                        showSignInWithInfo = false,
                        enabled = false,
                        modifier = itemModifier
                    )
                }
            }

            //edit profile
            item {
                ListGroupCard(
                    modifier = itemModifier
                ) {
                    ItemWithText(
                        text = stringResource(id = R.string.edit_profile),
                        onItemClick = navigateToEditProfile
                    )
                }
            }

            //sign out / delete account
            item {
                ListGroupCard(
                    modifier = itemModifier
                ) {
                    ItemWithText(
                        text = stringResource(id = R.string.sign_out),
                        onItemClick = { showSignOutDialog = true }
                    )

                    ItemDivider()

                    ItemWithText(
                        text = stringResource(id = R.string.delete_account),
                        onItemClick = navigateToDeleteAccount
                    )
                }
            }
        }
    }
}