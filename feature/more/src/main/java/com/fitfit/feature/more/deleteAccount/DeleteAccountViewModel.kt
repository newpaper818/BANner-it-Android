package com.fitfit.feature.more.deleteAccount

import android.app.Activity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitfit.core.data.data.repository.PreferencesRepository
import com.fitfit.core.data.data.repository.account.DeleteAccountRepository
import com.fitfit.core.model.enums.ProviderId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DeleteAccountUiState(
    val authButtonEnabled: Boolean = true,
    val authingWith: ProviderId? = null,
    val isAuthing: Boolean = false,
    val isAuthDone: Boolean = false,
    val isDeletingAccount: Boolean = false,
)

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val deleteAccountRepository: DeleteAccountRepository,
    private val preferencesRepository: PreferencesRepository
): ViewModel() {
    private val _deleteAccountUiState = MutableStateFlow(DeleteAccountUiState())
    val deleteAccountUiState = _deleteAccountUiState.asStateFlow()



    //==============================================================================================
    //get image ====================================================================================
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



    //==============================================================================================
    //re authenticate ==============================================================================
    fun reAuthenticate(
        activity: Activity,
        providerId: ProviderId,
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        showReAuthErrorSnackbar: () -> Unit,
        showReAuthErrorUserNotMatchSnackbar: () -> Unit
    ){
        setAuthButtonEnabled(false)
        setAuthingWith(providerId)

        viewModelScope.launch {
            when (providerId){
                ProviderId.GOOGLE -> {
//                    userRepository.reAuthLaunchGoogleLauncher(
//                        launcher = launcher,
//                        setIsAuthing = { setIsAuthing(it) },
//                        showErrorSnackbar = showReAuthErrorSnackbar
//                    )
                }
//                ProviderId.APPLE -> {
//                    userRepository.reAuthenticateAppleUser(
//                        activity = activity,
//                        setIsAuthing = { setIsAuthing(it) },
//                        setIsAuthDone = { setIsAuthDone(it) },
//                        showReAuthErrorSnackbar = showReAuthErrorSnackbar,
//                        showReAuthErrorUserNotMatchSnackbar = showReAuthErrorUserNotMatchSnackbar
//                    )
//                }
            }
        }

    }

    fun reAuthGoogleResult(
        result: ActivityResult,
        showReAuthErrorSnackbar: () -> Unit,
        showReAuthErrorUserNotMatchSnackbar: () -> Unit
    ){
//        userRepository.reAuthGoogleResult(
//            result = result,
//            setIsAuthing = { setIsAuthing(it) },
//            setIsAuthDone = { setIsAuthDone(it) },
//            showReAuthErrorSnackbar = showReAuthErrorSnackbar,
//            showReAuthErrorUserNotMatchSnackbar = showReAuthErrorUserNotMatchSnackbar
//        )
    }



    //==============================================================================================
    //delete account ===============================================================================
    /**
     * ⚠️ DELETE ACCOUNT ⚠️
     */
    suspend fun deleteAccount(
        jwt: String,
    ): Boolean {
        setIsDeletingAccount(true)

        return deleteAccountRepository.deleteAccount(jwt = jwt)
    }




    //==============================================================================================
    //sign out =====================================================================================
    suspend fun signOut(
        signOutResult: (isSignOutSuccess: Boolean) -> Unit
    ){
        preferencesRepository.saveJwtPreference(null)
        preferencesRepository.getJwtPreference(
            onGet = { jwt ->
                if (jwt == null)
                    signOutResult(true)
                else
                    signOutResult(false)
            }
        )
    }









    //==============================================================================================
    //set UiState ==================================================================================
    fun setAuthButtonEnabled(
        authButtonEnabled: Boolean
    ){
        _deleteAccountUiState.update {
            it.copy(authButtonEnabled = authButtonEnabled)
        }
    }

    fun setAuthingWith(
        authingWith: ProviderId?
    ){
        _deleteAccountUiState.update {
            it.copy(authingWith = authingWith)
        }
    }

    private fun setIsAuthing(
        isAuthing: Boolean
    ){
        _deleteAccountUiState.update {
            it.copy(isAuthing = isAuthing)
        }
    }

    private fun setIsAuthDone(
        isAuthDone: Boolean
    ){
        _deleteAccountUiState.update {
            it.copy(isAuthDone = isAuthDone)
        }
    }

    fun setIsDeletingAccount(
        isDeletingAccount: Boolean
    ){
        _deleteAccountUiState.update {
            it.copy(isDeletingAccount = isDeletingAccount)
        }
    }

}