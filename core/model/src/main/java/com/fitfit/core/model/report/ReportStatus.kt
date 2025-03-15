package com.fitfit.core.model.report

import androidx.annotation.StringRes
import com.fitfit.core.model.R

enum class ReportStatus(
    @StringRes val textId: Int
) {
    RECEIVED(R.string.received),

    LEGAL(R.string.legal),
    LEGAL_DEMOLITION(R.string.legal_demolition),

    ILLEGAL(R.string.illegal),
    ILLEGAL_DEMOLITION(R.string.illegal_demolition),

    UNKNOWN(R.string.unknown),
}