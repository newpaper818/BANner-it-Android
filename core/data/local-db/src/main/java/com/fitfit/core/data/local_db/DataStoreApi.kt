package com.fitfit.core.data.local_db

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.data.Theme
import com.fitfit.core.model.enums.AppTheme
import com.fitfit.core.model.enums.DateFormat
import com.fitfit.core.model.enums.TimeFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val DATA_STORE_TAG = "DataStore"

class DataStoreApi @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DbLocalDataSource {
    private companion object {
        val JWT_SECURED = stringPreferencesKey("jwt_secured")

        val APP_THEME = intPreferencesKey("app_theme")

        val DATE_FORMAT = intPreferencesKey("date_format")
        val DATE_USE_MONTH_NAME = booleanPreferencesKey("date_use_month_name")
        val DATE_INCLUDE_DAY_OF_WEEK = booleanPreferencesKey("date_include_day_of_week")
        val TIME_FORMAT = intPreferencesKey("time_format")
    }

    private val jwtSecured: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(DATA_STORE_TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[JWT_SECURED] ?: ""
        }

    private val appTheme: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(DATA_STORE_TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[APP_THEME] ?: AppTheme.AUTO.ordinal
        }

    private val dateFormat: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(DATA_STORE_TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[DATE_FORMAT] ?: DateFormat.YMD.ordinal
        }

    private val dateUseMonthName: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(DATA_STORE_TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[DATE_USE_MONTH_NAME] ?: false
        }

    private val dateIncludeDayOfWeek: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(DATA_STORE_TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[DATE_INCLUDE_DAY_OF_WEEK] ?: false
        }

    private val timeFormat: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(DATA_STORE_TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[TIME_FORMAT] ?: TimeFormat.T24H.ordinal
        }

















    //get
    override suspend fun getJwtPreference(
        onGet: (jwtOriginal: String?) -> Unit
    ){
        val jwtSecured = jwtSecured.firstOrNull()

        if (jwtSecured == null || jwtSecured == ""){
            onGet(null)
        }
        else {
            //decrypt jwtSecured string
            val (iv, encryptedData) = JwtSecure.decodeIvAndData(jwtSecured)
            val decryptedData = SecurityUtil.decryptData(BuildConfig.JWT_KEY_ALIAS, iv, encryptedData)
            onGet(decryptedData)
        }
    }

    override suspend fun getAppPreferencesValue(
        onGet: (Theme, DateTimeFormat) -> Unit
    ){

        //get data from dataStore
        //theme
        val appTheme = AppTheme.get(appTheme.firstOrNull() ?: AppTheme.AUTO.ordinal)

        //date time format
        val dateFormat = DateFormat.get(dateFormat.firstOrNull() ?: DateFormat.YMD.ordinal)
        val useMonthName = dateUseMonthName.firstOrNull() ?: false
        val includeDayOfWeek = dateIncludeDayOfWeek.firstOrNull() ?: false
        val timeFormat = TimeFormat.get(timeFormat.firstOrNull() ?: TimeFormat.T24H.ordinal)

        onGet(
            Theme(appTheme),
            DateTimeFormat(timeFormat, useMonthName, includeDayOfWeek, dateFormat)
        )
    }




    //save
    override suspend fun saveJwtPreference(
        jwtOriginal: String?
    ){
        dataStore.edit { preferences ->

            if (jwtOriginal == null){
                preferences[JWT_SECURED] = ""
                Log.d(DATA_STORE_TAG, "jwt original: null")
            }
            else {
                //encrypt jwt string
                val (iv, secureByteArray) = SecurityUtil.encryptData(BuildConfig.JWT_KEY_ALIAS, jwtOriginal)
                val jwtSecured = JwtSecure.encodeIvAndData(iv, secureByteArray)
                preferences[JWT_SECURED] = jwtSecured

                Log.d(DATA_STORE_TAG, "jwt original: $jwtOriginal / jwt secured: $jwtSecured")
            }
        }
    }

    override suspend fun saveAppThemePreference(appTheme: AppTheme) {
        dataStore.edit { preferences ->
            preferences[APP_THEME] = appTheme.ordinal
        }
    }

    override suspend fun saveDateFormatPreference(dateFormat: DateFormat) {
        dataStore.edit { preferences ->
            preferences[DATE_FORMAT] = dateFormat.ordinal
        }
    }

    override suspend fun saveDateUseMonthNamePreference(useMonthName: Boolean) {
        dataStore.edit { preferences ->
            preferences[DATE_USE_MONTH_NAME] = useMonthName
        }
    }

    override suspend fun saveDateIncludeDayOfWeekPreference(includeDayOfWeek: Boolean) {
        dataStore.edit { preferences ->
            preferences[DATE_INCLUDE_DAY_OF_WEEK] = includeDayOfWeek
        }
    }

    override suspend fun saveTimeFormatPreference(timeFormat: TimeFormat) {
        dataStore.edit { preferences ->
            preferences[TIME_FORMAT] = timeFormat.ordinal
        }
    }
}