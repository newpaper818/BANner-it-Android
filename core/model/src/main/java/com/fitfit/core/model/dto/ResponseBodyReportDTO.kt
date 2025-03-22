package com.fitfit.core.model.dto

import com.fitfit.core.model.report.Address
import com.fitfit.core.model.report.ReportLog
import com.fitfit.core.model.report.ReportStatus
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

fun ReportLog.toReportLogDTO(
    userId: Int
): RequestBodyReportDTO {
    return RequestBodyReportDTO(
        userId = userId,
        requestReportLogDTO = RequestReportLogDTO(
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
data class RequestBodyReportDTO(
    @Json(name = "user_id")val userId: Int,
    @Json(name = "report_log")val requestReportLogDTO: RequestReportLogDTO,
)

@JsonClass(generateAdapter = true)
data class RequestReportLogDTO(
    @Json(name = "location")val locationDTO: LocationDTO,
    @Json(name = "address")val addressDTO: AddressDTO,
    @Json(name = "content")val content: String,
)



//response
@JsonClass(generateAdapter = true)
data class ResponseBodyReportDTO(
    @Json(name = "report_log")val responseReportLogDTO: ResponseReportLogDTO?,
    @Json(name = "error")val error: ErrorDto?
)

@JsonClass(generateAdapter = true)
data class ResponseReportLogDTO(
    @Json(name = "report_id") val reportId: Int,
    @Json(name = "report_time") val reportTime: String,
    @Json(name = "status") val status: String,
    @Json(name = "created_user_id") val createdUserId: Int,

    @Json(name = "images") val images: List<String>,
    @Json(name = "location")val locationDTO: LocationDTO,
    @Json(name = "address")val addressDTO: AddressDTO,
    @Json(name = "content")val content: String,
){
    fun toReportLog(): ReportLog {
        return ReportLog(
            reportId = reportId,
            reportTime = ZonedDateTime.parse(reportTime),
            status = ReportStatus.valueOf(status),
            createdUserId = createdUserId,
            images = images,
            location = locationDTO.toLatLng(),
            address = addressDTO.toAddress(),
            content = content
        )
    }
}


//
@JsonClass(generateAdapter = true)
data class LocationDTO(
    @Json(name = "latitude")val latitude: Double,
    @Json(name = "longitude")val longitude: Double,
){
    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }

}

@JsonClass(generateAdapter = true)
data class AddressDTO(
    @Json(name = "address1")val address1: String,
    @Json(name = "address2")val address2: String,
    @Json(name = "address3")val address3: String,
){
    fun toAddress(): Address {
        return Address(
            address1 = address1,
            address2 = address2,
            address3 = address3
        )
    }
}