package com.fitfit.core.model.report.enums

import androidx.annotation.StringRes
import com.fitfit.core.model.R

enum class ReportStatus(
    @StringRes val textId: Int
) {
    RECEIVED(R.string.banner_received),
    COMPLETED(R.string.completed),
    ADMIN_CONFIRMED(R.string.admin_confirmed)
}