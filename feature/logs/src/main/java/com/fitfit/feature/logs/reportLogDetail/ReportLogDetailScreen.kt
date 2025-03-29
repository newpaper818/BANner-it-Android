package com.fitfit.feature.logs.reportLogDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.report.ReportLog
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.ui.card.report.ContentCard
import com.fitfit.core.ui.ui.card.report.ImageCard
import com.fitfit.core.ui.ui.card.report.ReportLogBannerInfoCard
import com.fitfit.core.ui.ui.card.report.ReportLogInfoCard
import com.fitfit.core.ui.ui.item.TitleText
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.logs.R

@Composable
fun ReportLogDetailRoute(
    appUserData: UserData,
    use2Panes: Boolean,
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,
    internetEnabled: Boolean,

    reportLog: ReportLog,

    navigateUp: () -> Unit,

    modifier: Modifier = Modifier,
) {


    ReportLogDetailScreen(
        appUserData = appUserData,
        spacerValue = spacerValue,
        dateTimeFormat = dateTimeFormat,
        internetEnabled = internetEnabled,
        reportLog = reportLog,
        navigateUp = navigateUp
    )
}

@Composable
private fun ReportLogDetailScreen(
    appUserData: UserData,
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,
    internetEnabled: Boolean,

    reportLog: ReportLog,
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
        }
    ){ paddingValues ->

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
                ReportLogInfoCard(
                    reportLog = reportLog,
                    dateTimeFormat = dateTimeFormat,
                    modifier = itemModifier
                )
            }

            //image
            item {
                TitleText(
                    text = stringResource(R.string.photos_, reportLog.images.size),
                    modifier = itemModifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 6.dp)
                )

                ImageCard(
                    modifier = itemModifier,
                    imageUserId = appUserData.userId,
                    internetEnabled = internetEnabled,
                    isEditMode = false,
                    imagePathList = reportLog.images,
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
            if (reportLog.bannersInfo.isNotEmpty()) {

                item{
                    TitleText(
                        text = stringResource(R.string.banner_info),
                        modifier = itemModifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 6.dp)
                    )

                    reportLog.bannersInfo.forEach { bannerInfo ->
                        ReportLogBannerInfoCard(
                            bannerInfo = bannerInfo,
                            modifier = itemModifier
                        )

                        if (bannerInfo != reportLog.bannersInfo.last()){
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
                    contentText = reportLog.content,
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