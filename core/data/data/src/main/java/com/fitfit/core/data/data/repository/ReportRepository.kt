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
        //get preSigned url
        val newReportImages = dbRemoteDataSource.getPreSignedUrls(
            jwt = jwt,
            reportImages = reportRecord.images
        )

        if (newReportImages != null){

            //upload to S3
            val uploadImagesResult = dbRemoteDataSource.uploadImagesToS3(
                reportImages = newReportImages
            )

            if (!uploadImagesResult) {
                onResult(false)
                return
            }
        }
        else {
            onResult(false)
            return
        }

        //report banner
        val reportBannerResult = dbRemoteDataSource.postBannerReport(
            jwt = jwt,
            userId = userId,
            reportRecord = reportRecord.copy(images = newReportImages)
        )

        onResult(reportBannerResult)
    }
}