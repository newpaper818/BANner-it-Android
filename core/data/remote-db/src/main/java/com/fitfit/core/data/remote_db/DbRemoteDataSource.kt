package com.fitfit.core.data.remote_db

import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.UserRole
import com.fitfit.core.model.report.BannerInfo
import com.fitfit.core.model.report.ReportRecord

interface DbRemoteDataSource {


    /**
     * request idToken /
     * sign up user /
     * get jwt, [UserData] from server
     *
     * @param userGoogleIdToken
     * @return Pair(jwt, [UserData])
     *
     * or null(error)
     */
    suspend fun requestUserDataWithIdToken(
        userGoogleIdToken: String,
    ): UserData?




    /**
     * request jwt /
     * check user exist /
     * get jwt, [UserData] from server
     *
     * @param jwt
     * @return Pair(jwt, [UserData]) or
     *
     * null(user not exist or error)
     */
    suspend fun requestUserDataWithJwt(
        jwt: String
    ): Pair<String, UserData>?



    suspend fun postBannerReport(
        jwt: String,
        userId: Int,
        reportRecord: ReportRecord
    ): Boolean

    //TODO delete after test
    suspend fun sendTestImage(
        jwt: String,
        userId: Int,
        reportRecord: ReportRecord
    ): Boolean


    suspend fun getAppUserReportRecords(
        jwt: String,
    ): List<ReportRecord>?

    suspend fun getAllReportRecords(

    ): List<ReportRecord>?

    suspend fun editBannerInfo(
        jwt: String,
        reportId: Int,
        bannerInfo: List<BannerInfo>
    ): Boolean


    suspend fun updateProfile(
        jwt: String,
        userName: String,
        userRole: UserRole
    ): Boolean
}

