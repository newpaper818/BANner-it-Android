package com.fitfit.feature.more.setTheme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.model.data.Theme
import com.fitfit.core.model.enums.AppTheme
import com.fitfit.core.ui.designsystem.components.MyScaffold
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.ui.item.ItemWithRadioButton
import com.fitfit.core.ui.ui.item.ListGroupCard
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.more.R
import kotlinx.coroutines.launch

@Composable
fun SetThemeRoute(
    use2Panes: Boolean,
    spacerValue: Dp,
    theme: Theme,
    updatePreferencesValue: () -> Unit,

    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,

    setThemeViewModel: SetThemeViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    SetThemeScreen(
        startSpacerValue = if (use2Panes) spacerValue / 2 else spacerValue,
        endSpacerValue = spacerValue,
        theme = theme,
        saveUserPreferences = { appTheme ->
            coroutineScope.launch {
                //save to dataStore
                setThemeViewModel.saveThemePreferences(
                    appTheme
                )

                //update preferences at appViewModel
                updatePreferencesValue()
            }
        },
        navigateUp = navigateUp,
        modifier = modifier,
        use2Panes = use2Panes
    )
}

@Composable
private fun SetThemeScreen(
    startSpacerValue: Dp,
    endSpacerValue: Dp,
    theme: Theme,

    saveUserPreferences: (appTheme: AppTheme?) -> Unit,

    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    use2Panes: Boolean
){
    val appTheme = theme.appTheme

    val appThemeList = enumValues<AppTheme>()

    val scaffoldModifier = if (use2Panes) modifier
        else modifier.navigationBarsPadding()


    MyScaffold(
        modifier = scaffoldModifier,
        contentWindowInsets = WindowInsets(bottom = 0),

        topBar = {
            MyTopAppBar(
                title = stringResource(R.string.theme),
                navigationIcon = TopAppBarIcon.back,
                onClickNavigationIcon = { navigateUp() }
            )
        }
    ){ paddingValues ->
        val itemModifier = Modifier.widthIn(max = itemMaxWidthSmall)

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(startSpacerValue, 16.dp, endSpacerValue, 200.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            //set app theme
            item {
                ListGroupCard(
                    modifier = itemModifier
                ) {
                    for (oneAppTheme in appThemeList){
                        ItemWithRadioButton(
                            isSelected = appTheme == oneAppTheme,
                            text = stringResource(id = oneAppTheme.textId),
                            onItemClick = {
                                if (oneAppTheme != appTheme){
                                    saveUserPreferences(oneAppTheme)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}