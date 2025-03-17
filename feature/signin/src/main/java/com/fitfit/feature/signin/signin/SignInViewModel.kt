package com.fitfit.feature.signin.signin

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fitfit.core.data.data.repository.CommonUiState
import com.fitfit.core.data.data.repository.CommonUiStateRepository
import com.fitfit.core.data.data.repository.PreferencesRepository
import com.fitfit.core.data.data.repository.account.SignInRepository
import com.fitfit.core.model.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val SIGN_IN_VIEWMODEL_TAG = "SignIn-ViewModel"

data class SignInUiState(
    val isSigningIn: Boolean = false,
    val signInButtonEnabled: Boolean = true
)

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val commonUiStateRepository: CommonUiStateRepository,
    private val signInRepository: SignInRepository,
    private val preferencesRepository: PreferencesRepository,
): ViewModel() {
    private val _signInUiState: MutableStateFlow<SignInUiState> =
        MutableStateFlow(SignInUiState())

    val signInUiState = _signInUiState.asStateFlow()

    init {
        //init commonUiState
        commonUiStateRepository._commonUiState.update {
            CommonUiState()
        }
    }


    //==============================================================================================
    //set UiState ==================================================================================
    fun setIsSigningIn(
        isSigningIn: Boolean
    ) {
        _signInUiState.update {
            it.copy(
                isSigningIn = isSigningIn
            )
        }
    }

    fun setSignInButtonEnabled(
        signInButtonEnabled: Boolean
    ) {
        _signInUiState.update {
            it.copy(
                signInButtonEnabled = signInButtonEnabled
            )
        }
    }



    //==============================================================================================
    //sign in ======================================================================================
    suspend fun signInWithGoogle(
        context: Context,
        onResult: (userData: UserData) -> Unit,
        onError: () -> Unit
    ){
        //FIXME: when user cancel it, not to show error snack bar

        setIsSigningIn(true)

        val jwtAndUserData = signInRepository.signInWithGoogle(context = context)

        if (jwtAndUserData == null){
            setIsSigningIn(false)
            onError()
        }
        else {
            val (jwt, userData) = jwtAndUserData
            preferencesRepository.saveJwtPreference(jwt)
            onResult(userData)
        }
    }







//    suspend fun signInLaunchGoogleLauncher(
//        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
//        showErrorSnackbar: () -> Unit
//    ) {
//        setIsSigningIn(true)
//        signInRepository.signInLaunchGoogleLauncher(
//            launcher = launcher,
//            signInError = {
//                setIsSigningIn(false)
//                showErrorSnackbar()
//            }
//        )
//    }

//    suspend fun signInWithGoogleResult(
//        result: ActivityResult,
//        onDone: (userData: UserData) -> Unit,
//        showErrorSnackbar: () -> Unit
//    ){
//        if (result.resultCode == Activity.RESULT_OK){
//            if (result.data == null){
//                setIsSigningIn(false)
//                return
//            }
//
//            //get signInResult from remote(firebase)
//            var userData = signInRepository.signInWithGoogleIntent(
//                intent = result.data!!
//            )
//
//
//            Log.d(SIGN_IN_VIEWMODEL_TAG, "signInWithGoogleResult - userData : $userData")
//
//            updateUserDataFromRemote(
//                userData = userData,
//                onDone = onDone,
//                showErrorSnackbar = showErrorSnackbar
//            )
//        }
//        else {
//            setIsSigningIn(false)
//        }
//    }







    //==============================================================================================
    private suspend fun updateUserDataFromRemote(
        userData: UserData?,
        onDone: (userData: UserData) -> Unit,
        showErrorSnackbar: () -> Unit
    ) {
        if (userData == null){
            setIsSigningIn(false)
            showErrorSnackbar()
        }
        else {
            //check user exit and
            //  if exit, get user data from firestore
            //  else, register user data to firestore
            signInRepository.updateUserDataFromRemote(
                userData = userData,
                setIsSigningIn = { setIsSigningIn(it) },
                onDone = onDone,
                showErrorSnackbar = showErrorSnackbar
            )
        }
    }
}