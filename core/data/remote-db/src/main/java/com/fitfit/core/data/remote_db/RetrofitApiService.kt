package com.fitfit.core.data.remote_db

import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.dto.EditBannerInfoRequestDTO
import com.fitfit.core.model.dto.EditBannerInfoResponseDTO
import com.fitfit.core.model.dto.GetReportRecordResponseDTO
import com.fitfit.core.model.dto.IdTokenRequestDTO
import com.fitfit.core.model.dto.ReportBannerRequestBodyDTO
import com.fitfit.core.model.dto.ReportBannerResponseDTO
import com.fitfit.core.model.dto.SignInResponseDTO
import com.fitfit.core.model.dto.TestReportBannerResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitApiService {

    @POST("oauth/validate")
    suspend fun requestUserDataWithIdToken(
        @Body idTokenRequestDTO: IdTokenRequestDTO
    ): Response<SignInResponseDTO>

    @POST("")
    fun requestUserDataWithJwt(
        @Header("Authorization") jwt: String,
    ): Response<SignInResponseDTO>






    @POST("reports/save")
    fun postBannerReport(
        @Header("Authorization") jwt: String,
        @Body reportBannerRequestBodyDTO: ReportBannerRequestBodyDTO
    ): Response<ReportBannerResponseDTO>


    //TODO test FIXME several photos
    @Multipart
    @POST("")
    fun postTestPhoto(
        @Part photo: MultipartBody.Part,
        @Part("test") userId: RequestBody
    ): Response<TestReportBannerResponseDTO>


    @GET("reports/logs")
    fun getAppUserReportRecords(
        @Header("Authorization") jwt: String,
    ): Response<GetReportRecordResponseDTO>


    @GET("reports")
    fun getAllReportRecords(

    ): Response<GetReportRecordResponseDTO>

    @PATCH("banners/update")
    fun editBannerStatus(
        @Header("Authorization") jwt: String,
        @Body editBannerInfoRequestDTO: EditBannerInfoRequestDTO
    ): Response<EditBannerInfoResponseDTO>




    @POST("")
    fun signUp(
        @Header("jwt") jwt: String,
    ): UserData


    @GET("")
    fun getUserInfo(
        @Header("jwt") jwt: String,
    ): UserData


    @GET("something")
    fun someApi(
        @Header("jwt") jwt: String,
    ): UserData

}