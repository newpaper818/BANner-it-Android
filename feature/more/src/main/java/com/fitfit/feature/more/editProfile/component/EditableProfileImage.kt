package com.fitfit.feature.more.editProfile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fitfit.core.ui.designsystem.components.button.ChangeProfileImageButton
import com.fitfit.core.ui.designsystem.components.button.DeleteProfileImageButton
import com.fitfit.core.ui.designsystem.components.utils.MyCard
import com.fitfit.core.ui.designsystem.components.utils.MySpacerColumn
import com.fitfit.core.ui.designsystem.components.utils.MySpacerRow
import com.fitfit.core.ui.ui.card.ProfileImage
import com.fitfit.feature.more.R

@Composable
internal fun EditableProfileImage(
    internetEnabled: Boolean,
    userId: Int,
    profileImage: String?,
    downloadImage: (imagePath: String, profileUserId: String, (Boolean) -> Unit) -> Unit,
    onClickEditImage: () -> Unit,
    onClickDeleteImage: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Row {
            MySpacerRow(width = 16.dp)
            Text(
                text = stringResource(id = R.string.profile_image),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        MySpacerColumn(height = 6.dp)

        MyCard(
            shape = MaterialTheme.shapes.extraLarge
        ) {
            AutoLayoutTwoContents(
                content1 = {
                    ProfileImage(
                        profileUserId = userId,
                        internetEnabled = internetEnabled,
                        profileImagePath = profileImage,
                    )
                },
                content2 = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //edit button
                        ChangeProfileImageButton(
                            onClick = onClickEditImage,
                            enabled = false,
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 88.dp)
                        )

                        MySpacerRow(8.dp)

                        //delete button
                        DeleteProfileImageButton(
                            onClick = onClickDeleteImage,
//                            enabled = profileImage != null,
                            enabled = false,
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 88.dp)
                        )
                    }
                }
            )
        }
    }
}