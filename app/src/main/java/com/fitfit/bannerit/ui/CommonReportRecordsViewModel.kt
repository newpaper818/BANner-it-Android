package com.fitfit.bannerit.ui

import androidx.lifecycle.ViewModel
import com.fitfit.core.data.data.repository.ReportRecordsRepository
import com.fitfit.core.model.report.ReportRecord
import com.fitfit.core.model.report.sampleReportRecord
import com.fitfit.core.model.report.sampleReportRecord2
import com.fitfit.core.model.report.sampleReportRecord3
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ReportUiState(
    val appUserReportRecords: List<ReportRecord> = listOf(),
    val allReportRecords: List<ReportRecord> = listOf(),

    val currentReportRecord: ReportRecord? = null
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




    suspend fun getAppUserReportRecords(
        jwt: String
    ) {
        val newAppUserReportRecords = reportRecordsRepository.getAppUserReportRecords(jwt = jwt)
        if (newAppUserReportRecords != null)
            setAppUserReportRecords(newAppUserReportRecords)

        //TODO delete after test
        else {
            val sampleList = listOf(sampleReportRecord, sampleReportRecord2, sampleReportRecord3)
            delay(2000)
            setAppUserReportRecords(sampleList)
        }
    }

    suspend fun getAllReportRecords(

    ) {
        val newAdminReportRecords = reportRecordsRepository.getAllReportRecords()
        if (newAdminReportRecords != null)
            setAllReportRecords(newAdminReportRecords)

        //TODO delete after test
        else {
            val sampleList = listOf(sampleReportRecord3, sampleReportRecord, sampleReportRecord2)
            delay(2000)
            setAllReportRecords(sampleList)
        }
    }

}