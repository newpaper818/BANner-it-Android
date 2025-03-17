package com.fitfit.core.data.remote_db

import android.util.Log
import com.fitfit.core.model.data.UserData
import javax.inject.Inject

private const val RETROFIT_TAG = "Retrofit"

class RetrofitApi @Inject constructor(
    private val retrofitApiService: RetrofitApiService
): DbRemoteDataSource {

    override suspend fun requestUserDataWithIdToken(
        userGoogleIdToken: String,
    ): Pair<String, UserData>? {
        try {
            val result = retrofitApiService.requestUserDataWithIdToken(idToken = userGoogleIdToken)
            Log.d(RETROFIT_TAG, "result = $result")
            Log.d(RETROFIT_TAG, "headers = ${result.headers()}")
            Log.d(RETROFIT_TAG, "body = ${result.body()}")

            //TODO: test this get jwt
            val jwt = result.headers()["Authentication"]?.let {
                if (it.startsWith("Bearer ")){
                    it.substringAfter("Bearer ")
                }
                else null
            }
            val userData = result.body()?.userDataDTO?.toUserData()

            Log.d(RETROFIT_TAG, "jwt = $jwt")
            Log.d(RETROFIT_TAG, "userData = $userData")

            return if (jwt !== null && userData !== null){
                    Pair(jwt, userData)
                } else {
                    null
                }


        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, e.toString())
            return null
        }
    }

    override suspend fun requestUserDataWithJwt(
        jwt: String
    ): Pair<String, UserData>? {
        try {
            val result = retrofitApiService.requestUserDataWithJwt(jwt = jwt)
            Log.d(RETROFIT_TAG, "result = $result")
            Log.d(RETROFIT_TAG, "headers = ${result.headers()}")
            Log.d(RETROFIT_TAG, "body = ${result.body()}")

            //TODO: get jwt, userData
            return null

        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, e.toString())
            return null
        }
    }
}