package com.fitfit.core.data.data.repository.account

import com.fitfit.core.data.remote_db.DbRemoteDataSource
import javax.inject.Inject

class DeleteAccountRepository @Inject constructor(
    private val dbRemoteDataSource: DbRemoteDataSource,
){
    /**
     * ⚠️ DELETE ACCOUNT ⚠️
     */
    suspend fun deleteAccount(
        jwt: String
    ): Boolean {
        return dbRemoteDataSource.deleteAccount(
            jwt = jwt
        )
    }
}