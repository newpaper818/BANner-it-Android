package com.fitfit.core.data.data.repository

import com.fitfit.core.data.remote_db.DbRemoteDataSource
import com.fitfit.core.model.enums.UserRole
import javax.inject.Inject

class EditProfileRepository @Inject constructor(
    private val dbRemoteDataSource: DbRemoteDataSource,
){

    suspend fun updateUserProfile(
        jwt: String,
        userName: String,
        userRole: UserRole
    ): Boolean {
        return dbRemoteDataSource.updateUserData(
            jwt = jwt,
            userName = userName,
            userRole = userRole
        )
    }
}