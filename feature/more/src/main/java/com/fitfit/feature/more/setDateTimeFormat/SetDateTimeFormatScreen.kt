package com.fitfit.feature.more.setDateTimeFormat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.enums.DateFormat
import com.fitfit.core.model.enums.TimeFormat
import com.fitfit.core.ui.designsystem.components.topAppBar.MyTopAppBar
import com.fitfit.core.ui.designsystem.icon.TopAppBarIcon
import com.fitfit.core.ui.ui.TimeFormatSegmentedButtons
import com.fitfit.core.ui.ui.item.ItemDivider
import com.fitfit.core.ui.ui.item.ItemWithRadioButton
import com.fitfit.core.ui.ui.item.ItemWithSwitch
import com.fitfit.core.ui.ui.item.ListGroupCard
import com.fitfit.core.utils.itemMaxWidthSmall
import com.fitfit.feature.more.R
import com.fitfit.feature.more.setDateTimeFormat.component.UpperDateTimeExampleBox
import kotlinx.coroutines.launch

@Composable
fun SetDateTimeFormatRoute(
    use2Panes: Boolean,
    spacerValue: Dp,
    dateTimeFormat: DateTimeFormat,
    updatePreferencesValue: () -> Unit,

    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,

    setDateTimeFormatViewModel: SetDateTimeFormatViewModel = hiltViewModel()
) {
    val setDateTimeFormatUiState by setDateTimeFormatViewModel.setDateTimeFormatUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        setDateTimeFormatViewModel.updateDateTimeExample(dateTimeFormat)
    }

    SetDateTimeFormatScreen(
        startSpacerValue = if (use2Panes) spacerValue / 2 else spacerValue,
        endSpacerValue = spacerValue,
        dateTimeFormat = dateTimeFormat,
        dateExampleText = setDateTimeFormatUiState.dateExample,
        timeExampleText = setDateTimeFormatUiState.timeExample,
        saveUserPreferences = { timeFormat, useMonthName, includeDayOfWeek, dateFormat ->
            coroutineScope.launch {
                //save to dataStore
                setDateTimeFormatViewModel.saveDateTimeFormatUserPreferences(
                    dateFormat,
                    useMonthName,
                    includeDayOfWeek,
                    timeFormat
                )

                //update preferences at appViewModel
                updatePreferencesValue()

                //update date time example text
                setDateTimeFormatViewModel.updateDateTimeExample(
                    DateTimeFormat(
                        timeFormat = timeFormat ?: dateTimeFormat.timeFormat,
                        useMonthName = useMonthName ?: dateTimeFormat.useMonthName,
                        includeDayOfWeek = includeDayOfWeek ?: dateTimeFormat.includeDayOfWeek,
                        dateFormat = dateFormat ?: dateTimeFormat.dateFormat
                    )
                )
            }
        },
        navigateUp = navigateUp,
        modifier = modifier,
        use2Panes = use2Panes
    )
}

@Composable
private fun SetDateTimeFormatScreen(
    startSpacerValue: Dp,
    endSpacerValue: Dp,
    dateTimeFormat: DateTimeFormat,

    dateExampleText: String,
    timeExampleText: String,

    saveUserPreferences: (timeFormat: TimeFormat?, useMonthName: Boolean?,
                          includeDayOfWeek: Boolean?, dateFormat: DateFormat?) -> Unit,

    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    use2Panes: Boolean
){
    val dateFormat = dateTimeFormat.dateFormat
    val useMonthName = dateTimeFormat.useMonthName
    val includeDayOfWeek = dateTimeFormat.includeDayOfWeek
    val timeFormat = dateTimeFormat.timeFormat

    val dateFormatList = enumValues<DateFormat>()

    val scaffoldModifier = if (use2Panes) modifier
    else modifier.navigationBarsPadding()

    Scaffold(
        modifier = scaffoldModifier,
        contentWindowInsets = WindowInsets(bottom = 0),

        topBar = {
            MyTopAppBar(
                startPadding = startSpacerValue,
                title = stringResource(id = R.string.date_time_format),
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
        ) {
            //current date
            item {
                UpperDateTimeExampleBox(
                    dateText = dateExampleText,
                    timeText = timeExampleText,
                    modifier = itemModifier
                )
            }

            //select time format 12h/24h
            item {
                ListGroupCard(
                    title = stringResource(id = R.string.time_format),
                    cardColors = CardColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = itemModifier
                ) {
                    TimeFormatSegmentedButtons(
                        is24h = timeFormat == TimeFormat.T24H,
                        setTimeFormat = { newTimeFormat ->
                            if (timeFormat != newTimeFormat) {
                                saveUserPreferences(newTimeFormat, null, null, null)
                            }
                        }
                    )
                }
            }


            item {

                ListGroupCard(
                    title = stringResource(id = R.string.date_format),
                    modifier = itemModifier
                ) {
                    //use month name
                    ItemWithSwitch(
                        text = stringResource(id = R.string.use_month_name),
                        checked = useMonthName,
                        onCheckedChange = { newUseMonthName ->
                            if (useMonthName != newUseMonthName) {
                                saveUserPreferences(null, newUseMonthName, null, null)
                            }
                        }
                    )

                    //include day of month
                    ItemWithSwitch(
                        text = stringResource(id = R.string.include_day_of_week),
                        checked = includeDayOfWeek,
                        onCheckedChange = { newIncludeDayOfWeek ->
                            if (includeDayOfWeek != newIncludeDayOfWeek) {
                                saveUserPreferences(null, null, newIncludeDayOfWeek, null)
                            }
                        }
                    )


                    ItemDivider()

                    //select date format list / radio button
                    for (oneDateFormat in dateFormatList){
                        ItemWithRadioButton(
                            isSelected = dateFormat == oneDateFormat,
                            text = stringResource(id = oneDateFormat.textId),
                            onItemClick = {
                                if (dateFormat != oneDateFormat) {
                                    saveUserPreferences(null, null, null, oneDateFormat)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}