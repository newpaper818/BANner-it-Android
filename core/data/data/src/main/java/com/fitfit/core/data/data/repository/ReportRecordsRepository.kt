package com.fitfit.core.data.data.repository

import com.fitfit.core.data.remote_db.DbRemoteDataSource
import com.fitfit.core.model.report.ReportRecord
import javax.inject.Inject

private const val REPORT_LOGS_REPOSITORY_TAG = "Report-Logs-Repository"

class ReportRecordsRepository @Inject constructor(
    private val dbRemoteDataSource: DbRemoteDataSource, //retrofit
) {

    suspend fun getAppUserReportRecords(
        jwt: String
    ): List<ReportRecord>? {
        return dbRemoteDataSource.getAppUserReportRecords(jwt = jwt)
    }

    suspend fun getAdminReportRecords(

    ): List<ReportRecord>? {
        return dbRemoteDataSource.getAllReportRecords()
    }
}