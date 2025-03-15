package com.fitfit.core.data.credentials

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.ProviderId

interface AuthRemoteDataSource{
//    fun getCurrentUser(): FirebaseUser?

    /**
     * sign in with google,
     * and get idToken
     *
     * @param context
     * @return idToken or null(error)
     */
    suspend fun signinWithGoogle(
        context: Context,
    ): String?

    suspend fun signInWithGoogleIntent(
        intent: Intent,
    ): UserData?


    suspend fun getGoogleSignInIntentSender(): IntentSender?


    suspend fun signOut(
        providerIdList: List<ProviderId>,
        signOutResult: (isSignOutSuccess: Boolean) -> Unit
    )

    fun reAuthenticateGoogleUser(
        intent: Intent,
        reAuthResult: (Boolean, Exception?) -> Unit
    )

    fun deleteAuthUser(
        deleteSuccess: (Boolean) -> Unit
    )
}