package com.fitfit.feature.more.editProfile

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.UserRole
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.ui.InternetUnavailableText
import com.fitfit.core.ui.ui.dialog.TwoButtonsDialog
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.more.R
import com.fitfit.feature.more.editProfile.component.AccountInfo
import com.fitfit.feature.more.editProfile.component.EditableProfileImage
import com.fitfit.feature.more.editProfile.component.EditableUserName
import com.fitfit.feature.more.editProfile.component.EditableUserRole
import kotlinx.coroutines.launch

@Composable
fun EditProfileRoute(
    userData: UserData,
    internetEnabled: Boolean,
    spacerValue: Dp,

    updateUserState: (userData: UserData) -> Unit,
    navigateUp: () -> Unit,

    modifier: Modifier = Modifier,
    editProfileViewModel: EditProfileViewModel = hiltViewModel(),
){
    LaunchedEffect(Unit) {
        editProfileViewModel.initEditAccountViewModel(userData)
    }

    val editProfileUiState by editProfileViewModel.editProfileUiState.collectAsState()

//    val galleryLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.GetContent()
//    ){ uri ->
//        if (uri != null) {
//            val fileName = editProfileViewModel.saveImageToInternalStorage(
//                tripId = 0,
//                index = 0,
//                uri = uri,
//                isProfileImage = true
//            )
//
//            if (fileName != null) {
//                editProfileViewModel.onUserProfileImageChanged(fileName)
//            }
//        }
//    }

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val successText = stringResource(id = R.string.snackbar_success_update_profile)
    val failText = stringResource(id = R.string.snackbar_fail_update_profile)
    val noChangedText = stringResource(id = R.string.snackbar_no_changed)

    val onClickBackButton = {
        if (editProfileViewModel.checkProfileInfoChanged(userData))
            editProfileViewModel.setShowExitDialog(true)
        else
            navigateUp()

    }

    BackHandler {
        onClickBackButton()
    }


    EditProfileScreen(
        editProfileUiState = editProfileUiState,
        userData = userData,
        spacerValue = spacerValue,
        internetEnabled = internetEnabled,
        showExitDialog = editProfileUiState.showExitDialog,
        setShowExitDialog = editProfileViewModel::setShowExitDialog,
        snackBarHostState = snackBarHostState,
        downloadImage = {_,_,_ ->},
        onClickEditImage = {
//            galleryLauncher.launch("image/*")
        },
        onClickDeleteImage = {
            editProfileViewModel.onUserProfileImageChanged(null)
        },
        onUserNameChanged = editProfileViewModel::onUserNameChanged,
        onUserRoleChanged = editProfileViewModel::onUserRoleChanged,
        onSaveClick = {
            editProfileViewModel.saveProfile(
                userData = userData,
                showSuccessSnackbar = {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message = successText)
                    }
                },
                showFailSnackbar = {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message = failText)
                    }
                },
                showNoChangedSnackbar = {
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message = noChangedText)
                    }
                },
                updateUserState = updateUserState
            )
        },
        onClickBack = onClickBackButton,
        navigateUp = navigateUp,
        modifier = modifier
    )
}

@Composable
private fun EditProfileScreen(
    editProfileUiState: EditProfileUiState,
    userData: UserData,
    spacerValue: Dp,
    internetEnabled: Boolean,
    showExitDialog: Boolean,
    setShowExitDialog: (Boolean) -> Unit,

    snackBarHostState: SnackbarHostState,
    downloadImage: (imagePath: String, tripManagerId: String, (Boolean) -> Unit) -> Unit,

    onClickEditImage: () -> Unit,
    onClickDeleteImage: () -> Unit,
    onUserNameChanged: (String) -> Unit,
    onUserRoleChanged: (UserRole) -> Unit,
    onSaveClick: () -> Unit,
    onClickBack: () -> Unit,

    navigateUp: () -> Unit,

    modifier: Modifier = Modifier,
){
    MyScaffold(
        modifier = modifier
            .imePadding()
            .navigationBarsPadding()
            .displayCutoutPadding(),
        contentWindowInsets = WindowInsets(bottom = 0),

        bottomSaveCancelBarVisible = !showExitDialog,
        onClickCancel = onClickBack,
        onClickSave = onSaveClick,
        saveEnabled = internetEnabled
                && !editProfileUiState.isInvalidUserName
                && editProfileUiState.isSaveButtonEnabled,

        topBar = {
            MyTopAppBar(
                startPadding = spacerValue,
                title = stringResource(id = R.string.edit_profile),
                navigationIcon = TopAppBarIcon.close,
                onClickNavigationIcon = { onClickBack() }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .width(500.dp)
                    .padding(bottom = 60.dp)
                    .imePadding(),
                snackbar = {
                    Snackbar(
                        snackbarData = it,
                        shape = MaterialTheme.shapes.medium
                    )
                }
            )
        }
    ) { paddingValues ->

        //dialog
        if (showExitDialog){
            TwoButtonsDialog(
                bodyText = stringResource(id = R.string.dialog_body_are_you_sure_to_exit),
                positiveButtonText = stringResource(id = R.string.dialog_button_exit),
                onDismissRequest = { setShowExitDialog(false) },
                onClickPositive = {
                    setShowExitDialog(false)
                    navigateUp()
                }
            )
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(spacerValue, 8.dp, spacerValue, 200.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            //profile image
            item {
                EditableProfileImage(
                    internetEnabled = internetEnabled,
                    userId = userData.userId,
                    profileImagePreviewUrl = editProfileUiState.userProfileImagePath,
                    downloadImage = downloadImage,
                    onClickEditImage = onClickEditImage,
                    onClickDeleteImage = onClickDeleteImage,
                    modifier  = Modifier.widthIn(max = itemMaxWidthSmall)
                )
            }



            //user name
            item {
                EditableUserName(
                    userName = editProfileUiState.userName,
                    isInvalidText = editProfileUiState.isInvalidUserName,
                    onUserNameChanged = onUserNameChanged,
                    modifier  = Modifier.widthIn(max = itemMaxWidthSmall)
                )
            }

            //user role
//            item {
//                EditableUserRole(
//                    userRole = userData.role,
//                    onUserRoleChanged = onUserRoleChanged,
//                    modifier  = Modifier.widthIn(max = itemMaxWidthSmall)
//                )
//            }

            //internet unavailable
            item {
                AnimatedVisibility(
                    visible = !internetEnabled,
                    enter = expandVertically(tween(500)) + fadeIn(tween(500, 200)),
                    exit = shrinkVertically(tween(500, 200)) + fadeOut(tween(500))
                ) {
                    InternetUnavailableText()
                }
            }

            //email
            item {
                MySpacerColumn(height = 32.dp)
                AccountInfo(userData = userData)
            }
        }
    }
}

