package com.fitfit.core.model.dto

import com.fitfit.core.model.dto.basic.AddressDTO
import com.fitfit.core.model.dto.basic.ErrorDto
import com.fitfit.core.model.dto.basic.LocationDTO
import com.fitfit.core.model.report.ReportRecord
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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