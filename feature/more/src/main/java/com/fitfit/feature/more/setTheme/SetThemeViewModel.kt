package com.fitfit.feature.more.setTheme

import androidx.lifecycle.ViewModel
import com.fitfit.core.data.data.repository.PreferencesRepository
import com.fitfit.core.model.enums.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetThemeViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
): ViewModel() {

    suspend fun saveThemePreferences(
        appTheme: AppTheme?,
    ){
        if (appTheme != null) {
            preferencesRepository.saveAppThemePreference(appTheme)
        }
    }
}