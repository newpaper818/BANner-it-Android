package com.fitfit.core.model.dto

import com.fitfit.core.model.dto.basic.AddressDTO
import com.fitfit.core.model.dto.basic.ErrorDto
import com.fitfit.core.model.dto.basic.LocationDTO
import com.fitfit.core.model.report.ReportRecord
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun ReportRecord.toReportRecordDTO(

): ReportBannerRequestBodyDTO {
    return ReportBannerRequestBodyDTO(
        reportBannerRequestDTO = ReportBannerRequestDTO(
            locationDTO = LocationDTO(
                latitude = location.latitude,
                longitude = location.longitude,
            ),
            addressDTO = AddressDTO(
                address1 = "",
                address2 = "",
                address3 = "",
            ),
            content = content
        )
    )
}

//TODO test
@JsonClass(generateAdapter = true)
data class TestRequestDTO(
    @Json(name = "test") val test: Int,
)

fun createJsonPartFromDto(
    dto: TestRequestDTO
): RequestBody {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(TestRequestDTO::class.java)
    val json = adapter.toJson(dto)
    return json.toRequestBody("application/json; charset=utf-8".toMediaType())
}






//request
@JsonClass(generateAdapter = true)
data class ReportBannerRequestBodyDTO(
    @Json(name = "report_log")val reportBannerRequestDTO: ReportBannerRequestDTO,
)

@JsonClass(generateAdapter = true)
data class ReportBannerRequestDTO(
    @Json(name = "location")val locationDTO: LocationDTO,
    @Json(name = "address")val addressDTO: AddressDTO,
    @Json(name = "content")val content: String,
)



//response
@JsonClass(generateAdapter = true)
data class TestReportBannerResponseDTO(
    @Json(name = "error")val error: ErrorDto?
)

@JsonClass(generateAdapter = true)
data class ReportBannerResponseDTO(
    @Json(name = "error")val error: ErrorDto?
)