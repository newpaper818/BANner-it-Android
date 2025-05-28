package com.fitfit.core.ui.ui.card.report

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.fitfit.core.model.report.data.BannerInfo
import com.fitfit.core.ui.designsystem.components.ImageFromFile
import com.fitfit.core.ui.designsystem.components.ImageFromUrl
import com.fitfit.core.ui.designsystem.components.ImageFromUrlAndBannerBoxOverlay
import com.fitfit.core.ui.designsystem.components.utils.ClickableBox
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.MyIcons
import com.fitfit.core.ui.designsystem.theme.CustomColor
import com.fitfit.core.ui.designsystem.theme.n70
import com.fitfit.core.ui.designsystem.theme.n80
import com.fitfit.core.ui.ui.R

const val MAX_IMAGE_COUNT = 3

/**
 *
 * @param images image preview url or image file name
 */
@Composable
fun ImageCard(
    imageUserId: Int,
    internetEnabled: Boolean,
    isEditMode: Boolean,
    images: List<String>,
    isImageCountOver: Boolean,

    onClickImage: (imageIndex: Int) -> Unit,
//    onAddImages: (List<String>) -> Unit,
    deleteImage: (String) -> Unit,
    isOverImage: (Boolean) -> Unit,

//    reorderImageList: (currentIndex: Int, destinationIndex: Int) -> Unit,
    downloadImage: (imagePath: String, imageUserId: Int, result: (Boolean) -> Unit) -> Unit,
    saveImageToInternalStorage: (index: Int, uri: Uri) -> String?,

    bannersInfo: List<BannerInfo>? = null,

    modifier: Modifier = Modifier
){

    // 3 or over
    val borderColor = if (isImageCountOver) CustomColor.outlineError
                        else Color.Transparent

    val modifier1 = if (isEditMode) modifier.sizeIn(maxHeight = 390.dp)
                    else modifier
                            .sizeIn(maxWidth = 650.dp)
//                        .sizeIn(maxWidth = 650.dp, maxHeight = 390.dp)



    AnimatedVisibility(
        visible = isEditMode || images.isNotEmpty(),
        enter = scaleIn(animationSpec = tween(0))
                + expandVertically(animationSpec = tween(0))
                + fadeIn(animationSpec = tween(0)),
        exit = scaleOut(animationSpec = tween(300))
                + shrinkVertically(animationSpec = tween(300))
                + fadeOut(animationSpec = tween(300))
    ) {

        Column(
            modifier = modifier
        ) {
            MyCard(
                shape = if (isEditMode && images.isNotEmpty()) MaterialTheme.shapes.extraLarge
                        else MaterialTheme.shapes.medium,
                modifier = modifier1
                    .fillMaxWidth()
                    .border(1.dp, borderColor, MaterialTheme.shapes.extraLarge)
            ) {
                Box {
                    Column {
                        //edit mode (image file name)
                        AnimatedVisibility(
                            visible = isEditMode,
                            enter = fadeIn(animationSpec = tween(0, 0)),
                            exit = fadeOut(animationSpec = tween(500, 0))
                        ) {

                            Column(modifier = Modifier.fillMaxWidth()) {

                                MySpacerColumn(height = 12.dp)

//                                val slideStates = remember {
//                                    mutableStateMapOf<String, SlideState>(
//                                        *imagePathList.map { it to SlideState.NONE }.toTypedArray()
//                                    )
//                                }

                                //if no image
                                if (images.isEmpty()) {
                                    Text(
                                        text = stringResource(id = R.string.no_photos),
                                        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                                        modifier = Modifier.padding(12.dp, 0.dp)
                                    )
                                }

                                //images
                                LazyRow(
                                    verticalAlignment = Alignment.CenterVertically,
                                    contentPadding = PaddingValues(12.dp, 0.dp, 0.dp, 0.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    itemsIndexed(images) { index, imageFileName ->

                                        key(images) {
//                                            val slideState =
//                                                slideStates[imagePath] ?: SlideState.NONE

                                            ImageWithDeleteIcon(
                                                userId = imageUserId,
                                                internetEnabled = internetEnabled,
                                                imageFileName = imageFileName,
                                                imagePathList = images,
                                                onClickImage = {
                                                    onClickImage(index)
                                                },
                                                onDeleteClick = {
                                                    deleteImage(imageFileName)
                                                },
                                                downloadImage = downloadImage,
//                                                slideState = slideState,
//                                                updateSlideState = { imageIndex, newSlideState ->
//                                                    slideStates[imagePathList[imageIndex]] =
//                                                        newSlideState
//                                                },
//                                                updateItemPosition = { currentIndex, destinationIndex ->
//                                                    //on drag end
//                                                    coroutineScope.launch {
//                                                        //reorder list
//                                                        reorderImageList(currentIndex, destinationIndex)
//
//                                                        //all slideState to NONE
//                                                        slideStates.putAll(imagePathList.associateWith { SlideState.NONE })
//                                                    }
//                                                }
                                            )
                                            MySpacerRow(width = 12.dp)
                                        }
                                    }
                                }

                                MySpacerColumn(height = 12.dp)
                            }
                        }
                    }

                    //not edit mode showing images (image preview url)
                    Column {
                        AnimatedVisibility(
                            visible = !isEditMode,
                            enter = expandVertically(tween(500)),
                            exit = shrinkVertically(tween(500))
                        ) {
                            val pageState = rememberPagerState { images.size }

                            ClickableBox(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.BottomCenter,
                                onClick = {
                                    onClickImage(pageState.currentPage)
                                }
                            ) {
                                HorizontalPager(
                                    state = pageState,
                                    beyondViewportPageCount = 3,
                                    pageContent = {
                                        if (bannersInfo.isNullOrEmpty()){
                                            ImageFromUrl(
                                                imageUrl = images[it],
                                                contentDescription = stringResource(id = R.string.photo),
                                                modifier = Modifier.fillMaxSize(),
                                            )
                                        }
                                        else{
                                            ImageFromUrlAndBannerBoxOverlay(
                                                imageUrl = images[it],
                                                contentDescription = stringResource(id = R.string.photo),
                                                modifier = Modifier.fillMaxSize(),
                                                bannersInfo = bannersInfo
                                            )
                                        }

                                    }
                                )

                                if (images.size != 1)
                                    ImageIndicateDots(
                                        pageCount = images.size,
                                        currentPage = pageState.currentPage
                                    )
                            }

                        }
                    }
                }
            }
        }
    }
}



@Composable
private fun ImageWithDeleteIcon(
    userId: Int,
    internetEnabled: Boolean,
    imageFileName: String,
    imagePathList: List<String>,
    onClickImage: () -> Unit,
    onDeleteClick: () -> Unit,
    downloadImage: (imagePath: String, imageUserId: Int, result: (Boolean) -> Unit) -> Unit,

//    slideState: SlideState,
//    updateSlideState: (tripIdx: Int, slideState: SlideState) -> Unit,
//    updateItemPosition: (currentIndex: Int, destinationIndex: Int) -> Unit
){

    var cardWidthInt: Int
    val cardWidthDp = 170.dp
    var spacerWidthInt: Int
    val spacerWidthDp = 16.dp

    with(LocalDensity.current){
        cardWidthInt = cardWidthDp.toPx().toInt()
        spacerWidthInt = spacerWidthDp.toPx().toInt()
    }


    var isDragged by remember { mutableStateOf(false) }

    val zIndex = if (isDragged) 1.0f else 0.0f

//    val horizontalTranslation by animateIntAsState(
//        targetValue = when (slideState){
//            SlideState.UP   -> -(cardWidthInt + spacerWidthInt)
//            SlideState.DOWN -> cardWidthInt + spacerWidthInt
//            else -> 0
//        },
//        label = "horizontal translation"
//    )

    //card x offset

    val scale by animateFloatAsState(
        targetValue = if (isDragged) 1.05f else 1f,
        label = "scale"
    )

    val dragModifier = Modifier
//        .offset { IntOffset(horizontalTranslation, 0) }
        .scale(scale)
        .zIndex(zIndex)

    MyCard(
        onClick = onClickImage,
        shape = MaterialTheme.shapes.medium,
        modifier = dragModifier
            .size(cardWidthDp)
//            .dragAndDropHorizontal(
//                item = imagePath,
//                items = imagePathList,
//                itemWidth = cardWidthInt + spacerWidthInt,
//                updateSlideState = updateSlideState,
//                onStartDrag = {
//                    isDragged = true
//                },
//                onStopDrag = { currentIndex, destinationIndex ->
//                    isDragged = false
//
//                    if (currentIndex != destinationIndex) {
//                        updateItemPosition(currentIndex, destinationIndex)
//                    }
//                },
//                isDraggedAfterLongPress = true
//            )

    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            ImageFromFile(
                internetEnabled = internetEnabled,
                imageUserId = userId,
                imageFileName = imageFileName,
                contentDescription = stringResource(id = R.string.photo),
                downloadImage = downloadImage,
                modifier = Modifier.fillMaxSize()
            )

            //delete icon
            Box(
                modifier = Modifier.padding(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable {
                            onDeleteClick()
                        }
                ) {
                    DisplayIcon(icon = MyIcons.deleteImage)
                }
            }
        }
    }
}

@Composable
private fun ImageIndicateDots(
    pageCount:Int,
    currentPage: Int
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .height(20.dp)
                .clip(CircleShape)
                .background(n70.copy(alpha = 0.4f)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) {

                //current image dot
                if (currentPage == it){
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .size(10.dp)
                    )
                }

                //other dots
                else{
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(10.dp)
                    ){
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(n80.copy(alpha = 0.7f))
                                .size(8.dp)
                        )
                    }
                }
            }
        }

        MySpacerColumn(height = 8.dp)
    }
}










