package com.fitfit.core.model.dto

import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.ProviderId
import com.fitfit.core.model.enums.UserRole
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignInResponse(
    @Json(name = "success")val success: Boolean,
    @Json(name = "user_data")val userDataDTO: UserDataDTO?,
    @Json(name = "error")val error: String?
)

@JsonClass(generateAdapter = true)
data class UserDataDTO(
    @Json(name = "user_id")val userId: Int,
    @Json(name = "role")val role: String,
    @Json(name = "name")val name: String,
    @Json(name = "email")val email: String,
    @Json(name = "profile_image_url")val profileImageUrl: String,
){
    fun toUserData(): UserData {
        return UserData(
            userId = userId,
            role = UserRole.valueOf(role),
            name = name,
            email = email,
            profileImageUrl = profileImageUrl,
            providerIds = listOf(ProviderId.GOOGLE)
        )
    }
}