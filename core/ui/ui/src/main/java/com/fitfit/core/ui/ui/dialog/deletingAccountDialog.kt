package com.fitfit.core.ui.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.utils.CircularLoadingIndicator
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.designsystem.theme.BannerItTheme
import com.fitfit.core.ui.ui.R
import com.fitfit.core.ui.ui.dialog.component.DIALOG_DEFAULT_WIDTH

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletingAccountDialog(
    modifier: Modifier = Modifier,
    width: Dp? = DIALOG_DEFAULT_WIDTH,
){
    val columnModifier =
        if (width != null) modifier
            .width(width)
            .padding(16.dp)
        else               modifier.padding(16.dp)


    BasicAlertDialog(
        onDismissRequest = { },
        modifier = modifier,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = columnModifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                //title text / align center

                Row(
                    modifier = Modifier.fillMaxWidth().padding(12.dp, 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CircularLoadingIndicator()

                    MySpacerRow(width = 16.dp)

                    Text(
                        text = stringResource(id = R.string.deleting_account),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}























@Composable
@PreviewLightDark
private fun Preview_DeletingAccountDialog(){
    BannerItTheme {
        MyScaffold {
            DeletingAccountDialog()
        }
    }
}