package com.fitfit.core.model.report

import com.google.android.gms.maps.model.LatLng
import java.time.ZonedDateTime

data class ReportLog(
    val reportId: Int = 0,
    val reportTime: ZonedDateTime = ZonedDateTime.now(),
    val status: ReportStatus = ReportStatus.RECEIVED,

    val createdUserId: Int = 0,

    val images: List<String> = listOf(), //string(url)
    val location: LatLng = LatLng(0.0, 0.0),
    val address: Address = Address(),
    val content: String = ""
)

data class Address(
    val address1: String = "",
    val address2: String = "",
    val address3: String = ""
)








val sampleReportLog = ReportLog(
    reportId = 123,
    reportTime = ZonedDateTime.now(),
    status = ReportStatus.RECEIVED,

    images = listOf(),
    location = LatLng(0.0, 0.0),
    content = "report content"
)

