package com.fitfit.core.model.dto

import com.fitfit.core.model.dto.basic.ErrorDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//response
@JsonClass(generateAdapter = true)
data class DeleteAccountResponseDTO(
    @Json(name = "error")val error: ErrorDto?
)