package com.fitfit.core.model.report

import com.google.android.gms.maps.model.LatLng
import java.time.ZonedDateTime

data class ReportLog(
    val reportId: String,
    val reportDateTime: ZonedDateTime,
    val status: ReportStatus,

    val images: List<String>,
    val location: LatLng,
    val content: String
)



val sampleReportLog = ReportLog(
    reportId = "AA123",
    reportDateTime = ZonedDateTime.now(),
    status = ReportStatus.RECEIVED,

    images = listOf(),
    location = LatLng(0.0, 0.0),
    content = "report content"
)