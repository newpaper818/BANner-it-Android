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
    val allReportLogs: List<ReportLog> = listOf(),

    val currentReportLogIndex: Int? = null
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

    private fun setAllReportLogs(
        adminReportLogs: List<ReportLog>
    ) {
        _reportUiState.update {
            it.copy(allReportLogs = adminReportLogs)
        }
    }

    fun setCurrentReportLogIndex(
        reportLogIndex: Int?
    ) {
        _reportUiState.update {
            it.copy(currentReportLogIndex = reportLogIndex)
        }
    }


    suspend fun getAppUserReportLogs(
        jwt: String
    ) {
        val newAppUserReportLogs = reportLogsRepository.getAppUserReportLogs(jwt = jwt)
        if (newAppUserReportLogs != null)
            setAppUserReportLogs(newAppUserReportLogs)

        //TODO delete after test
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
            setAllReportLogs(newAdminReportLogs)

        //TODO delete after test
        else {
            val sampleList = listOf(sampleReportLog3, sampleReportLog, sampleReportLog2)
            delay(2000)
            setAllReportLogs(sampleList)
        }
    }

}