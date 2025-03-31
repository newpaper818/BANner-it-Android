package com.fitfit.feature.logs.reportRecordDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.report.BannerInfo
import com.fitfit.core.model.report.ReportRecord
import com.fitfit.core.model.report.ReportStatus
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.ui.card.report.ContentCard
import com.fitfit.core.ui.ui.card.report.ImageCard
import com.fitfit.core.ui.ui.card.report.ReportRecordBannerInfoCard
import com.fitfit.core.ui.ui.card.report.ReportRecordInfoCard
import com.fitfit.core.ui.ui.dialog.SelectBannerStatusDialog
import com.fitfit.core.ui.ui.item.TitleText
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.logs.R
import kotlinx.coroutines.launch

@Composable
fun ReportRecordDetailRoute(
    appUserData: UserData,
    use2Panes: Boolean,
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,
    internetEnabled: Boolean,

    reportRecord: ReportRecord,

    navigateUp: () -> Unit,

    modifier: Modifier = Modifier,
    reportRecordDetailViewModel: ReportRecordDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        reportRecordDetailViewModel.setCurrentReportRecord(reportRecord)
    }

    val coroutineScope = rememberCoroutineScope()

    val reportRecordDetailUiState by reportRecordDetailViewModel.reportRecordDetailUiState.collectAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    val editBannerStatusErrorText = stringResource(R.string.edit_banner_status_error)

    val editBannerStatusErrorSnackbar = {
        coroutineScope.launch {
            snackBarHostState.showSnackbar(
                message = editBannerStatusErrorText,
                duration = SnackbarDuration.Short
            )
        }
    }


    if (reportRecordDetailUiState.currentReportRecord != null) {
        ReportRecordDetailScreen(
            appUserData = appUserData,
            spacerValue = spacerValue,
            dateTimeFormat = dateTimeFormat,
            internetEnabled = internetEnabled,
            snackBarHostState = snackBarHostState,
            reportRecord = reportRecordDetailUiState.currentReportRecord!!,

            currentBannerInfo = reportRecordDetailUiState.currentBannerInfo,
            setCurrentBannerInfo = reportRecordDetailViewModel::setCurrentBannerInfo,
            showSelectBannerStatusDialog = reportRecordDetailUiState.showSelectBannerStatusDialog,
            setShowSelectBannerStatusDialog = reportRecordDetailViewModel::setShowSelectBannerStatusDialog,
            editBannerStatus = { bannerId, bannerStatus ->
                coroutineScope.launch {
                    val result = reportRecordDetailViewModel.editBannerStatus(
                        jwt = appUserData.jwt,
                        reportId = reportRecord.reportId,
                        bannerId = bannerId,
                        bannerStatus = bannerStatus
                    )
                    reportRecordDetailViewModel.setShowSelectBannerStatusDialog(false)

                    if (!result) {
                        editBannerStatusErrorSnackbar()
                    }
                }
            },

            navigateUp = navigateUp
        )
    }
}

@Composable
private fun ReportRecordDetailScreen(
    appUserData: UserData,
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,
    internetEnabled: Boolean,
    snackBarHostState: SnackbarHostState,

    reportRecord: ReportRecord,

    currentBannerInfo: BannerInfo?,
    setCurrentBannerInfo: (BannerInfo) -> Unit,
    showSelectBannerStatusDialog: Boolean,
    setShowSelectBannerStatusDialog: (Boolean) -> Unit,
    editBannerStatus: (bannerId: Int, bannerStatus: ReportStatus) -> Unit,

    navigateUp: () -> Unit,
){
    val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)

    MyScaffold(
        modifier = Modifier,

        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.report_record),
                navigationIcon = TopAppBarIcon.back,
                onClickNavigationIcon = { navigateUp() }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .width(500.dp)
                    .navigationBarsPadding(),
                snackbar = {
                    Snackbar(
                        snackbarData = it,
                        shape = MaterialTheme.shapes.medium
                    )
                }
            )
        }
    ){ paddingValues ->

        //dialog
        if (currentBannerInfo != null && showSelectBannerStatusDialog){
            SelectBannerStatusDialog(
                initialBannerStatus = currentBannerInfo.status,
                onSaveClick = { newBannerStatus ->
                    if (newBannerStatus != currentBannerInfo.status)
                        editBannerStatus(currentBannerInfo.bannerId, newBannerStatus)
                    else
                        setShowSelectBannerStatusDialog(false)
                },
                onDismissRequest = { setShowSelectBannerStatusDialog(false) }
            )
        }


        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(spacerValue, 16.dp, spacerValue, 200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            //report info
            item {
                ReportRecordInfoCard(
                    reportRecord = reportRecord,
                    dateTimeFormat = dateTimeFormat,
                    modifier = itemModifier
                )
            }

            //image
            item {
                TitleText(
                    text = stringResource(R.string.photos_, reportRecord.images.size),
                    modifier = itemModifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 6.dp)
                )

                ImageCard(
                    modifier = itemModifier,
                    imageUserId = appUserData.userId,
                    internetEnabled = internetEnabled,
                    isEditMode = false,
                    imagePathList = reportRecord.images,
                    isImageCountOver = false,
                    onClickImage = { initialImageIndex ->

                    },
                    deleteImage = { _ -> },
                    isOverImage = { _ -> },
                    downloadImage = { _, _, _ -> },
                    saveImageToInternalStorage = {_, _ -> null}
                )
            }

            //banner info
            if (reportRecord.bannersInfo.isNotEmpty()) {

                item{
                    TitleText(
                        text = stringResource(R.string.banner_info),
                        modifier = itemModifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 6.dp)
                    )

                    reportRecord.bannersInfo.forEach { bannerInfo ->
                        ReportRecordBannerInfoCard(
                            bannerInfo = bannerInfo,
                            onClick = {
                                setCurrentBannerInfo(bannerInfo)
                                setShowSelectBannerStatusDialog(true)
                            },
                            modifier = itemModifier
                        )

                        if (bannerInfo != reportRecord.bannersInfo.last()){
                            MySpacerColumn(16.dp)
                        }
                    }
                }
            }

            //content
            item {
                TitleText(
                    text = stringResource(R.string.content),
                    modifier = itemModifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 6.dp)
                )

                ContentCard(
                    modifier = itemModifier,
                    isEditMode = false,
                    contentText = reportRecord.content,
                    onContentTextChange = { _ -> },
                    isLongText = { _ -> },
                )
            }

            //location
            item {
                TitleText(
                    text = stringResource(R.string.location),
                    modifier = itemModifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 6.dp)
                )

                MyCard(
                    modifier = Modifier
                        .widthIn(max = itemMaxWidthSmall)
                        .height(140.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}