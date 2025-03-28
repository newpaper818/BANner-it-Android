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
    val content: String = "",
    val bannerInfo: List<BannerInfo> = listOf()
)

data class BannerInfo(
    val bannerId: Int = 0,
    val status: ReportStatus = ReportStatus.RECEIVED,
    val companyName: String = "",
    val phoneNumber: String = "",
)

data class Address(
    val address1: String = "",
    val address2: String = "",
    val address3: String = ""
)








val sampleReportLog = ReportLog(
    reportId = 12345,
    reportTime = ZonedDateTime.now(),
    status = ReportStatus.RECEIVED,

    createdUserId = 145,

    images = listOf("https://www.greenkorea.org/wp-content/uploads/2024/03/IMG_8832-615x820.jpg"),
    location = LatLng(0.0, 0.0),
    address = Address(),
    content = "report content",
    bannerInfo = listOf(
        BannerInfo(
            bannerId = 4978,
            status = ReportStatus.LEGAL,
            companyName = "aaaa",
            phoneNumber = "010-2222-3366"
        ),
        BannerInfo(
            bannerId = 1059,
            status = ReportStatus.ILLEGAL,
            companyName = "ff",
            phoneNumber = "010-2222-7890"
        )
    )
)

val sampleReportLog2 = ReportLog(
    reportId = 454545,
    reportTime = ZonedDateTime.parse("2025-03-25T20:30:50Z"),
    status = ReportStatus.ILLEGAL_DEMOLITION,

    createdUserId = 1234,

    images = listOf(),
    location = LatLng(0.0, 0.0),
    address = Address(),
    content = "report content",
    bannerInfo = listOf(
        BannerInfo(
            bannerId = 4978,
            status = ReportStatus.LEGAL,
            companyName = "aaaa",
            phoneNumber = "010-2222-3366"
        ),
        BannerInfo(
            bannerId = 1059,
            status = ReportStatus.ILLEGAL,
            companyName = "ff",
            phoneNumber = "010-2222-7890"
        )
    )
)

val sampleReportLog3 = ReportLog(
    reportId = 124511,
    reportTime = ZonedDateTime.parse("2025-03-28T08:10:00Z"),
    status = ReportStatus.LEGAL,

    createdUserId = 1234,

    images = listOf(""),
    location = LatLng(0.0, 0.0),
    address = Address(),
    content = "report content",
    bannerInfo = listOf(
        BannerInfo(
            bannerId = 4978,
            status = ReportStatus.LEGAL,
            companyName = "aaaa",
            phoneNumber = "010-2222-3366"
        ),
        BannerInfo(
            bannerId = 1059,
            status = ReportStatus.ILLEGAL,
            companyName = "ff",
            phoneNumber = "010-2222-7890"
        )
    )
)
