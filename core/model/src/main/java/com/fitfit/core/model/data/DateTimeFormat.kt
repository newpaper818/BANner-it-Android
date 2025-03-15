package com.fitfit.core.model.data

import com.fitfit.core.model.enums.DateFormat
import com.fitfit.core.model.enums.TimeFormat

data class DateTimeFormat(
    val timeFormat: TimeFormat = TimeFormat.T24H,
    val useMonthName: Boolean = false,
    val includeDayOfWeek: Boolean = false,
    val dateFormat: DateFormat = DateFormat.YMD
)