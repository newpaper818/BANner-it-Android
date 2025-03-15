package com.fitfit.core.data.local_db

import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.data.Theme
import com.fitfit.core.model.enums.AppTheme
import com.fitfit.core.model.enums.DateFormat
import com.fitfit.core.model.enums.TimeFormat

interface DbLocalDataSource {

    /**
     * update app setting values - at app start
     */
    suspend fun getJwtPreference(
        onGet: (jwtOriginal: String?) -> Unit
    )

    suspend fun getAppPreferencesValue(
        onGet: (Theme, DateTimeFormat) -> Unit
    )



    suspend fun saveJwtPreference(jwtOriginal: String?)


    suspend fun saveAppThemePreference(appTheme: AppTheme)

    suspend fun saveDateFormatPreference(dateFormat: DateFormat)

    suspend fun saveDateUseMonthNamePreference(useMonthName: Boolean)

    suspend fun saveDateIncludeDayOfWeekPreference(includeDayOfWeek: Boolean)

    suspend fun saveTimeFormatPreference(timeFormat: TimeFormat)
}