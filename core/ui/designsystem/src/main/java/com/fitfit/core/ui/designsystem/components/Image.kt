package com.fitfit.core.ui.designsystem.components

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.fitfit.core.model.report.data.BannerInfo
import com.fitfit.core.ui.designsystem.R
import com.fitfit.core.ui.designsystem.components.utils.ClickableBox
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.icon.DisplayIcon
import com.fitfit.core.ui.designsystem.icon.MyIcons
import com.fitfit.core.ui.designsystem.theme.CustomColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ImageFromDrawable(
    @DrawableRes imageDrawable: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
){
    Image(
        painter = painterResource(id = imageDrawable),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}

@Composable
fun ImageFromUrl(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
){
    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }

    if (isLoading){
        OnLoadingImage()
    }

    if (isError){
        OnErrorImage()
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(300)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        onLoading = {
            isLoading = true
        },
        onSuccess = {
            isLoading = false
            isError = false
        },
        onError = {
            isLoading = false
            isError = true
        }
    )
}

@Composable
fun ImageFromUrlAndBannerBoxOverlay(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    bannersInfo: List<BannerInfo>? = null,
){
    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }

    val imageSizePx = remember(imageUrl) { mutableStateOf(IntSize.Zero) }
    val displaySizeDp = remember(imageUrl) { mutableStateOf(IntSize.Zero) }

    val aspectRatio = remember(imageUrl) { mutableFloatStateOf(3f / 4f) }

    val density = LocalDensity.current

    LaunchedEffect(imageUrl) {
        imageSizePx.value = getImageResolution(context, imageUrl) ?: IntSize.Zero
//        Log.d("aaa", "-- new image size(dp): $imageSizePx")

        aspectRatio.value = imageSizePx.value.width.toFloat() / imageSizePx.value.height
    }


    Box(
        modifier = Modifier
            .aspectRatio(aspectRatio.value)
            .onSizeChanged {
                val newWidth = with(density) { it.width.toDp().value }
                val newHeight = with(density) { it.height.toDp().value }

                val newSize = IntSize(newWidth.toInt(), newHeight.toInt())
                if (displaySizeDp.value != newSize) {
                    displaySizeDp.value = newSize
//                    Log.d("aaa", "-- new display size(dp): $displaySizeDp")
                }
            }
    ) {
        if (isLoading){
            OnLoadingImage()
        }

        if (isError){
            OnErrorImage()
        }

        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(300)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
            onLoading = {
                isLoading = true
            },
            onSuccess = {
                isLoading = false
                isError = false
            },
            onError = {
                isLoading = false
                isError = true
            }
        )

        // Banner box overlay
        if(
            !bannersInfo.isNullOrEmpty()
            && imageSizePx.value.width >= 1 && imageSizePx.value.height >= 1
            && displaySizeDp.value.width >= 1 && displaySizeDp.value.height >= 1) {

            BannerBoxOverlay(
                displaySizeDp = displaySizeDp,
                imageSizePx = imageSizePx,
                bannersInfo = bannersInfo
            )
        }
    }
}

@Composable
private fun BannerBoxOverlay(
    displaySizeDp: MutableState<IntSize>,
    imageSizePx: MutableState<IntSize>,
    bannersInfo: List<BannerInfo>
){
//    Log.d("aaa", "image size: $imageSizePx , display size: $displaySizeDp")

    val scaleX = displaySizeDp.value.width.toFloat() / imageSizePx.value.width
    val scaleY = displaySizeDp.value.height.toFloat() / imageSizePx.value.height

    bannersInfo.forEach { bannerInfo ->
        var show by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
//            delay(1000)
            show = true
        }

        val center = bannerInfo.center
        val width = bannerInfo.width
        val height = bannerInfo.height

//        Log.d("aaa", "    box center: ${center}, width: $width, height: $height")

        if (center != null && width != null && height != null) {
            val scaledCenterX = center[0] * scaleX
            val scaledCenterY = center[1] * scaleY
            val scaledWidth = width * scaleX
            val scaledHeight = height * scaleY

//                    Log.d("aaa", "    scaledCenterX: ${scaledCenterX}, scaledCenterY: ${scaledCenterY}")
//                    Log.d("aaa", "    scaledWidth: ${scaledWidth}, scaledHeight: ${scaledHeight}")

            // top-left coordinates
            val startX = (scaledCenterX - (scaledWidth / 2)).coerceIn(0f, displaySizeDp.value.width.toFloat())
            val startY = (scaledCenterY - (scaledHeight / 2)).coerceIn(0f, displaySizeDp.value.height.toFloat())
            val startOffset = DpOffset(startX.dp, startY.dp)

//                    Log.d("aaa", "    startX: ${startX}, startY: ${startY}")

            Box(
                modifier = Modifier
                    .offset(startOffset.x, startOffset.y)
            ) {

                AnimatedVisibility(
                    visible = show,
                    enter = fadeIn(tween(500)) + scaleIn(tween(1000, delayMillis = 200)),
                    exit = fadeOut(tween(300)) + scaleOut(tween(300))
                ) {
                    Box(
                        modifier = Modifier
                            .size(
                                width = scaledWidth.dp.coerceAtMost((displaySizeDp.value.width - startX).dp),
                                height = scaledHeight.dp.coerceAtMost((displaySizeDp.value.height - startY).dp)
                            )
                            .border(2.dp, bannerInfo.status.color, RoundedCornerShape(4.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(topStart = 4.dp, bottomEnd = 4.dp))
                                .background(bannerInfo.status.color)
                        ) {
                            Text(
                                text = bannerInfo.bannerId.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = bannerInfo.status.textColor,
                                modifier = Modifier.padding(horizontal = 3.dp, vertical = 0.5.dp)
                            )
                        }
                    }
                }
            }

        }
    }
}

