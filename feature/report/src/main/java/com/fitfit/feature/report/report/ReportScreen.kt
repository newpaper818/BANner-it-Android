package com.fitfit.feature.report.report

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.report.ReportRecord
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.button.BottomReportCancelButtons
import com.fitfit.core.ui.designsystem.components.button.GetPhotosButtons
import com.fitfit.core.ui.designsystem.components.button.LocationButtons
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.designsystem.theme.CustomColor
import com.fitfit.core.ui.ui.card.report.ContentCard
import com.fitfit.core.ui.ui.card.report.ImageCard
import com.fitfit.core.ui.ui.card.report.MAX_CONTENT_LENGTH
import com.fitfit.core.ui.ui.card.report.MAX_IMAGE_COUNT
import com.fitfit.core.ui.ui.dialog.TwoButtonsDialog
import com.fitfit.core.ui.ui.item.TitleText
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.report.R
import com.fitfit.feature.report.report.component.SendReportResultDialog
import kotlinx.coroutines.launch

@Composable
fun ReportRoute(
    appUserData: UserData,
    use2Panes: Boolean,
    spacerValue: Dp,
    internetEnabled: Boolean,

    navigateUp: () -> Unit,
    navigateToCamera: () -> Unit,
    navigateToImage: (imageList: List<String>, initialImageIndex: Int) -> Unit,

    modifier: Modifier = Modifier,

    reportViewModel: ReportViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val reportUiState by reportViewModel.reportUiState.collectAsState()

    val onClickBackButton = {
        reportViewModel.setShowExitDialog(true)
    }

    LaunchedEffect(Unit) {
        reportViewModel.updateCameraCapturedImage(context = context)
    }


    if (reportUiState.showExitDialog) {
        TwoButtonsDialog(
            bodyText = stringResource(id = R.string.dialog_body_exit),
            positiveButtonText = stringResource(id = R.string.exit),
            onDismissRequest = {
                reportViewModel.setShowExitDialog(false)
            },
            onClickPositive = {
                reportViewModel.setShowExitDialog(false)
                navigateUp()
            }
        )
    }

    AnimatedVisibility(
        visible = reportUiState.showSendReportResultDialog,
        enter = slideInHorizontally(animationSpec = tween(300), initialOffsetX = { it }),
        exit = slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { it }),
        modifier = Modifier.zIndex(1f)
    ) {
        SendReportResultDialog(
            success = reportUiState.sendReportResultIsSuccess == true,
            onClickGoBackHome = {
                navigateUp()
            },
            onClickGoBack = {
                reportViewModel.setShowSendReportResultDialog(false)
            }
        )
    }




    BackHandler {
        if (reportUiState.showSendReportResultDialog){
            when (reportUiState.sendReportResultIsSuccess){
                true -> navigateUp()
                false -> reportViewModel.setShowSendReportResultDialog(false)
                null -> { }
            }
        }
        else{
            onClickBackButton()
        }
    }

    ReportScreen(
        appUserData = appUserData,
        spacerValue = spacerValue,
        internetEnabled = internetEnabled,

        reportRecord = reportUiState.reportRecord,
        isPhotoCountOver = reportUiState.isPhotoCountOver,
        isContentTextLengthOver = reportUiState.isContentTextLengthOver,
        isErrorContained = reportUiState.isErrorContained,

        setShowExitDialog = reportViewModel::setShowExitDialog,
        setContentText = reportViewModel::setContentText,
        setContentTextLengthOver = reportViewModel::setContentTextLengthOver,
        setPhotoCountOver = reportViewModel::setPhotoCountOver,

        addPhotos = reportViewModel::addPhotos,
        deletePhotos = reportViewModel::deletePhotos,
        saveImageToInternalStorage = { index, uri ->
            reportViewModel.saveImageToInternalStorage(
                userId = appUserData.userId,
                index = index,
                uri = uri
            )
        },

        navigateToCamera = navigateToCamera,
        navigateToImage = navigateToImage,
        onClickReport = {
            coroutineScope.launch {
                reportViewModel.sendBannerReport(appUserData)
            }
        }
    )
}

