package com.fitfit.core.data.remote_db

import android.content.Context
import android.util.Log
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.dto.EditBannerInfoRequestDTO
import com.fitfit.core.model.dto.GetPreSignedUrlRequestDTO
import com.fitfit.core.model.dto.IdTokenRequestDTO
import com.fitfit.core.model.dto.UpdateUserDataDTO
import com.fitfit.core.model.dto.UpdateUserDataRequestDTO
import com.fitfit.core.model.dto.toBannerInfoIdWithStatusDTO
import com.fitfit.core.model.dto.toReportRecordDTO
import com.fitfit.core.model.enums.UserRole
import com.fitfit.core.model.report.BannerInfo
import com.fitfit.core.model.report.ReportImage
import com.fitfit.core.model.report.ReportRecord
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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

            //data
            val jwt = result.headers()["Authorization"]?.replace("Bearer ", "")
            val userData = result.body()?.userDataDTO?.toUserData(jwt ?: "")

            if (
                result.code() == 200
                && result.body()?.error == null
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

    override suspend fun getPreSignedUrl(
        reportImages: List<ReportImage>
    ): List<ReportImage>? {
        try {
            val result = retrofitApiService.getPreSignedUrl(
                getPreSignedUrlRequestDTO = GetPreSignedUrlRequestDTO(
                    imageFileNames = reportImages.mapNotNull { it.fileName }
                )
            )

            if (
                result.code() == 200
                && result.body()?.error == null
            ) {
                val newReportImages = result.body()?.keyAndUrls?.mapIndexed { index, keyAndUrlDTO ->
                    keyAndUrlDTO.toReportImage(reportImages[index])
                }
                return newReportImages
            }
            else {
                Log.e(RETROFIT_TAG, "getPreSignedUrl result: $result")
                Log.e(RETROFIT_TAG, "getPreSignedUrl headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "getPreSignedUrl body: ${result.body()}")
                return null
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "getPreSignedUrl - $e")
            return null
        }
    }

    override suspend fun uploadImagesToS3(
        reportImages: List<ReportImage>
    ): Boolean {
        try{
            reportImages.forEach { reportImage ->
                val preSignedUrl = reportImage.preSignedUrl

                val imageFile = reportImage.fileName?.let { File(context.filesDir, it) }
                val requestFile = imageFile?.asRequestBody("image/*".toMediaTypeOrNull())

                val multipartBody = requestFile?.let {
                    MultipartBody.Part.createFormData("image", imageFile.name, it)
                }

                if (preSignedUrl != null && multipartBody != null){
                    val result = retrofitApiService.uploadImageToS3(
                        preSignedUrl = preSignedUrl,
                        imageFile = multipartBody
                    )

                    if (
                        result.code() == 200
                    ) {

                    }
                    else {
                        Log.e(RETROFIT_TAG, "uploadImagesToS3 result: $result")
                        Log.e(RETROFIT_TAG, "uploadImagesToS3 headers: ${result.headers()}")
                        Log.e(RETROFIT_TAG, "uploadImagesToS3 body: ${result.body()}")
                        return false
                    }
                }
                else {
                    Log.e(RETROFIT_TAG, "uploadImagesToS3 - preSignedUrl == null or multipartBody == null")
                    return false
                }
            }
        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "uploadImagesToS3 - $e")
            return false
        }

        return true
    }

    override suspend fun postBannerReport(
        jwt: String,
        userId: Int,
        reportRecord: ReportRecord
    ): Boolean {
        try {
            val result = retrofitApiService.postBannerReport(
                jwt = jwt,
                reportBannerRequestBodyDTO = reportRecord.toReportRecordDTO()
            )

            if (
                result.code() == 200
                && result.body()?.error == null
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



    //
//    override suspend fun sendTestImage(
//        jwt: String,
//        userId: Int,
//        reportRecord: ReportRecord
//    ): Boolean {
//        val requestDTO = TestRequestDTO(test = userId)
//        val jsonPart = createJsonPartFromDto(requestDTO)

//        val userIdReq = RequestBody.create(
//            MediaType.parse("application/json"),
//            "{"
//                    + "\"test\" : \"$userId\""
//            + "}"
//        )

//        val photos = reportRecord.images.map {
//            val imageFile = File(context.filesDir, it)
//            val requestFile = imageFile
//                .asRequestBody("image/jpg".toMediaTypeOrNull())
//
//            MultipartBody.Part.createFormData(
//                "image", imageFile.name, requestFile
//            )
//        }
//
//        try {
//            val result = retrofitApiService.postTestPhoto(
//                photos = photos,
//                userId = jsonPart
//            )
//            val error = result.body()?.error
//
//            Log.d(RETROFIT_TAG, "result = $result")
//
//            if (error == null)
//                return true
//            else
//                return false
//
//        } catch (e: Exception){
//            Log.e(RETROFIT_TAG, e.toString())
//            return false
//        }
//
//    }

    override suspend fun getAppUserReportRecords(
        jwt: String
    ): List<ReportRecord>? {
        try {
            val result = retrofitApiService.getAppUserReportRecords(
                jwt = jwt
            )

            if (
                result.code() == 200
                && result.body()?.error == null
            ) {
                return result.body()?.reportRecordsDTO?.map { it.toReportRecord() }
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

            if (
                result.code() == 200
                && result.body()?.error == null
            ) {
                return result.body()?.reportRecordsDTO?.map { it.toReportRecord() }
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

            if (
                result.code() == 200
                && result.body()?.error == null
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

            if (
                result.code() == 200
                && result.body()?.error == null
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

    override suspend fun deleteAccount(
        jwt: String
    ): Boolean {
        try {
            val result = retrofitApiService.deleteAccount(
                jwt = jwt
            )

            if (
                result.code() == 200
                && result.body()?.error == null
            ) {
                return true
            }
            else {
                Log.e(RETROFIT_TAG, "deleteAccount result: $result")
                Log.e(RETROFIT_TAG, "deleteAccount headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "deleteAccount body: ${result.body()}")
                return false
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "deleteAccount - $e")
            return false
        }
    }
}