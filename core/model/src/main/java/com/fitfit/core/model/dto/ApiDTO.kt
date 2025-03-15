package com.fitfit.core.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(
    @Json(name = "success")val success: Boolean,
    @Json(name = "jwt_token")val jwt: String?,
    @Json(name = "userData")val userData: UserDataDTO?,
    @Json(name = "error")val error: String?
)

@JsonClass(generateAdapter = true)
data class UserDataDTO(
    @Json(name = "name")val name: String,
    @Json(name = "email")val email: String,
    @Json(name = "profileImagePath")val profileImagePath: String,
)