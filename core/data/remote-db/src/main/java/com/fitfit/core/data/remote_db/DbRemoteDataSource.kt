package com.fitfit.core.data.remote_db

import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.report.ReportLog

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



    suspend fun postReportLog(
        jwt: String,
        userId: Int,
        reportLog: ReportLog
    ): Boolean
}

