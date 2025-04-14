package com.fitfit.core.model.dto

import com.fitfit.core.model.dto.basic.ErrorDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//request
@JsonClass(generateAdapter = true)
data class UpdateUserDataRequestDTO(
//    @Json(name = "user_data")val updateUserDataDTO: UpdateUserDataDTO,
    @Json(name = "name")val userName: String,
)

@JsonClass(generateAdapter = true)
data class UpdateUserDataDTO(
    @Json(name = "name")val userName: String,
//    @Json(name = "role")val role: String,
)

//response
@JsonClass(generateAdapter = true)
data class UpdateUserDataResponseDTO(
    @Json(name = "data")val data: String?,
    @Json(name = "error")val error: ErrorDto?
)