package com.fitfit.core.data.data.repository

import com.fitfit.core.data.local_db.DbLocalDataSource
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.data.Theme
import com.fitfit.core.model.enums.AppTheme
import com.fitfit.core.model.enums.DateFormat
import com.fitfit.core.model.enums.TimeFormat
import javax.inject.Inject

class PreferencesRepository @Inject constructor(
    private val dbLocalDataSource: DbLocalDataSource
) {
    suspend fun getJwtPreference(
        onGet: (jwtOriginal: String?) -> Unit
    ){
        dbLocalDataSource.getJwtPreference(onGet = onGet)
    }

    suspend fun getAppPreferencesValue(
        onGet: (Theme, DateTimeFormat) -> Unit
    ) {
        dbLocalDataSource.getAppPreferencesValue(onGet = onGet)
    }



    suspend fun saveJwtPreference(
        jwtOriginal: String?
    ){
        dbLocalDataSource.saveJwtPreference(jwtOriginal = jwtOriginal)
    }

    suspend fun saveAppThemePreference(
        appTheme: AppTheme
    ) {
        dbLocalDataSource.saveAppThemePreference(appTheme = appTheme)
    }

    suspend fun saveDateFormatPreference(
        dateFormat: DateFormat
    ) {
        dbLocalDataSource.saveDateFormatPreference(dateFormat = dateFormat)
    }

    suspend fun saveDateUseMonthNamePreference(
        useMonthName: Boolean
    ) {
        dbLocalDataSource.saveDateUseMonthNamePreference(useMonthName = useMonthName)
    }

    suspend fun saveDateIncludeDayOfWeekPreference(
        includeDayOfWeek: Boolean
    ) {
        dbLocalDataSource.saveDateIncludeDayOfWeekPreference(includeDayOfWeek = includeDayOfWeek)
    }

    suspend fun saveTimeFormatPreference(
        timeFormat: TimeFormat
    ) {
        dbLocalDataSource.saveTimeFormatPreference(timeFormat = timeFormat)
    }

}