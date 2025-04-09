package com.fitfit.core.data.remote_db

import com.fitfit.core.model.dto.DeleteAccountResponseDTO
import com.fitfit.core.model.dto.EditBannerInfoRequestDTO
import com.fitfit.core.model.dto.EditBannerInfoResponseDTO
import com.fitfit.core.model.dto.GetPreSignedUrlRequestDTO
import com.fitfit.core.model.dto.GetReportRecordResponseDTO
import com.fitfit.core.model.dto.IdTokenRequestDTO
import com.fitfit.core.model.dto.ReportBannerRequestBodyDTO
import com.fitfit.core.model.dto.ReportBannerResponseDTO
import com.fitfit.core.model.dto.SignInResponseDTO
import com.fitfit.core.model.dto.GetPreSignedUrlResponseDTO
import com.fitfit.core.model.dto.UpdateUserDataRequestDTO
import com.fitfit.core.model.dto.UpdateUserDataResponseDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Url

interface RetrofitApiService {

    //sign in --------------------------------------------------------------------------------------

    /**API-2*/
    @POST("oauth/validate")
    suspend fun requestUserDataWithIdToken(
        @Body idTokenRequestDTO: IdTokenRequestDTO
    ): Response<SignInResponseDTO>

    /**API-11*/
    @POST("oauth/refresh")
    fun requestUserDataWithJwt(
        @Header("Authorization") jwt: String,
    ): Response<SignInResponseDTO>





    //report banner --------------------------------------------------------------------------------
    // get preSigned url
    /**API-13*/
    @POST("presigned-url")
    fun getPreSignedUrl(
        @Body getPreSignedUrlRequestDTO: GetPreSignedUrlRequestDTO
    ): Response<GetPreSignedUrlResponseDTO>

    @PUT
    fun uploadImageToS3(
        @Url preSignedUrl: String,
        @Part imageFile: MultipartBody.Part
    ): Response<Unit>

    /**API-14*/
    @POST("reports/save")
    fun postBannerReport(
        @Header("Authorization") jwt: String,
        @Body reportBannerRequestBodyDTO: ReportBannerRequestBodyDTO
    ): Response<ReportBannerResponseDTO>






    //get report records ---------------------------------------------------------------------------
    /**API-15*/
    @GET("reports/logs/me")
    fun getAppUserReportRecords(
        @Header("Authorization") jwt: String,
    ): Response<GetReportRecordResponseDTO>

    /**API-16*/
    @GET("reports/logs")
    fun getAllReportRecords(

    ): Response<GetReportRecordResponseDTO>





    //edit report records --------------------------------------------------------------------------
    /**API-4*/
    @PATCH("banners/update")
    fun editBannerStatus(
        @Header("Authorization") jwt: String,
        @Body editBannerInfoRequestDTO: EditBannerInfoRequestDTO
    ): Response<EditBannerInfoResponseDTO>





    //account --------------------------------------------------------------------------------------
    /**API-8*/
    @PATCH("users/update")
    fun updateUserData(
        @Header("Authorization") jwt: String,
        @Body updateUserDataRequestDTO: UpdateUserDataRequestDTO
    ): Response<UpdateUserDataResponseDTO>

    /**
     * ⚠️ DELETE ACCOUNT ⚠️
     */
    /**API-3*/
    @DELETE("users/delete")
    fun deleteAccount(
        @Header("Authorization") jwt: String,
    ): Response<DeleteAccountResponseDTO>
}