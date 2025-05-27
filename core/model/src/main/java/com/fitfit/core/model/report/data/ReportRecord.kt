package com.fitfit.core.model.report.data

import com.fitfit.core.model.report.enums.ReportStatus
import com.fitfit.core.model.report.enums.BannerStatus
import com.google.android.gms.maps.model.LatLng
import java.time.ZonedDateTime

data class ReportRecord(
    val reportId: Int = 0,
    val reportTime: ZonedDateTime = ZonedDateTime.now(),
    val status: ReportStatus = ReportStatus.RECEIVED,

    val createdUserId: Int? = null,

    val images: List<ReportImage> = listOf(), //string(url)
    val location: LatLng = LatLng(0.0, 0.0),
    val address: Address = Address(),
    val content: String = "",
    val bannersInfo: List<BannerInfo> = listOf()
)

data class ReportImage(
    val fileName: String? = null,
    val previewUrl: String? = null,
    val preSignedUrl: String? = null, //upload url
    val s3Key: String? = null
)

data class BannerInfo(
    val bannerId: Int = 0,
    val status: BannerStatus = BannerStatus.RECEIVED,
    val category: String = "",
    val companyName: String = "",
    val phoneNumber: String = "",
    val center: List<Float>? = null,
    val width: Float? = null,
    val height: Float? = null
)

data class Address(
    val address1: String = "",
    val address2: String = "",
    val address3: String = ""
)








val sampleReportRecord = ReportRecord(
    reportId = 12345,
    reportTime = ZonedDateTime.now(),
    status = ReportStatus.RECEIVED,

    createdUserId = 145,

    images = listOf(
        ReportImage(previewUrl = "https://www.greenkorea.org/wp-content/uploads/2024/03/IMG_8832-615x820.jpg"),
        ReportImage(previewUrl = "https://lh6.googleusercontent.com/proxy/1ZoW7lMeLruPcXpuTv-OrWj_Ksq7roabBQoZ90yyZdCxl7dNUTEmdyuLjmQlL785mb16F3W3_DUxERbt6JKKG8-1YOfDhE08oefCjcrvsxqtwzvLkA")
    ),
    location = LatLng(0.0, 0.0),
    address = Address(),
    content = "report content",
    bannersInfo = listOf(
        BannerInfo(
            bannerId = 4978,
            status = BannerStatus.RECEIVED,
            category = "정치",
            companyName = "aaaa",
            phoneNumber = "010-2222-3366"
        ),
        BannerInfo(
            bannerId = 1059,
            status = BannerStatus.ILLEGAL,
            category = "의료",
            companyName = "ff",
            phoneNumber = "010-2222-7890"
        ),
        BannerInfo(
            bannerId = 2373732,
            status = BannerStatus.ILLEGAL_DEMOLITION,
            category = "광고",
            companyName = "long text long text long text long text long text long text long text long text long text long text long text long text long text long text long text long text long text long text",
            phoneNumber = "010-2222-7890"
        ),
        BannerInfo(
            bannerId = 43458,
            status = BannerStatus.LEGAL,
            category = "부동산",
            companyName = "ff",
            phoneNumber = "010-2222-7890"
        ),
        BannerInfo(
            bannerId = 54,
            status = BannerStatus.LEGAL_DEMOLITION,
            category = "기타",
            companyName = "ff",
            phoneNumber = "010-2222-7890"
        ),
        BannerInfo(
            bannerId = 134377,
            status = BannerStatus.UNKNOWN,
            category = "?",
            companyName = "ff",
            phoneNumber = "010-2222-7890"
        )
    )
)

val sampleReportRecord2 = ReportRecord(
    reportId = 454545,
    reportTime = ZonedDateTime.parse("2025-03-25T20:30:50Z"),
    status = ReportStatus.COMPLETED,

    createdUserId = 1234,

    images = listOf(),
    location = LatLng(0.0, 0.0),
    address = Address(),
    content = "report content",
    bannersInfo = listOf(
        BannerInfo(
            bannerId = 4978,
            status = BannerStatus.LEGAL,
            category = "?",
            companyName = "aaaa",
            phoneNumber = "010-2222-3366"
        ),
        BannerInfo(
            bannerId = 1059,
            status = BannerStatus.ILLEGAL,
            category = "?",
            companyName = "ff",
            phoneNumber = "010-2222-7890"
        )
    )
)

val sampleReportRecord3 = ReportRecord(
    reportId = 124511,
    reportTime = ZonedDateTime.parse("2025-03-28T08:10:00Z"),
    status = ReportStatus.ADMIN_CONFIRMED,

    createdUserId = 1234,

    images = listOf(ReportImage()),
    location = LatLng(0.0, 0.0),
    address = Address(),
    content = "report content",
    bannersInfo = listOf(
        BannerInfo(
            bannerId = 4978,
            status = BannerStatus.LEGAL,
            category = "?",
            companyName = "aaaa",
            phoneNumber = "010-2222-3366"
        ),
        BannerInfo(
            bannerId = 1059,
            status = BannerStatus.ILLEGAL,
            category = "?",
            companyName = "ff",
            phoneNumber = "010-2222-7890"
        )
    )
)
