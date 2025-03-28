package com.fitfit.core.model.dto

import com.fitfit.core.model.dto.basic.ErrorDto
import com.fitfit.core.model.dto.basic.UserDataDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//request
@JsonClass(generateAdapter = true)
data class IdTokenRequestDTO(
    @Json(name = "id_token")val idToken: String,
)


//response
@JsonClass(generateAdapter = true)
data class SignInResponseDTO(
    @Json(name = "user_data")val userDataDTO: UserDataDTO?,
    @Json(name = "error")val error: ErrorDto?
)