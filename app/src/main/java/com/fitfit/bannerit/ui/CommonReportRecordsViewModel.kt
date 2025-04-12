package com.fitfit.bannerit.ui

import androidx.lifecycle.ViewModel
import com.fitfit.core.data.data.repository.ReportRecordsRepository
import com.fitfit.core.model.report.ReportRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 *
 * @property images image preview url or image file name
 */
data class ReportUiState(
    val appUserReportRecords: List<ReportRecord> = listOf(),
    val allReportRecords: List<ReportRecord> = listOf(),

    val currentReportRecord: ReportRecord? = null,

    val images: List<String> = listOf(),
    val initialImageIndex: Int = 0
)

@HiltViewModel
class CommonReportRecordsViewModel @Inject constructor(
    private val reportRecordsRepository: ReportRecordsRepository
): ViewModel() {

    private val _reportUiState = MutableStateFlow(ReportUiState())
    val reportUiState = _reportUiState.asStateFlow()


    private fun setAppUserReportRecords(
        appUserReportRecords: List<ReportRecord>
    ){
        _reportUiState.update {
            it.copy(appUserReportRecords = appUserReportRecords)
        }
    }

    private fun setAllReportRecords(
        adminReportRecords: List<ReportRecord>
    ) {
        _reportUiState.update {
            it.copy(allReportRecords = adminReportRecords)
        }
    }

    fun setCurrentReportRecord(
        reportRecord: ReportRecord?
    ) {
        _reportUiState.update {
            it.copy(currentReportRecord = reportRecord)
        }
    }

    fun setImageListAndInitialImageIndex(
        images: List<String>,
        initialImageIndex: Int
    ) {
        _reportUiState.update {
            it.copy(
                images = images,
                initialImageIndex = initialImageIndex
            )
        }
    }

    fun clearReportRecords(

    ){
        _reportUiState.update {
            it.copy(
                appUserReportRecords = listOf(),
                allReportRecords = listOf(),
                currentReportRecord = null
            )
        }
    }




    suspend fun getAppUserReportRecords(
        jwt: String
    ) {
        val newAppUserReportRecords = reportRecordsRepository.getAppUserReportRecords(jwt = jwt)
        if (newAppUserReportRecords != null
            && newAppUserReportRecords.map { it.reportId } != _reportUiState.value.appUserReportRecords.map { it.reportId }) {
            setAppUserReportRecords(newAppUserReportRecords)
        }
    }

    suspend fun getAllReportRecords(

    ) {
        val newAdminReportRecords = reportRecordsRepository.getAllReportRecords()
        if (newAdminReportRecords != null
            && newAdminReportRecords.map { it.reportId } != _reportUiState.value.allReportRecords.map { it.reportId }) {
            setAllReportRecords(newAdminReportRecords)
        }
    }

}