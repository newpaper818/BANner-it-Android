package com.fitfit.core.model.dto

import com.fitfit.core.model.dto.basic.ErrorDto
import com.fitfit.core.model.dto.basic.ReportRecordDTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


//response
@JsonClass(generateAdapter = true)
data class GetReportRecordResponseDTO(
    @Json(name = "error")val error: ErrorDto?,
    @Json(name = "report_logs")val reportRecordsDTO: List<ReportRecordDTO>
)