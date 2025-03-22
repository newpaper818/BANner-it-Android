package com.fitfit.core.data.remote_db

import android.util.Log
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.dto.IdTokenRequestDTO
import com.fitfit.core.model.dto.toReportLogDTO
import com.fitfit.core.model.report.ReportLog
import javax.inject.Inject

private const val RETROFIT_TAG = "Retrofit"

class RetrofitApi @Inject constructor(
    private val retrofitApiService: RetrofitApiService
): DbRemoteDataSource {

    override suspend fun requestUserDataWithIdToken(
        userGoogleIdToken: String,
    ): UserData? {
        try {
            val result = retrofitApiService.requestUserDataWithIdToken(
                idTokenRequestDTO = IdTokenRequestDTO(idToken = userGoogleIdToken)
            )

            //result
            val code = result.code()
            val header = result.headers()
            val error = result.body()?.error

            //data
            val jwt = header["Authorization"]?.replace("Bearer ", "")
            val userData = result.body()?.userDataDTO?.toUserData(jwt ?: "")

            if (
                code == 200
                && error == null
                && jwt != null
                && userData != null
            ) {
                return userData
            }
            else {
                Log.e(RETROFIT_TAG, "result: $result")
                Log.e(RETROFIT_TAG, "headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "body: ${result.body()}")
                return null
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

    override suspend fun postReportLog(
        jwt: String,
        userId: Int,
        reportLog: ReportLog
    ): Boolean {
        try {
            val result = retrofitApiService.postReportLog(
                jwt = jwt,
                requestBodyReportDTO = reportLog.toReportLogDTO(
                    userId = userId
                )
            )

            val code = result.code()
            val error = result.body()?.error

            if (
                code == 200
                && error == null
            ) {
                return true
            }
            else {
                Log.e(RETROFIT_TAG, "result: $result")
                Log.e(RETROFIT_TAG, "headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "body: ${result.body()}")
                return false
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, e.toString())
            return false
        }
    }
}