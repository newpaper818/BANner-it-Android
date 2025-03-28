package com.fitfit.bannerit.ui

import androidx.lifecycle.ViewModel
import com.fitfit.core.data.data.repository.ReportLogsRepository
import com.fitfit.core.model.report.ReportLog
import com.fitfit.core.model.report.sampleReportLog
import com.fitfit.core.model.report.sampleReportLog2
import com.fitfit.core.model.report.sampleReportLog3
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ReportUiState(
    val appUserReportLogs: List<ReportLog> = listOf(),
    val adminReportLogs: List<ReportLog> = listOf(),

    val currentReportLog: ReportLog? = null
)

@HiltViewModel
class CommonReportLogsViewModel @Inject constructor(
    private val reportLogsRepository: ReportLogsRepository
): ViewModel() {

    private val _reportUiState = MutableStateFlow(ReportUiState())
    val reportUiState = _reportUiState.asStateFlow()


    private fun setAppUserReportLogs(
        appUserReportLogs: List<ReportLog>
    ){
        _reportUiState.update {
            it.copy(appUserReportLogs = appUserReportLogs)
        }
    }

    private fun setAdminReportLogs(
        adminReportLogs: List<ReportLog>
    ) {
        _reportUiState.update {
            it.copy(adminReportLogs = adminReportLogs)
        }
    }

    fun setCurrentReportLog(
        reportLog: ReportLog
    ) {
        _reportUiState.update {
            it.copy(currentReportLog = reportLog)
        }
    }


    suspend fun getAppUserReportLogs(
        jwt: String
    ) {
        val newAppUserReportLogs = reportLogsRepository.getAppUserReportLogs(jwt = jwt)
        if (newAppUserReportLogs != null)
            setAppUserReportLogs(newAppUserReportLogs)
        else {
            val sampleList = listOf(sampleReportLog, sampleReportLog2, sampleReportLog3)
            delay(2000)
            setAppUserReportLogs(sampleList)
        }
    }

    suspend fun getAdminReportLogs(

    ) {
        val newAdminReportLogs = reportLogsRepository.getAdminReportLogs()
        if (newAdminReportLogs != null)
            setAdminReportLogs(newAdminReportLogs)
    }

}