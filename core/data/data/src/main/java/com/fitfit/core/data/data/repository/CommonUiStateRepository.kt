package com.fitfit.core.data.data.repository

import com.fitfit.core.model.report.ReportLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

//data class ReportUiState(
//    val appUserReportLogs: List<ReportLog> = listOf(),
//    val adminReportLogs: List<ReportLog> = listOf(),
//)
//
//
//@Singleton
//class CommonUiStateRepository @Inject constructor() {
//    val _commonUiState: MutableStateFlow<ReportUiState> =
//        MutableStateFlow(
//            ReportUiState()
//        )
//
//    val commonUiState = _commonUiState.asStateFlow()
//}