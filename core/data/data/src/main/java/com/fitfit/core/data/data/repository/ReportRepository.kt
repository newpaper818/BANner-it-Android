package com.fitfit.core.data.data.repository

import com.fitfit.core.data.remote_db.DbRemoteDataSource
import com.fitfit.core.model.report.ReportRecord
import javax.inject.Inject

private const val REPORT_REPOSITORY_TAG = "Report-Repository"

class ReportRepository @Inject constructor(
    private val dbRemoteDataSource: DbRemoteDataSource, //retrofit
) {

    //send report log
    suspend fun sendBannerReport(
        jwt: String,
        userId: Int,
        reportRecord: ReportRecord,
        onResult: (Boolean) -> Unit
    ){
//        val result = dbRemoteDataSource.postBannerReport(
//            jwt = jwt,
//            userId = userId,
//            reportRecord = reportRecord
//        )
//        onResult(result)

        //TODO delete after test - use above
        val result = dbRemoteDataSource.sendTestImage(
            jwt = jwt,
            userId = userId,
            reportRecord = reportRecord
        )
        onResult(result)
    }


    //get all report logs

}