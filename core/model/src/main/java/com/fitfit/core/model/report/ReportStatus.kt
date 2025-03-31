package com.fitfit.core.model.report


import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.fitfit.core.model.R

enum class ReportStatus(
    @StringRes val textId: Int,
    val color: Color,
    val textColor: Color
) {
    RECEIVED(
        R.string.received,
        Color(0xfff09000), Color.White
    ),

    LEGAL(
        R.string.legal,
        Color(0xff000BE3), Color.White
    ),
    LEGAL_DEMOLITION(
        R.string.legal_demolition,
        Color(0xff06C200), Color.White
    ),

    ILLEGAL(
        R.string.illegal,
        Color(0xffd13030), Color.White
    ),
    ILLEGAL_DEMOLITION(
        R.string.illegal_demolition,
        Color(0xff06C200), Color.White
    ),

    UNKNOWN(
        R.string.unknown,
        Color(0xFF808080), Color.White
    ),
}