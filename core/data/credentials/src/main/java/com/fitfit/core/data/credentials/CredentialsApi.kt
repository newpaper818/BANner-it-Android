package com.fitfit.core.data.credentials

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.fitfit.core.model.data.UserData
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import javax.inject.Inject

private const val CREDENTIALS_TAG = "Credentials"

class CredentialsApi @Inject constructor(

): AuthRemoteDataSource {

    override suspend fun signinWithGoogle(
        context: Context, //activity based context
    ): String? {
        val credentialManager = CredentialManager.create(context)

        val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption.Builder(
            serverClientId = BuildConfig.GOOGLE_WEB_CLIENT_ID
        ).build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()


        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context
            )
            val userGoogleIdToken = handleSignInWithGoogle(result)

            return userGoogleIdToken

        } catch (e: GetCredentialException) {
            Log.e(CREDENTIALS_TAG, "signinWithGoogle GetCredentialException error : $e")
            e.printStackTrace()
            return null
        } catch (e: Exception){
            Log.e(CREDENTIALS_TAG, "signinWithGoogle error : $e")
            e.printStackTrace()
            return null
        }
    }

    private fun handleSignInWithGoogle(
        result: GetCredentialResponse
    ): String? {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken

//                    Log.d(CREDENTIALS_TAG, "user Google idToken: $idToken")
                    return idToken
                }
            }
        }

        return null
    }






    override suspend fun signInWithGoogleIntent(intent: Intent): UserData? {
        TODO("Not yet implemented")
    }

    override suspend fun getGoogleSignInIntentSender(): IntentSender? {
        TODO("Not yet implemented")
    }

//    override suspend fun signOut(
//        providerIdList: List<ProviderId>,
//        signOutResult: (isSignOutSuccess: Boolean) -> Unit
//    ) {
//        TODO("Not yet implemented")
//    }

    override fun reAuthenticateGoogleUser(
        intent: Intent,
        reAuthResult: (Boolean, Exception?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteAuthUser(deleteSuccess: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

}