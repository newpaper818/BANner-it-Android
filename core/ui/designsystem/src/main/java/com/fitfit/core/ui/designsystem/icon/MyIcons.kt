package com.fitfit.core.ui.designsystem.icon

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.FormatListBulleted
import androidx.compose.material.icons.automirrored.rounded.Login
import androidx.compose.material.icons.automirrored.rounded.ManageSearch
import androidx.compose.material.icons.automirrored.rounded.NavigateNext
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.outlined.HideImage
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.CloudOff
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material.icons.rounded.East
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.FileDownload
import androidx.compose.material.icons.rounded.Flag
import androidx.compose.material.icons.rounded.FlipCameraAndroid
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.MoreTime
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.MyLocation
import androidx.compose.material.icons.rounded.NoPhotography
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.material.icons.rounded.OutlinedFlag
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material.icons.rounded.QrCode
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Route
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.R
import com.fitfit.core.ui.designsystem.theme.CustomColor

data class MyIcon(
    val imageVector: ImageVector,
    val size: Dp = 22.dp,
    val isGray: Boolean = false,
    val color: Color? = null,  /**if null, set [color] to Material.colors.onSurface, onBackground...*/
    @StringRes val descriptionTextId: Int? = null
)





object NavigationBarIcon {
    val reportFilled = MyIcon(Icons.Rounded.Flag,               24.dp, false, null, R.string.report)
    val reportOutlined = MyIcon(Icons.Rounded.OutlinedFlag,     24.dp, false, null, R.string.report)

    val lookupFilled = MyIcon(Icons.AutoMirrored.Rounded.ManageSearch,      28.dp, false, null, R.string.lookup)
    val lookupOutlined = MyIcon(Icons.AutoMirrored.Rounded.ManageSearch,    28.dp, false, null, R.string.lookup)

    val myRecordsFilled = MyIcon(Icons.AutoMirrored.Rounded.FormatListBulleted,     24.dp, false, null, R.string.my_records)
    val myRecordsOutlined = MyIcon(Icons.AutoMirrored.Rounded.FormatListBulleted,   24.dp, false, null, R.string.my_records)

    val moreFilled = MyIcon(Icons.Rounded.MoreHoriz,      24.dp, false, null, R.string.more)
    val moreOutlined = MyIcon(Icons.Outlined.MoreHoriz,   24.dp, false, null, R.string.more)
}

object TopAppBarIcon {
    val back = MyIcon(Icons.AutoMirrored.Rounded.ArrowBack,     22.dp, false, null, R.string.back)
    val edit = MyIcon(Icons.Rounded.Edit,                       22.dp, false, null, R.string.edit)
    val close = MyIcon(Icons.Rounded.Close,                     22.dp, false, null, R.string.close)
    val more = MyIcon(Icons.Rounded.MoreVert,                   22.dp, false, null, R.string.more_options)
    val downloadImage = MyIcon(Icons.Rounded.FileDownload,      22.dp, false, CustomColor.white, R.string.download_image)
    val closeImageScreen = MyIcon(Icons.Rounded.Close,          22.dp, false, CustomColor.white, R.string.close)
}

object IconTextButtonIcon {
    val qrCode = MyIcon(Icons.Rounded.QrCode,                 24.dp, false, null, null)
    val add = MyIcon(Icons.Rounded.Add,                       24.dp, false, null, R.string.example)
    val delete = MyIcon(Icons.Rounded.Delete,                 24.dp, false, null, R.string.example)
    val leftArrow = MyIcon(Icons.Rounded.KeyboardArrowLeft,   30.dp, false, null, R.string.example)
    val rightArrow = MyIcon(Icons.Rounded.KeyboardArrowRight, 30.dp, false, null, R.string.example)
}

object IconButtonIcon {
    val stop = MyIcon(Icons.Rounded.Close,     34.dp, false, null, R.string.stop)
    val pause = MyIcon(Icons.Rounded.Pause,    30.dp, false, null, R.string.pause)

    val minus = MyIcon(Icons.Rounded.Remove,   30.dp, false, null, R.string.minus)
    val plus = MyIcon(Icons.Rounded.Add,       30.dp, false, null, R.string.plus)

    val camera = MyIcon(Icons.Rounded.CameraAlt,        30.dp, false, null, null)
    val gallery = MyIcon(Icons.Rounded.PhotoLibrary,    30.dp, false, null, null)

    val map = MyIcon(Icons.Rounded.Map,                 30.dp, false, null, null)
    val currentLocation = MyIcon(Icons.Rounded.MyLocation,   30.dp, false, null, null)

    val closeCamera = MyIcon(Icons.Rounded.Close,          30.dp, false, CustomColor.white, R.string.close)
}

object FabIcon {
    val add = MyIcon(Icons.Rounded.Add, 24.dp, false, null, R.string.example)
    val map = MyIcon(Icons.Rounded.Map, 22.dp, false, null, R.string.example)
}






