package com.fitfit.core.data.remote_db

import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.dto.IdTokenRequestDTO
import com.fitfit.core.model.dto.RequestBodyReportDTO
import com.fitfit.core.model.dto.ResponseBodyReportDTO
import com.fitfit.core.model.dto.SignInResponseDTO
import com.fitfit.core.model.dto.TestBodyReportDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
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






    @POST("")
    fun postReportLog(
        @Header("Authorization") jwt: String,
        @Body requestBodyReportDTO: RequestBodyReportDTO
    ): Response<ResponseBodyReportDTO>


    @Multipart
    @POST("")
    fun postTestPhoto(
        @Part photo: MultipartBody.Part,
        @Part("user_id") userId: RequestBody
    ): Response<TestBodyReportDTO>







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