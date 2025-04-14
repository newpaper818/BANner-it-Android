package com.fitfit.feature.logs.reportRecordDetail

import androidx.lifecycle.ViewModel
import com.fitfit.core.data.data.repository.ReportRecordDetailRepository
import com.fitfit.core.model.report.data.BannerInfo
import com.fitfit.core.model.report.data.ReportRecord
import com.fitfit.core.model.report.enums.ReportStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ReportRecordDetailUiState(
    val currentReportRecord: ReportRecord = ReportRecord(),

    val currentBannerInfo: BannerInfo? = null,
    val showSelectBannerStatusDialog: Boolean = false,
)

@HiltViewModel
class ReportRecordDetailViewModel @Inject constructor(
    private val reportRecordDetailRepository: ReportRecordDetailRepository,
): ViewModel() {
    private val _reportRecordDetailUiState = MutableStateFlow(ReportRecordDetailUiState())
    val reportRecordDetailUiState = _reportRecordDetailUiState.asStateFlow()

    fun setCurrentReportRecord(reportRecord: ReportRecord){
        _reportRecordDetailUiState.update {
            it.copy(currentReportRecord = reportRecord)
        }
    }

    fun setCurrentBannerInfo(bannerInfo: BannerInfo?){
        _reportRecordDetailUiState.update {
            it.copy(currentBannerInfo = bannerInfo)
        }
    }

    fun setShowSelectBannerStatusDialog(show: Boolean){
        _reportRecordDetailUiState.update {
            it.copy(showSelectBannerStatusDialog = show)
        }
    }

    suspend fun editBannerStatus(
        jwt: String,
        reportId: Int,
        bannerId: Int,
        bannerStatus: ReportStatus
    ): Boolean {
        //retrofit
        val result = reportRecordDetailRepository.editBannerInfo(
            jwt = jwt,
            reportId = reportId,
            bannerInfo = listOf(
                BannerInfo(
                    bannerId = bannerId,
                    status = bannerStatus
                )
            )
        )

        if (result){
            //change currentReportRecord
            val newBannerInfos = _reportRecordDetailUiState.value.currentReportRecord!!.bannersInfo.map { bannerInfo ->
                if (bannerInfo.bannerId == bannerId) {
                    bannerInfo.copy(status = bannerStatus)
                } else {
                    bannerInfo
                }
            }

            val newReportRecord = _reportRecordDetailUiState.value.currentReportRecord!!.copy(
                bannersInfo = newBannerInfos
            )
            setCurrentReportRecord(newReportRecord)

            return true
        }
        else{
            return false
        }
    }
}