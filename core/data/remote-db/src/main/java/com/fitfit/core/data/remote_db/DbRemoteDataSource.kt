package com.fitfit.core.data.remote_db

import com.fitfit.core.model.data.UserData

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
    ): Pair<String, UserData>?




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
}