object MyIcons {

    //error
    val error = MyIcon(Icons.Rounded.ErrorOutline,          80.dp, false, null, R.string.error)

    //check
    val check = MyIcon(Icons.Rounded.CheckCircleOutline,    80.dp, false, null, R.string.completed)


    //sign in screen
    val signIn = MyIcon(Icons.AutoMirrored.Rounded.Login,       36.dp, true, null, R.string.sign_in)
    val internetUnavailable = MyIcon(Icons.Rounded.CloudOff,    40.dp, true, null, R.string.internet_unavailable)
    val internetUnavailableWhite = MyIcon(Icons.Rounded.CloudOff, 40.dp, false, Color.White, R.string.internet_unavailable)

    //no item
    val noReportRecord = MyIcon(Icons.Rounded.EditNote,    40.dp, true, null, null)

    //edit profile
    val changeProfileImage = MyIcon(Icons.Rounded.Image,    24.dp, false, null, null)
    val deleteProfileImage = MyIcon(Icons.Rounded.Delete,   24.dp, false, null, null)

    //image card
    val deleteImage = MyIcon(Icons.Rounded.Close,               16.dp, false, null, R.string.delete_image)
    val imageLoadingError = MyIcon(Icons.Rounded.ErrorOutline,  36.dp, false, null, R.string.image_loading_error)
    val noImage = MyIcon(Icons.Outlined.HideImage,              36.dp, true, null, R.string.no_image)

    //search / text input
    val searchLocation = MyIcon(Icons.Rounded.Search,   24.dp, false, null, R.string.example)
    val searchFriend = MyIcon(Icons.Rounded.Search,     24.dp, false, null, R.string.example)
    val clearInputText = MyIcon(Icons.Rounded.Close,    22.dp, false, null, R.string.example)

    //item expand collapse
    val expand = MyIcon(Icons.Rounded.ExpandMore,   22.dp, true, null, R.string.expand)
    val collapse = MyIcon(Icons.Rounded.ExpandLess, 22.dp, true, null, R.string.collapse)

    //
    val delete = MyIcon(Icons.Rounded.Delete,            22.dp, true, null, R.string.example)
    val deleteSpot = MyIcon(Icons.Rounded.Delete,        22.dp, true, null, R.string.example)
    val deleteStartTime = MyIcon(Icons.Rounded.Delete,   22.dp, true, null, R.string.example)
    val deleteEndTime = MyIcon(Icons.Rounded.Delete,     22.dp, true, null, R.string.example)
    val dragHandle = MyIcon(Icons.Rounded.DragHandle,    22.dp, true, null, R.string.example)
    val clickableItem = MyIcon(Icons.AutoMirrored.Rounded.NavigateNext,    22.dp, true, null, null)


    //set color
    val setColor = MyIcon(Icons.Outlined.Palette,   24.dp, false, null, R.string.example)
    val selectedColor = MyIcon(Icons.Rounded.Done,  22.dp, false, null, R.string.example)


    //invited friend
    val viewOnly = MyIcon(Icons.Rounded.Visibility,     24.dp, true, null, R.string.example)
    val allowEdit = MyIcon(Icons.Rounded.Edit,          24.dp, true, null, R.string.example)

    //workout
    val noCamera = MyIcon(Icons.Rounded.NoPhotography,          40.dp, false, null, null)
    val flipCamera = MyIcon(Icons.Rounded.FlipCameraAndroid,    30.dp, false, null, R.string.flip_camera)

    //date time
    val date = MyIcon(Icons.Rounded.CalendarMonth,      22.dp, true, null, R.string.example)
    val time = MyIcon(Icons.Rounded.AccessTime,         20.dp, true, null, R.string.example)
    val setTime = MyIcon(Icons.Rounded.MoreTime,        20.dp, true, null, R.string.example)
    val rightArrowTo = MyIcon(Icons.Rounded.East,       22.dp, true, null, R.string.example)
    val rightArrowToSmall = MyIcon(Icons.Rounded.East,  18.dp, true, null, R.string.example)

    //set time dialog
    val switchToTextInput = MyIcon(Icons.Outlined.Keyboard,   22.dp, true, null, R.string.example)
    val switchToTouchInput = MyIcon(Icons.Rounded.Schedule,   22.dp, true, null, R.string.example)

    //information card
    val category = MyIcon(Icons.Rounded.Bookmarks,   20.dp, true, null, R.string.example)
    val budget = MyIcon(Icons.Rounded.Payments,      22.dp, true, null, R.string.example)
    val travelDistance = MyIcon(Icons.Rounded.Route, 20.dp, true, null, R.string.example)

    //setting
    val openInNew = MyIcon(Icons.Rounded.OpenInNew,     22.dp, true, null, R.string.open_in_new)
    val sendEmail = MyIcon(Icons.AutoMirrored.Rounded.Send,     22.dp, true, null, R.string.example)
}