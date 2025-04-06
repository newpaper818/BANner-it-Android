package com.fitfit.core.data.remote_db

import com.fitfit.core.model.dto.DeleteAccountResponseDTO
import com.fitfit.core.model.dto.EditBannerInfoRequestDTO
import com.fitfit.core.model.dto.EditBannerInfoResponseDTO
import com.fitfit.core.model.dto.GetReportRecordResponseDTO
import com.fitfit.core.model.dto.IdTokenRequestDTO
import com.fitfit.core.model.dto.ReportBannerRequestBodyDTO
import com.fitfit.core.model.dto.ReportBannerResponseDTO
import com.fitfit.core.model.dto.SignInResponseDTO
import com.fitfit.core.model.dto.TestReportBannerResponseDTO
import com.fitfit.core.model.dto.UpdateUserDataRequestDTO
import com.fitfit.core.model.dto.UpdateUserDataResponseDTO
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

    //sign in --------------------------------------------------------------------------------------
    @POST("oauth/validate")
    suspend fun requestUserDataWithIdToken(
        @Body idTokenRequestDTO: IdTokenRequestDTO
    ): Response<SignInResponseDTO>

    @POST("")
    fun requestUserDataWithJwt(
        @Header("Authorization") jwt: String,
    ): Response<SignInResponseDTO>





    //report banner --------------------------------------------------------------------------------
    @POST("reports/save")
    fun postBannerReport(
        @Header("Authorization") jwt: String,
        @Body reportBannerRequestBodyDTO: ReportBannerRequestBodyDTO
    ): Response<ReportBannerResponseDTO>

    //TODO test
    @Multipart
    @POST("")
    fun postTestPhoto(
        @Part photos: List<MultipartBody.Part>,
        @Part("test") userId: RequestBody
    ): Response<TestReportBannerResponseDTO>





    //get report records ---------------------------------------------------------------------------
    @GET("reports/logs")
    fun getAppUserReportRecords(
        @Header("Authorization") jwt: String,
    ): Response<GetReportRecordResponseDTO>

    @GET("reports")
    fun getAllReportRecords(

    ): Response<GetReportRecordResponseDTO>





    //edit report records --------------------------------------------------------------------------
    @PATCH("banners/update")
    fun editBannerStatus(
        @Header("Authorization") jwt: String,
        @Body editBannerInfoRequestDTO: EditBannerInfoRequestDTO
    ): Response<EditBannerInfoResponseDTO>





    //account --------------------------------------------------------------------------------------
    @PATCH("users/update")
    fun updateUserData(
        @Header("Authorization") jwt: String,
        @Body updateUserDataRequestDTO: UpdateUserDataRequestDTO
    ): Response<UpdateUserDataResponseDTO>

    /**
     * ⚠️ DELETE ACCOUNT ⚠️
     */
    @PATCH("users/delete")
    fun deleteAccount(
        @Header("Authorization") jwt: String,
    ): Response<DeleteAccountResponseDTO>
}