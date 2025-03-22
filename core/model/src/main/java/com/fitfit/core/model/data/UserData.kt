package com.fitfit.core.model.data

import com.fitfit.core.model.enums.ProviderId
import com.fitfit.core.model.enums.UserRole

data class UserData(
    val jwt: String,
    val userId: Int,
    val role: UserRole,

    val name: String?,
    val email: String?,
    val profileImageUrl: String?,

    val providerIds: List<ProviderId>,
)