package com.fitfit.core.data.remote_db

import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.dto.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitApiService {

    @POST("oauth/validate")
    suspend fun requestUserDataWithIdToken(
        @Body idTokenRequestDTO: IdTokenRequestDTO
    ): Response<SignInResponseDTO>

    @POST("")
    fun requestUserDataWithJwt(
        @Header("Authorization") jwt: String,
    ): Response<SignInResponseDTO>






//    @POST("")
//    fun postReportLog(
//        @Header("Authorization") jwt: String,
//        @Body reportLogDTO: ReportLogDTO
//    ): Response<>










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