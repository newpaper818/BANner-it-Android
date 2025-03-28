package com.fitfit.core.data.data.repository

import com.fitfit.core.data.remote_db.DbRemoteDataSource
import com.fitfit.core.model.report.ReportLog
import javax.inject.Inject

private const val REPORT_REPOSITORY_TAG = "Report-Repository"

class ReportRepository @Inject constructor(
    private val dbRemoteDataSource: DbRemoteDataSource, //retrofit
) {

    //send report log
    suspend fun sendReportLog(
        jwt: String,
        userId: Int,
        reportLog: ReportLog,
        onResult: (Boolean) -> Unit
    ){
//        val result = dbRemoteDataSource.postReportLog(
//            jwt = jwt,
//            userId = userId,
//            reportLog = reportLog
//        )
//        onResult(result)

        //TODO delete after test - use above
        val result = dbRemoteDataSource.sendTestImage(
            jwt = jwt,
            userId = userId,
            reportLog = reportLog
        )
        onResult(result)
    }


    //get all report logs

}