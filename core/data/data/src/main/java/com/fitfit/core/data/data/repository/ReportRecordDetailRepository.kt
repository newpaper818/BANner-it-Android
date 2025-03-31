package com.fitfit.core.data.data.repository

import com.fitfit.core.data.remote_db.DbRemoteDataSource
import com.fitfit.core.model.report.BannerInfo
import javax.inject.Inject

class ReportRecordDetailRepository @Inject constructor(
    private val dbRemoteDataSource: DbRemoteDataSource, //retrofit
) {

    //edit banner status
    suspend fun editBannerInfo(
        jwt: String,
        reportId: Int,
        bannerInfo: List<BannerInfo>
    ): Boolean {
        return dbRemoteDataSource.editBannerInfo(
            jwt = jwt,
            reportId = reportId,
            bannerInfo = bannerInfo
        )
    }

}