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
                Log.d(RETROFIT_TAG, "API-2 requestUserDataWithIdToken success")
                return userData
            }
            else {
                Log.e(RETROFIT_TAG, "API-2 requestUserDataWithIdToken jwt: $jwt")
                Log.e(RETROFIT_TAG, "API-2 requestUserDataWithIdToken userData: $userData")

                Log.e(RETROFIT_TAG, "API-2 requestUserDataWithIdToken result: $result")
                Log.e(RETROFIT_TAG, "API-2 requestUserDataWithIdToken headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "API-2 requestUserDataWithIdToken body: ${result.body()}")
                return null
            }
        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, "API-2 requestUserDataWithIdToken - $e")
            return null
        }
    }

    override suspend fun requestUserDataWithJwt(
        jwt: String
    ): Pair<String, UserData>? {
        try {
            val result = retrofitApiService.requestUserDataWithJwt(jwt = getJwtFormat(jwt))

            val newJwt = result.headers()["Authorization"]?.replace("Bearer ", "")
            val userData = result.body()?.userDataDTO?.toUserData(newJwt ?: "")

            if (
                result.code() == 200
                && result.body()?.error == null
                && newJwt != null
                && userData != null
            ) {
                Log.d(RETROFIT_TAG, "API-11 requestUserDataWithJwt success")
                return Pair(newJwt, userData)
            }
            else{
                Log.e(RETROFIT_TAG, "API-11 requestUserDataWithJwt jwt: $newJwt")
                Log.e(RETROFIT_TAG, "API-11 requestUserDataWithJwt userData: $userData")

                Log.d(RETROFIT_TAG, "API-11 requestUserDataWithJwt result = $result")
                Log.d(RETROFIT_TAG, "API-11 requestUserDataWithJwt headers = ${result.headers()}")
                Log.d(RETROFIT_TAG, "API-11 requestUserDataWithJwt body = ${result.body()}")
                return null
            }

        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, "API-11 requestUserDataWithJwt - $e")
            return null
        }
    }

    override suspend fun getPreSignedUrls(
        jwt: String,
        reportImages: List<ReportImage>
    ): List<ReportImage>? {
        try {
            val result = retrofitApiService.getPreSignedUrls(
                jwt = getJwtFormat(jwt),
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
                Log.d(RETROFIT_TAG, "API-13 getPreSignedUrls success")
                Log.d(RETROFIT_TAG, "API-13 getPreSignedUrls body: ${result.body()}")
                return newReportImages
            }
            else {
                Log.e(RETROFIT_TAG, "API-13 getPreSignedUrls result: $result")
                Log.e(RETROFIT_TAG, "API-13 getPreSignedUrls headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "API-13 getPreSignedUrls body: ${result.body()}")
                return null
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "API-13 getPreSignedUrls - $e")
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
                        Log.d(RETROFIT_TAG, "uploadImagesToS3 success - ${reportImage.s3Key}")

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
                jwt = getJwtFormat(jwt),
                reportBannerRequestBodyDTO = reportRecord.toReportRecordDTO()
            )

            if (
                result.code() == 200
                && result.body()?.error == null
            ) {
                Log.d(RETROFIT_TAG, "API-14 postBannerReport success")
                return true
            }
            else {
                Log.e(RETROFIT_TAG, "API-14 postBannerReport result: $result")
                Log.e(RETROFIT_TAG, "API-14 postBannerReport headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "API-14 postBannerReport body: ${result.body()}")
                return false
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "API-14 postBannerReport - $e")
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
                jwt = getJwtFormat(jwt)
            )

            if (
                result.code() == 200
                && result.body()?.error == null
            ) {
                Log.d(RETROFIT_TAG, "API-15 getAppUserReportRecords success")
                return result.body()?.reportRecordsDTO?.map { it.toReportRecord() }
            }
            else {
                Log.e(RETROFIT_TAG, "API-15 getAppUserReportRecords result: $result")
                Log.e(RETROFIT_TAG, "API-15 getAppUserReportRecords headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "API-15 getAppUserReportRecords body: ${result.body()}")
                return null
            }
        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, "API-15 getAppUserReportRecords - $e")
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
                Log.d(RETROFIT_TAG, "API-16 getAllReportRecords success")
                return result.body()?.reportRecordsDTO?.map { it.toReportRecord() }
            }
            else {
                Log.e(RETROFIT_TAG, "API-16 getAllReportRecords result: $result")
                Log.e(RETROFIT_TAG, "API-16 getAllReportRecords headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "API-16 getAllReportRecords body: ${result.body()}")
                return null
            }
        } catch (e: Exception) {
            Log.e(RETROFIT_TAG, "API-16 getAllReportRecords - $e")
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
                jwt = getJwtFormat(jwt),
                editBannerInfoRequestDTO = EditBannerInfoRequestDTO(
                    reportId = reportId,
                    bannerInfoIdWithStatusDTO = bannerInfo.map { it.toBannerInfoIdWithStatusDTO() }
                )
            )

            if (
                result.code() == 200
                && result.body()?.error == null
            ) {
                Log.d(RETROFIT_TAG, "API-4 editBannerInfo success")
                return true
            }
            else {
                Log.e(RETROFIT_TAG, "API-4 editBannerInfo result: $result")
                Log.e(RETROFIT_TAG, "API-4 editBannerInfo headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "API-4 editBannerInfo body: ${result.body()}")
                return false
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "API-4 editBannerInfo - $e")
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
                jwt = getJwtFormat(jwt),
                updateUserDataRequestDTO = UpdateUserDataRequestDTO(
                    updateUserDataDTO = UpdateUserDataDTO(
                        userName = userName,
//                        role = userRole.name
                    )
                )
            )

            if (
                result.code() == 200
                && result.body()?.error == null
            ) {
                Log.d(RETROFIT_TAG, "API-8 updateUserData success")
                Log.d(RETROFIT_TAG, "API-8 updateUserData body: ${result.body()}")
                return true
            }
            else {
                Log.e(RETROFIT_TAG, "API-8 updateUserData result: $result")
                Log.e(RETROFIT_TAG, "API-8 updateUserData headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "API-8 updateUserData body: ${result.body()}")
                return false
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "API-8 updateUserData - $e")
            return false
        }
    }

    override suspend fun deleteAccount(
        jwt: String
    ): Boolean {
        try {
            val result = retrofitApiService.deleteAccount(
                jwt = getJwtFormat(jwt)
            )

            if (
                result.code() == 200
                && result.body()?.error == null
            ) {
                Log.d(RETROFIT_TAG, "API-3 deleteAccount success")
                return true
            }
            else {
                Log.e(RETROFIT_TAG, "API-3 deleteAccount result: $result")
                Log.e(RETROFIT_TAG, "API-3 deleteAccount headers: ${result.headers()}")
                Log.e(RETROFIT_TAG, "API-3 deleteAccount body: ${result.body()}")
                return false
            }

        } catch (e: Exception){
            Log.e(RETROFIT_TAG, "API-3 deleteAccount - $e")
            return false
        }
    }
}

private fun getJwtFormat(
    jwt: String
): String{
    return "Bearer $jwt"
}