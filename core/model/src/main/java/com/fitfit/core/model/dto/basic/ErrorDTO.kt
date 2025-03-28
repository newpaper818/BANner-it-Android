package com.fitfit.core.model.dto.basic

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorDto(
    //40100	승인되지 않은 접근입니다
    //50000	서버 내부 오류입니다
    @Json(name = "code")    val code: String,
    @Json(name = "message") val message: String,
)