suspend fun getImageResolution(
    context: Context,
    imageUrl: String
): IntSize? {
    val imageLoader = ImageLoader(context)

    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .size(Size.ORIGINAL)
        .build()

    val result = imageLoader.execute(request)

    val drawable = (result.drawable ?: return null)
    return IntSize(drawable.intrinsicWidth, drawable.intrinsicHeight)
}

@Composable
fun ImageFromUrlForReportListItem(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
){
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }

    var currentImagePainter by remember { mutableStateOf<Painter?>(null) }

    when {
        isError -> {
            OnErrorImage()
        }

        !isError && currentImagePainter != null -> {
            Image(
                painter = currentImagePainter!!,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }

        isLoading -> {
            OnLoadingImage()
        }
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(300)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        onLoading = {
            isLoading = true
        },
        onSuccess = {
            coroutineScope.launch {
                isError = false
                isLoading = false

                if (currentImagePainter == null)
                    delay(300)

                currentImagePainter = it.painter

            }
        },
        onError = {
            isLoading = false
            isError = true
        }
    )
}

@Composable
fun ImageFromFile(
    internetEnabled: Boolean,
    imageUserId: Int,
    imageFileName: String,
    contentDescription: String,
    downloadImage: (imagePath: String, imageUserId: Int, result: (Boolean) -> Unit) -> Unit,

    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    isImageScreen: Boolean = false,
    onClick: () -> Unit = { }
){
    val context = LocalContext.current
    val imageFile = File(context.filesDir, imageFileName)

    var imageFileExit by rememberSaveable { mutableStateOf(imageFile.exists()) }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var isError by rememberSaveable { mutableStateOf(false) }


    if (isLoading){
        OnLoadingImage(
            isImageScreen = isImageScreen
        )
    }

    if (isError){
        OnErrorImage(
            isImageScreen = isImageScreen,
            onClick = onClick
        )
    }

    if (imageFileExit) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(imageFile)
                .crossfade(true)
                .crossfade(300)
                .build(),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier,
            onLoading = {
                isLoading = true
            },
            onSuccess = {
                isLoading = false
                isError = false
            },
            onError = {
                isLoading = false
                isError = true
            }
        )
    }
    else {
        LaunchedEffect(internetEnabled) {
            if (internetEnabled){
                isLoading = true
                isError = false

                downloadImage(imageFileName, imageUserId
                ) {result ->
                    if (result) {
                        imageFileExit = true
                    }
                    else {
                        isError = true
                        isLoading = false
                    }
                }
            }
            else{
                isLoading = false
                isError = true
            }
        }
    }
}

@Composable
fun NoImage(
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceDim),
        contentAlignment = Alignment.Center,
    ){
        DisplayIcon(
            icon = MyIcons.noImage
        )
    }
}






















@Composable
private fun OnLoadingImage(
    isImageScreen: Boolean = false
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .shimmerEffect(isImageScreen = isImageScreen)
    )
}

@Composable
private fun OnErrorImage(
    isImageScreen: Boolean = false,
    onClick: () -> Unit = { }
){
    var boxSize by remember{
        mutableStateOf(IntSize.Zero)
    }

    val minBoxLengthDp = with(LocalDensity.current){
        Integer.min(boxSize.height, boxSize.width).toDp()
    }

    val boxColor = if (isImageScreen) CustomColor.imageBackground
                    else MaterialTheme.colorScheme.surfaceDim

    val onColor = if (isImageScreen) CustomColor.white
                    else MaterialTheme.colorScheme.onSurfaceVariant

    ClickableBox(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { boxSize = it },
        contentAlignment = Alignment.Center,
        containerColor = boxColor,
        enabled = isImageScreen,
        onClick = onClick
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DisplayIcon(
                icon = MyIcons.imageLoadingError,
                color = onColor
            )

            if (minBoxLengthDp > 160.dp) {
                MySpacerColumn(height = 8.dp)

                Text(
                    text = stringResource(id = R.string.image_loading_error),
                    color = onColor
                )
            }
        }
    }
}



fun Modifier.shimmerEffect(
    isImageScreen: Boolean = false
): Modifier = composed {
    var size by remember{
        mutableStateOf(IntSize.Zero)
    }

    val transition = rememberInfiniteTransition(label = "infinite_transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1200)
        ),
        label = "shimmer_effect"
    )

    val colors = if (isImageScreen){
        listOf(
            CustomColor.imageBackground,
            Color(0xFF1A1A1A),
            Color(0xFF1A1A1A),
            CustomColor.imageBackground
        )
    }
    else {
        listOf(
            MaterialTheme.colorScheme.surfaceBright,
            MaterialTheme.colorScheme.surfaceTint,
            MaterialTheme.colorScheme.surfaceTint,
            MaterialTheme.colorScheme.surfaceBright
        )
    }


    background(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}