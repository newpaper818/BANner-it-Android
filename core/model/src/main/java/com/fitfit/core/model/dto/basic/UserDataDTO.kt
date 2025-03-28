package com.fitfit.core.model.dto.basic

import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.ProviderId
import com.fitfit.core.model.enums.UserRole
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDataDTO(
    @Json(name = "user_id")val userId: Int,
    @Json(name = "role")val role: String,
    @Json(name = "name")val name: String,
    @Json(name = "email")val email: String,
    @Json(name = "profile_image_url")val profileImageUrl: String,
){
    fun toUserData(
        jwt: String
    ): UserData {
        return UserData(
            jwt = jwt,
            userId = userId,
            role = UserRole.valueOf(role),
            name = name,
            email = email,
            profileImageUrl = profileImageUrl,
            providerIds = listOf(ProviderId.GOOGLE)
        )
    }
}