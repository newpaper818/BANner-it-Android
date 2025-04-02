package com.fitfit.core.data.remote_db

import android.content.Context
import android.util.Log
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.dto.EditBannerInfoRequestDTO
import com.fitfit.core.model.dto.IdTokenRequestDTO
import com.fitfit.core.model.dto.UpdateUserDataDTO
import com.fitfit.core.model.dto.UpdateUserDataRequestDTO
import com.fitfit.core.model.dto.toBannerInfoIdWithStatusDTO
import com.fitfit.core.model.dto.toReportRecordDTO
import com.fitfit.core.model.enums.UserRole
import com.fitfit.core.model.report.BannerInfo
import com.fitfit.core.model.report.ReportRecord
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
                Log.e(RETROFIT_TAG, "requestUserDataWithIdToken result: $result")
                Log.e(RETROFIT_TAG, "requestUserDataWithIdToken headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "requestUserDataWithIdToken body: ${result.body()}")
                return null
            }
        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, "requestUserDataWithIdToken - $e")
            return null
        }
    }

    override suspend fun requestUserDataWithJwt(
        jwt: String
    ): Pair<String, UserData>? {
        try {
            val result = retrofitApiService.requestUserDataWithJwt(jwt = jwt)
            Log.d(RETROFIT_TAG, "requestUserDataWithJwt result = $result")
            Log.d(RETROFIT_TAG, "requestUserDataWithJwt headers = ${result.headers()}")
            Log.d(RETROFIT_TAG, "requestUserDataWithJwt body = ${result.body()}")

            //TODO: get jwt, userData
            return null

        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, "requestUserDataWithJwt - $e")
            return null
        }
    }

    override suspend fun postBannerReport(
        jwt: String,
        userId: Int,
        reportRecord: ReportRecord
    ): Boolean {
        try {
            val result = retrofitApiService.postBannerReport(
                jwt = jwt,
                reportBannerRequestBodyDTO = reportRecord.toReportRecordDTO(
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
                Log.e(RETROFIT_TAG, "postBannerReport result: $result")
                Log.e(RETROFIT_TAG, "postBannerReport headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "postBannerReport body: ${result.body()}")
                return false
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "postBannerReport - $e")
            return false
        }
    }

    //TODO delete after test - use above
    override suspend fun sendTestImage(
        jwt: String,
        userId: Int,
        reportRecord: ReportRecord
    ): Boolean {
        val imageFile = File(context.filesDir, reportRecord.images[0])
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

    override suspend fun getAppUserReportRecords(
        jwt: String
    ): List<ReportRecord>? {
        try {
            val result = retrofitApiService.getAppUserReportRecords(
                jwt = jwt
            )

            //result
            val code = result.code()
            val error = result.body()?.error
            val reportRecords = result.body()?.reportRecordsDTO?.map { it.toReportRecord() }

            if (
                code == 200
                && error == null
            ) {
                return reportRecords
            }
            else {
                Log.e(RETROFIT_TAG, "getAppUserReportRecords result: $result")
                Log.e(RETROFIT_TAG, "getAppUserReportRecords headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "getAppUserReportRecords body: ${result.body()}")
                return null
            }
        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, "getAppUserReportRecords - $e")
            return null
        }
    }

    override suspend fun getAllReportRecords(

    ): List<ReportRecord>? {
        try {
            val result = retrofitApiService.getAllReportRecords()

            //result
            val code = result.code()
            val error = result.body()?.error
            val reportRecords = result.body()?.reportRecordsDTO?.map { it.toReportRecord() }

            if (
                code == 200
                && error == null
            ) {
                return reportRecords
            }
            else {
                Log.e(RETROFIT_TAG, "getAllReportRecords result: $result")
                Log.e(RETROFIT_TAG, "getAllReportRecords headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "getAllReportRecords body: ${result.body()}")
                return null
            }
        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, "getAllReportRecords - $e")
            return null
        }
    }

    override suspend fun editBannerInfo(
        jwt: String,
        reportId: Int,
        bannerInfo: List<BannerInfo>
    ): Boolean {
        try {
            val result = retrofitApiService.editBannerStatus(
                jwt = jwt,
                editBannerInfoRequestDTO = EditBannerInfoRequestDTO(
                    reportId = reportId,
                    bannerInfoIdWithStatusDTO = bannerInfo.map { it.toBannerInfoIdWithStatusDTO() }
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
                Log.e(RETROFIT_TAG, "editBannerInfo result: $result")
                Log.e(RETROFIT_TAG, "editBannerInfo headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "editBannerInfo body: ${result.body()}")
                return false
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "editBannerInfo - $e")
            return false
        }
    }

    override suspend fun updateUserData(
        jwt: String,
        userName: String,
        userRole: UserRole
    ): Boolean {
        try {
            val result = retrofitApiService.updateUserData(
                jwt = jwt,
                updateUserDataRequestDTO = UpdateUserDataRequestDTO(
                    updateUserDataDTO = UpdateUserDataDTO(
                        userName = userName,
                        role = userRole.name
                    )
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
                Log.e(RETROFIT_TAG, "updateUserData result: $result")
                Log.e(RETROFIT_TAG, "updateUserData headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "updateUserData body: ${result.body()}")
                return false
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "updateUserData - $e")
            return false
        }
    }
}