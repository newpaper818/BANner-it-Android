package com.fitfit.core.data.data.repository

import com.fitfit.core.data.remote_db.DbRemoteDataSource
import com.fitfit.core.model.report.ReportLog
import javax.inject.Inject

private const val REPORT_LOGS_REPOSITORY_TAG = "Report-Logs-Repository"

class ReportLogsRepository @Inject constructor(
    private val dbRemoteDataSource: DbRemoteDataSource, //retrofit
) {

    suspend fun getAppUserReportLogs(
        jwt: String
    ): List<ReportLog>? {
        return dbRemoteDataSource.getAppUserReportLogs(jwt = jwt)
    }

    suspend fun getAdminReportLogs(

    ): List<ReportLog>? {
        return dbRemoteDataSource.getAllReportLogs()
    }
}