package com.fitfit.core.model.enums

import androidx.annotation.StringRes
import com.fitfit.core.model.R

enum class UserRole(
    @StringRes val textId: Int
) {
    ADMIN(R.string.administrator),
    USER(R.string.user),
    GUEST(R.string.guest)
}