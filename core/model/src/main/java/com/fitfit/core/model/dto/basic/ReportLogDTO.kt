package com.fitfit.core.model.dto.basic

import com.fitfit.core.model.report.Address
import com.fitfit.core.model.report.BannerInfo
import com.fitfit.core.model.report.ReportLog
import com.fitfit.core.model.report.ReportStatus
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
data class ReportLogDTO(
    @Json(name = "report_id") val reportId: Int,
    @Json(name = "report_time") val reportTime: String,
    @Json(name = "status") val status: String,
    @Json(name = "created_user_id") val createdUserId: Int,

    @Json(name = "images") val images: List<String>,
    @Json(name = "location")val locationDTO: LocationDTO,
    @Json(name = "address")val addressDTO: AddressDTO,
    @Json(name = "content")val content: String,
    @Json(name = "banner_info")val bannerInfoDTO: List<BannerInfoDTO>,
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
            content = content,
            bannerInfo = bannerInfoDTO.map { it.toBannerInfo() }
        )
    }
}



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

@JsonClass(generateAdapter = true)
data class BannerInfoDTO(
    @Json(name = "banner_id")val bannerId: Int,
    @Json(name = "status") val status: String,
    @Json(name = "company_name")val companyName: String,
    @Json(name = "phone_number")val phoneNumber: String,
){
    fun toBannerInfo(): BannerInfo {
        return BannerInfo(
            bannerId = bannerId,
            status = ReportStatus.valueOf(status),
            companyName = companyName,
            phoneNumber = phoneNumber
        )
    }
}