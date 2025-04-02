package com.fitfit.feature.more.editProfile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitfit.core.data.data.repository.EditProfileRepository
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val MAX_NAME_LENGTH = 30

data class EditProfileUiState(
    val userName: String? = null,
    val userRole: UserRole = UserRole.USER,
    val userProfileImagePath: String? = null,

    val isInvalidUserName: Boolean = false,
    val isSaveButtonEnabled: Boolean = true,

    val showExitDialog: Boolean = false,
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val editProfileRepository: EditProfileRepository,
): ViewModel() {
    private val _editProfileUiState = MutableStateFlow(EditProfileUiState())
    val editProfileUiState = _editProfileUiState.asStateFlow()



    //==============================================================================================
    //init viewModel ===============================================================================
    fun initEditAccountViewModel(
        userData: UserData
    ){
        _editProfileUiState.update {
            it.copy(
                userName = userData.name,
                userRole = userData.role,
                userProfileImagePath = userData.profileImageUrl,
                isInvalidUserName = (userData.name?.length ?: 0) > MAX_NAME_LENGTH
            )
        }
    }

    //==============================================================================================
    //dialog ===============================================================================
    fun setShowExitDialog(showExitDialog: Boolean){
        _editProfileUiState.update {
            it.copy(showExitDialog = showExitDialog)
        }
    }

    //==============================================================================================
    //image ========================================================================================
//    fun getImage(
//        imagePath: String,
//        imageUserId: String,
//        result: (Boolean) -> Unit
//    ){
//        getImageRepository.getImage(
//            imagePath = imagePath,
//            imageUserId = imageUserId,
//            result = result
//        )
//    }
//
//    fun saveImageToInternalStorage(
//        tripId: Int,
//        index: Int,
//        uri: Uri,
//        isProfileImage: Boolean = true
//    ): String? {
//        return commonImageRepository.saveImageToInternalStorage(
//            tripId = tripId,
//            index = index,
//            uri = uri,
//            isProfileImage = isProfileImage
//        )
//    }



    //==============================================================================================
    //on edit value ================================================================================
    fun onUserProfileImageChanged(
        newProfileImage: String?
    ){
        _editProfileUiState.update {
            it.copy(
                userProfileImagePath = newProfileImage
            )
        }
    }

    fun onUserNameChanged(
        newUserName: String
    ){
        _editProfileUiState.update {
            it.copy(
                userName = newUserName.ifEmpty { null },
                isInvalidUserName = newUserName.length > MAX_NAME_LENGTH || newUserName.isEmpty()
            )
        }
    }

    fun onUserRoleChanged(
        newUserRole: UserRole
    ){
        _editProfileUiState.update {
            it.copy(
                userRole = newUserRole
            )
        }
    }





    //==============================================================================================
    //save =========================================================================================
    fun checkProfileInfoChanged(
        userData: UserData
    ): Boolean {
        val newProfileImagePath = _editProfileUiState.value.userProfileImagePath
        val newUserName = _editProfileUiState.value.userName
        val newUserRole = _editProfileUiState.value.userRole

        val isProfileImageChanged = userData.profileImageUrl != newProfileImagePath
        val isUserNameChanged = userData.name != newUserName
        val isUserRoleChanged = userData.role != newUserRole

        return isUserNameChanged || isProfileImageChanged || isUserRoleChanged
    }

    fun saveProfile(
        userData: UserData,

        showSuccessSnackbar: () -> Unit,
        showFailSnackbar: () -> Unit,
        showNoChangedSnackbar: () -> Unit,

        updateUserState: (userData: UserData) -> Unit
    ){
        viewModelScope.launch {
            _editProfileUiState.update {
                it.copy(isSaveButtonEnabled = false)
            }

            val newProfileImagePath = _editProfileUiState.value.userProfileImagePath
            val newUserName = _editProfileUiState.value.userName
            val newUserRole = _editProfileUiState.value.userRole

            //save to db
            //check name is same with before
            //if same, do nothing
            //else, save to db

            val isProfileImageChanged = userData.profileImageUrl != newProfileImagePath

            if (checkProfileInfoChanged(userData)) {
                //upload profile image
//                if (isProfileImageChanged) {
//                    editProfileRepository.updateProfileImage(
//                        userId = userData.userId,
//                        imagePath = newProfileImagePath
//                    )
//                }

                //update db
                val isSuccess = editProfileRepository.updateUserProfile(
                    jwt = userData.jwt,
                    userName = newUserName ?: "",
                    userRole = newUserRole
                )

                if (isSuccess){
                    //update user state
                    updateUserState(
                        userData.copy(
                            name = newUserName,
                            role = newUserRole,
                            profileImageUrl = newProfileImagePath)
                    )

                    //show success snackbar
                    showSuccessSnackbar()
                }
                else {
                    //show error snackbar
                    showFailSnackbar()
                }


            } else {
                showNoChangedSnackbar()
            }

            delay(1300)

            _editProfileUiState.update {
                it.copy(isSaveButtonEnabled = true)
            }
        }
    }
}