package com.fitfit.feature.report.report

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.report.ReportLog
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.button.BottomReportCancelButtons
import com.fitfit.core.ui.designsystem.components.button.GetPhotosButtons
import com.fitfit.core.ui.designsystem.components.button.LocationButtons
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.designsystem.theme.CustomColor
import com.fitfit.core.ui.ui.card.ContentCard
import com.fitfit.core.ui.ui.card.MAX_IMAGE_COUNT
import com.fitfit.core.ui.ui.card.ImageCard
import com.fitfit.core.ui.ui.card.MAX_CONTENT_LENGTH
import com.fitfit.core.ui.ui.dialog.TwoButtonsDialog
import com.fitfit.core.ui.ui.item.TitleText
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.report.R
import kotlinx.coroutines.launch

@Composable
fun ReportRoute(
    appUserData: UserData,
    use2Panes: Boolean,
    spacerValue: Dp,
    internetEnabled: Boolean,

    navigateUp: () -> Unit,
    navigateToCamera: () -> Unit,
    navigateToSendReport: () -> Unit,

    modifier: Modifier = Modifier,

    reportViewModel: ReportViewModel = hiltViewModel()
) {
    val reportUiState by reportViewModel.reportUiState.collectAsState()

    val onClickBackButton = {
        reportViewModel.setShowExitDialog(true)
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

    BackHandler {
        onClickBackButton()
    }

    ReportScreen(
        appUserData = appUserData,
        spacerValue = spacerValue,
        internetEnabled = internetEnabled,

        reportLog = reportUiState.reportLog,
        isPhotoCountOver = reportUiState.isPhotoCountOver,
        isContentTextLengthOver = reportUiState.isContentTextLengthOver,
        reportButtonEnabled = reportUiState.reportButtonEnabled,

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
        navigateToSendReport = navigateToSendReport
    )
}

@Composable
private fun ReportScreen(
    appUserData: UserData,
    spacerValue: Dp,
    internetEnabled: Boolean,

    reportLog: ReportLog,
    isPhotoCountOver: Boolean,
    isContentTextLengthOver: Boolean,
    reportButtonEnabled: Boolean,

    setShowExitDialog: (Boolean) -> Unit,
    setContentText: (String) -> Unit,
    setContentTextLengthOver: (Boolean) -> Unit,
    setPhotoCountOver: (Boolean) -> Unit,

    addPhotos: (List<String>) -> Unit,
    deletePhotos: (List<String>) -> Unit,
    saveImageToInternalStorage: (index: Int, uri: Uri) -> String?,

    navigateToCamera: () -> Unit,
    navigateToSendReport: () -> Unit,
){
    val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)
    val coroutineScope = rememberCoroutineScope()

    //gallery launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetMultipleContents()
    ){ uriList ->
        var addUriList = uriList

        if (reportLog.photos.size + uriList.size > MAX_IMAGE_COUNT && !isPhotoCountOver){
            setPhotoCountOver(true)
        }

        if (uriList.size > 10){
            addUriList = addUriList.subList(0, 10)
        }

        val fileList: MutableList<String> = mutableListOf()

        coroutineScope.launch {
            //save to internal storage
            addUriList.forEachIndexed { index, uri ->
                val file = saveImageToInternalStorage(index, uri)
                if (file != null)
                    fileList.add(file)
            }
            addPhotos(fileList)
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
                            text = stringResource(R.string.photos, reportLog.photos.size, MAX_IMAGE_COUNT),
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
                        imagePathList = reportLog.photos,
                        isImageCountOver = isPhotoCountOver,
                        onClickImage = { initialImageIndex ->

                        },
//                        onAddImages = { addedImageFiles ->
//                            addPhotos(addedImageFiles)
//                        },
                        deleteImage = { deletedImageFiles ->
                            deletePhotos(listOf(deletedImageFiles))
                        },
                        isOverImage = { isOverImage ->
                            setPhotoCountOver(isOverImage)
                        },
                        downloadImage = { imagePath, imageUserId, result -> },
                        saveImageToInternalStorage = saveImageToInternalStorage
                    )

                    GetPhotosButtons(
                        enabled = reportLog.photos.size < MAX_IMAGE_COUNT,
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
                        contentText = reportLog.content,
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
                reportEnabled = reportButtonEnabled,
                onClickCancel = {
                    setShowExitDialog(true)
                },
                onClickReport = {
                    navigateToSendReport()
                },
                modifier = Modifier
            )
        }
    }
}