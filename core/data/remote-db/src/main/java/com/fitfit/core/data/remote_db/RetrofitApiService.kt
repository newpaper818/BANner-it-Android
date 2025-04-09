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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT

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
    // get preSigned url
    @POST("presigned-url")
    fun getPreSignedUrl(
        @Body getPreSignedUrlRequestDTO: GetPreSignedUrlRequestDTO
    ): Response<GetPreSignedUrlResponseDTO>

    @PUT
    fun uploadImageToS3(

    )

    @POST("reports/save")
    fun postBannerReport(
        @Header("Authorization") jwt: String,
        @Body reportBannerRequestBodyDTO: ReportBannerRequestBodyDTO
    ): Response<ReportBannerResponseDTO>






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