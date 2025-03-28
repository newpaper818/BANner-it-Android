package com.fitfit.core.data.remote_db

import android.content.Context
import android.util.Log
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.dto.IdTokenRequestDTO
import com.fitfit.core.model.dto.toReportLogDTO
import com.fitfit.core.model.report.ReportLog
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

private const val RETROFIT_TAG = "Retrofit"

class RetrofitApi @Inject constructor(
    @ApplicationContext private val context: Context,
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

    override suspend fun postReportBanner(
        jwt: String,
        userId: Int,
        reportLog: ReportLog
    ): Boolean {
        try {
            val result = retrofitApiService.postReportBanner(
                jwt = jwt,
                reportBannerRequestBodyDTO = reportLog.toReportLogDTO(
//                    userId = userId
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

    //TODO delete after test - use above
    override suspend fun sendTestImage(
        jwt: String,
        userId: Int,
        reportLog: ReportLog
    ): Boolean {
        val imageFile = File(context.filesDir, reportLog.images[0])
        val request = RequestBody.create(
            MediaType.parse("image/jpg"),
            imageFile
        )

        val userIdReq = RequestBody.create(
            MediaType.parse("application/json"),
            "{"
                    + "\"user_id\" : \"$userId\""
            + "}"
        )

        val photo = MultipartBody.Part.createFormData(
            "photo", imageFile.name, request
        )

        try {
            val result = retrofitApiService.postTestPhoto(
                photo = photo,
                userId = userIdReq
            )
            val error = result.body()?.error

            Log.d(RETROFIT_TAG, "result = $result")

            if (error == null)
                return true
            else
                return false

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, e.toString())
            return false
        }

    }

    override suspend fun getAppUserReportLogs(
        jwt: String
    ): List<ReportLog>? {
        try {
            val result = retrofitApiService.getAppUserReportLogs(
                jwt = jwt
            )

            //result
            val code = result.code()
            val error = result.body()?.error
            val reportLogs = result.body()?.reportLogsDTO?.map { it.toReportLog() }

            if (
                code == 200
                && error == null
            ) {
                return reportLogs
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

    override suspend fun getAllReportLogs(

    ): List<ReportLog>? {
        try {
            val result = retrofitApiService.getAllReportLogs()

            //result
            val code = result.code()
            val error = result.body()?.error
            val reportLogs = result.body()?.reportLogsDTO?.map { it.toReportLog() }

            if (
                code == 200
                && error == null
            ) {
                return reportLogs
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
}