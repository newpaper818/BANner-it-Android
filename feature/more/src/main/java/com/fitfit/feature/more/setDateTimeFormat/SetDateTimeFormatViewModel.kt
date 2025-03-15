package com.fitfit.feature.more.setDateTimeFormat

import androidx.lifecycle.ViewModel
import com.fitfit.core.data.data.repository.PreferencesRepository
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.enums.DateFormat
import com.fitfit.core.model.enums.TimeFormat
import com.fitfit.core.utils.getDateText
import com.fitfit.core.utils.getTimeText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

private const val SET_DATE_TIME_FORMAT_VIEWMODEL_TAG = "SetDateTimeFormat-ViewModel"

data class SetDateTimeFormatUiState(
    val dateExample: String = "",
    val timeExample: String = "",
)

@HiltViewModel
class SetDateTimeFormatViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
): ViewModel() {
    private val _setDateTimeFormatViewModel = MutableStateFlow(SetDateTimeFormatUiState())
    val setDateTimeFormatUiState = _setDateTimeFormatViewModel.asStateFlow()


    fun updateDateTimeExample(
        dateTimeFormat: DateTimeFormat
    ){
        val localDate = LocalDate.of(2025,1,20)
        val localTime = LocalTime.of(14,40)

        val newDateExample = getDateText(localDate, dateTimeFormat, true)
        val newTimeExample = getTimeText(localTime, dateTimeFormat.timeFormat)

        _setDateTimeFormatViewModel.update {
            it.copy(
                dateExample = newDateExample,
                timeExample = newTimeExample
            )
        }
    }

    suspend fun saveDateTimeFormatUserPreferences(
        dateFormat: DateFormat? = null,
        useMonthName: Boolean? = null,
        includeDayOfWeek: Boolean? = null,
        timeFormat: TimeFormat? = null
    ){
        if (dateFormat != null) {
            preferencesRepository.saveDateFormatPreference(dateFormat = dateFormat)
        }
        else if (useMonthName != null) {
            preferencesRepository.saveDateUseMonthNamePreference(useMonthName = useMonthName)
        }
        else if (includeDayOfWeek != null) {
            preferencesRepository.saveDateIncludeDayOfWeekPreference(includeDayOfWeek = includeDayOfWeek)
        }
        else if (timeFormat != null) {
            preferencesRepository.saveTimeFormatPreference(timeFormat = timeFormat)
        }
    }
}