@Composable
private fun ReportScreen(
    appUserData: UserData,
    spacerValue: Dp,
    internetEnabled: Boolean,

    reportRecord: ReportRecord,
    isPhotoCountOver: Boolean,
    isContentTextLengthOver: Boolean,
    isErrorContained: Boolean,

    setShowExitDialog: (Boolean) -> Unit,
    setContentText: (String) -> Unit,
    setContentTextLengthOver: (Boolean) -> Unit,
    setPhotoCountOver: (Boolean) -> Unit,

    addPhotos: (List<String>) -> Unit,
    deletePhotos: (List<String>) -> Unit,
    saveImageToInternalStorage: (index: Int, uri: Uri) -> String?,

    navigateToCamera: () -> Unit,
    navigateToImage: (imageList: List<String>, initialImageIndex: Int) -> Unit,
    onClickReport: () -> Unit,
){
    val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)
    val coroutineScope = rememberCoroutineScope()

    //gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ){ uriList ->
        var addUriList = uriList

        if (reportRecord.images.size + uriList.size > MAX_IMAGE_COUNT && !isPhotoCountOver){
            setPhotoCountOver(true)
        }

        if (uriList.size > 10){
            addUriList = addUriList.subList(0, 10)
        }

        val fileNames: MutableList<String> = mutableListOf()

        coroutineScope.launch {
            //save to internal storage
            addUriList.forEachIndexed { index, uri ->
                val fileName = saveImageToInternalStorage(index, uri)
                if (fileName != null)
                    fileNames.add(fileName)
            }
            addPhotos(fileNames)
        }
    }

    MyScaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .displayCutoutPadding()
            .imePadding(),

        //top app bar
        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.report),
                internetEnabled = internetEnabled,
                startPadding = spacerValue,
                navigationIcon = TopAppBarIcon.back,
                onClickNavigationIcon = { setShowExitDialog(true) }
            )
        }
    ){ paddingValues ->

        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            //content
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp),
                contentPadding = PaddingValues(spacerValue, 16.dp, spacerValue, 200.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .navigationBarsPadding()
            ){
                //photos
                item {
                    Row(
                        modifier = itemModifier.padding(bottom = 6.dp)
                    ) {
                        TitleText(
                            text = stringResource(R.string.photos, reportRecord.images.size, MAX_IMAGE_COUNT),
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        if (isPhotoCountOver) {
                            Text(
                                text = stringResource(R.string.photo_up_to, MAX_IMAGE_COUNT),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = CustomColor.outlineError
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }

                    ImageCard(
                        modifier = itemModifier,
                        imageUserId = appUserData.userId,
                        internetEnabled = internetEnabled,
                        isEditMode = true,
                        images = reportRecord.images.mapNotNull { it.fileName },
                        isImageCountOver = isPhotoCountOver,
                        onClickImage = { initialImageIndex ->
                            navigateToImage(reportRecord.images.mapNotNull { it.fileName }, initialImageIndex)
                        },
//                        onAddImages = { addedImageFiles ->
//                            addPhotos(addedImageFiles)
//                        },
                        deleteImage = { deletedImageFileName ->
                            deletePhotos(listOf(deletedImageFileName))
                        },
                        isOverImage = { isOverImage ->
                            setPhotoCountOver(isOverImage)
                        },
                        downloadImage = { imagePath, imageUserId, result -> },
                        saveImageToInternalStorage = saveImageToInternalStorage
                    )

                    MySpacerColumn(height = 16.dp)

                    GetPhotosButtons(
                        enabled = reportRecord.images.size < MAX_IMAGE_COUNT,
                        onClickTakePhotos = {
                            navigateToCamera()
                        },
                        onClickSelectPhotos = {
                            galleryLauncher.launch("image/*")
                        }
                    )
                }

                //content
                item {
                    Row(
                        modifier = itemModifier.padding(bottom = 6.dp)
                    ) {
                        TitleText(
                            text = stringResource(R.string.content),
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        if (isContentTextLengthOver) {
                            Text(
                                text = stringResource(R.string.text_up_to, MAX_CONTENT_LENGTH),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = CustomColor.outlineError
                                ),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }


                    ContentCard(
                        modifier = itemModifier,
                        isEditMode = true,
                        contentText = reportRecord.content,
                        onContentTextChange = { newContentText ->
                            setContentText(newContentText)
                        },
                        isLongText = { isLongText ->
                            setContentTextLengthOver(isLongText)
                        },
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

                    MySpacerColumn(16.dp)

                    LocationButtons(
                        onClickSelectLocation = {

                        },
                        onClickCurrentLocation = {

                        }
                    )
                }
            }

            //bottom report cancel buttons
            BottomReportCancelButtons(
                reportEnabled = isErrorContained && internetEnabled && reportRecord.images.isNotEmpty(),
                onClickCancel = {
                    setShowExitDialog(true)
                },
                onClickReport = {
                    onClickReport()
                },
                modifier = Modifier
            )
        }
    }
}