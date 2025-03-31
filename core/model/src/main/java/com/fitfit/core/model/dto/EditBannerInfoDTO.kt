package com.fitfit.core.model.dto

import com.fitfit.core.model.dto.basic.BannerInfoIdWithStatusDTO
import com.fitfit.core.model.dto.basic.ErrorDto
import com.fitfit.core.model.report.BannerInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

fun BannerInfo.toBannerInfoIdWithStatusDTO(): BannerInfoIdWithStatusDTO {
    return BannerInfoIdWithStatusDTO(
        bannerId = bannerId,
        status = status.name
    )
}

//request
@JsonClass(generateAdapter = true)
data class EditBannerInfoRequestDTO(
    @Json(name = "report_id")val reportId: Int,
    @Json(name = "banner_info")val bannerInfoIdWithStatusDTO: List<BannerInfoIdWithStatusDTO>,
)

//response
@JsonClass(generateAdapter = true)
data class EditBannerInfoResponseDTO(
    @Json(name = "error")val error: ErrorDto?
)