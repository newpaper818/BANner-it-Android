package com.fitfit.feature.logs.reportRecordDetail

import androidx.lifecycle.ViewModel
import com.fitfit.core.data.data.repository.ReportRecordDetailRepository
import com.fitfit.core.model.report.BannerInfo
import com.fitfit.core.model.report.ReportStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class ReportRecordDetailUiState(
    val currentBannerInfo: BannerInfo? = null,
    val showSelectBannerStatusDialog: Boolean = false,
)

@HiltViewModel
class ReportRecordDetailViewModel @Inject constructor(
    private val reportRepository: ReportRecordDetailRepository,
): ViewModel() {
    private val _reportRecordDetailUiState = MutableStateFlow(ReportRecordDetailUiState())
    val reportRecordDetailUiState = _reportRecordDetailUiState.asStateFlow()


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

    fun saveBannerStatus(
        reportId: Int,
        bannerId: Int,
        bannerStatus: ReportStatus
    ){

    }
}