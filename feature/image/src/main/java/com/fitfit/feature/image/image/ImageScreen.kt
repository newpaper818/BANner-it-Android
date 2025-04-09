package com.fitfit.feature.image.image

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.fitfit.core.ui.designsystem.components.ImageFromFile
import com.fitfit.core.ui.designsystem.components.ImageFromUrl
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.ImageTopAppBar
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import com.fitfit.core.ui.designsystem.theme.CustomColor
import com.fitfit.feature.image.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ImageRoute(
    imageUerId: Int,
    internetEnabled: Boolean,
    images: List<String>,
    initialImageIndex: Int,

    navigateUp: () -> Unit,
    downloadImage: (imagePath: String, imageUserId: String, result: (Boolean) -> Unit) -> Unit,
    saveImageToExternalStorage: (imageFileName: String) -> Boolean,

    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()

    val downloadCompleteText = stringResource(id = R.string.toast_download_complete)
    val downloadErrorText = stringResource(id = R.string.toast_download_error)

    val onClickBack = {
        if (!systemUiController.isSystemBarsVisible)
            systemUiController.isSystemBarsVisible = true
        navigateUp()
    }

    BackHandler {
        onClickBack()
    }

    var showImageOnly by rememberSaveable { mutableStateOf(false) }

    val pageState = rememberPagerState(
        initialPage = initialImageIndex,
        pageCount = { images.size }
    )

    ImageScreen(
        imageUerId = imageUerId,
        internetEnabled = internetEnabled,
        pageState = pageState,
        images = images,
        showImageOnly = showImageOnly,
        onClickBack = onClickBack,
        onClickImage = {
            showImageOnly = !showImageOnly
            systemUiController.isSystemBarsVisible = !showImageOnly
        },
        onClickDownloadImage = {
            val saveResult = saveImageToExternalStorage(images[pageState.currentPage])

            if (saveResult) Toast.makeText(context, downloadCompleteText, Toast.LENGTH_SHORT).show()
            else            Toast.makeText(context, downloadErrorText, Toast.LENGTH_SHORT).show()
        },
        downloadImage = downloadImage,
        modifier = modifier
    )
}


@Composable
private fun ImageScreen(
    imageUerId: Int,
    internetEnabled: Boolean,

    pageState: PagerState,
    images: List<String>,
    showImageOnly: Boolean,

    onClickBack: () -> Unit,
    onClickImage: () -> Unit,
    onClickDownloadImage: (imageFileName: String) -> Unit,
    downloadImage: (imagePath: String, imageUserId: String, result: (Boolean) -> Unit) -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        modifier = modifier,
        topBar = {
            ImageTopAppBar(
                visible = !showImageOnly,
                title = "${pageState.currentPage + 1} / ${images.size}",
                navigationIconOnClick = { onClickBack() },
                actionIcon1Onclick = { onClickDownloadImage(images[pageState.currentPage]) }
            )
        },
        bottomBar = {

        }
    ) { _ ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomColor.imageBackground),
            contentAlignment = Alignment.Center
        ) {
            HorizontalPager(
                state = pageState,
                beyondViewportPageCount = 3,
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                pageContent = {

                    //each image(page) have zoom state
                    val zoomState = rememberZoomState(maxScale = 6f)

                    LaunchedEffect(pageState.currentPage) {
                        zoomState.reset()
                    }

                    //why using this? BoxWithConstraints
                    BoxWithConstraints(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = onClickImage
                            )
                    ) {

                        val a = this

                        if ("https" in images[it]) {
                            ImageFromUrl(
                                imageUrl = images[it],
                                contentDescription = stringResource(id = R.string.image),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .zoomable(
                                        zoomState = zoomState,
//                                    onTap = { onClickImage() }
                                    ),
                                contentScale = ContentScale.Fit,
                            )
                        }
                        else {
                            ImageFromFile(
                                internetEnabled = internetEnabled,
                                imageUserId = imageUerId,
                                imageFileName = images[it],
                                contentDescription = stringResource(id = R.string.image),
                                downloadImage = { _, _, _ -> },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .zoomable(
                                        zoomState = zoomState,
//                                    onTap = { onClickImage() }
                                    ),
                                contentScale = ContentScale.Fit,
                                isImageScreen = true,
//                            onClick = onClickImage
                            )
                        }
                    }
                }
            )
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
@PreviewLightDark
private fun ImageScreenPreview(){
    BannerItTheme {
        MyScaffold {
            ImageScreen(
                imageUerId = 1,
                internetEnabled = true,
                pageState = rememberPagerState { 1 },
                images = listOf("", ""),
                showImageOnly = false,
                onClickBack = { },
                onClickImage = { },
                onClickDownloadImage = { },
                downloadImage = {_,_,_ -> }
            )
        }
    }
}



