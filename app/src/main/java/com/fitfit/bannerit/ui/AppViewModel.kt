package com.fitfit.bannerit.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fitfit.bannerit.navigation.TopLevelDestination
import com.fitfit.core.data.data.repository.PreferencesRepository
import com.fitfit.core.data.data.repository.SplashRepository
import com.fitfit.core.model.data.DateTimeFormat
import com.fitfit.core.model.data.Theme
import com.fitfit.core.model.data.UserData
import com.fitfit.core.model.enums.ScreenDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val APP_VIEWMODEL_TAG = "App-ViewModel"

data class AppPreferencesState(
    val theme: Theme = Theme(),
    val dateTimeFormat: DateTimeFormat = DateTimeFormat()
)

data class DestinationState(
    val startScreenDestination: ScreenDestination? = null, //if not null, splash screen will be finish
    val moreDetailStartScreenDestination: ScreenDestination = ScreenDestination.SET_DATE_TIME_FORMAT,
    val currentTopLevelDestination: TopLevelDestination = TopLevelDestination.REPORT,
    val currentScreenDestination: ScreenDestination = ScreenDestination.SIGN_IN
)

data class AppUiState(
    val appUserData: UserData? = null,
    val appPreferences: AppPreferencesState = AppPreferencesState(),
    val screenDestination: DestinationState = DestinationState(),

    val showSplashScreen: Boolean = true,
    val firstLaunch: Boolean = true,
)



@HiltViewModel
class AppViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    private val splashRepository: SplashRepository,
): ViewModel() {
    private val _jwt = MutableStateFlow(null as String?)
    private val jwt = _jwt.asStateFlow()

    private val _appUiState = MutableStateFlow(AppUiState())
    val appUiState = _appUiState.asStateFlow()

    //==============================================================================================
    //update app setting values - at app start =====================================================
    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAppPreferencesValue()
        }
    }

    suspend fun getAppPreferencesValue(){

        //get app preferences from local db
        preferencesRepository.getAppPreferencesValue { theme, dateTimeFormat ->
            _appUiState.update {
                it.copy(
                    appPreferences = it.appPreferences.copy(
                        theme = theme,
                        dateTimeFormat = dateTimeFormat
                    )
                )
            }
        }
    }









    //==============================================================================================
    //set uiState value ============================================================================
    fun firstLaunchToFalse() {
        _appUiState.update {
            it.copy(firstLaunch = false)
        }
    }






    //==============================================================================================
    //at sign in screen ============================================================================
    fun initAppUiState(

    ){
        _appUiState.update {
            it.copy(
                appUserData = null,
                firstLaunch = true
            )
        }
    }









    //==============================================================================================
    //at app start splash screen ===================================================================
    fun intiUserAndUpdateStartDestination (
        internetEnabled: Boolean,
    ){
        Log.d("MainActivity1", "[1] intiUserAndUpdateStartDestination start")

        initSignedInUser(
            internetEnabled = internetEnabled,
            onDone = {
                updateCurrentScreenDestination()
            }
        )
        Log.d("MainActivity1", "                              [1]intiUserAndUpdateStartDestination done")
    }

    private fun initSignedInUser(
        internetEnabled: Boolean,
        onDone: () -> Unit
    ){
        Log.d("MainActivity1", "[2] initSignedInUser start")


        viewModelScope.launch {
//            val time = measureNanoTime {
//            val userData = userRepository.getSignedInUser()
//            userData = null


            //send jwt to server, get user data

            //get jwt from local db
            preferencesRepository.getJwtPreference { jwt ->
                _jwt.update { jwt }
            }

            val jwt = jwt.value
            var newUserData: UserData? = null

            //not signed in
            if (jwt == null || jwt == ""){
                newUserData = null
            }

            //signed in -> get jwt, user data
            else if (internetEnabled){
                //gwt user data with jwt
                val jwtAndUserData = splashRepository.getUserData(jwt = jwt)

                if (jwtAndUserData == null){
                    preferencesRepository.saveJwtPreference(null)
                    newUserData = null
                }
                else {
                    val (newJwt, newUserData1) = jwtAndUserData
                    preferencesRepository.saveJwtPreference(newJwt)
                    _jwt.update { newJwt }
                    newUserData = newUserData1
                }
            }

            _appUiState.update {
                it.copy(appUserData = newUserData)
            }

            onDone()

            Log.d("MainActivity1", "[2] initSignedInUser - user: ${newUserData?.userId}")
//            }
//            Log.d("MainActivity1", "[2] ${time*0.000000001} - initSignedInUser")
        }
    }










    //==============================================================================================
    //update screen destination ====================================================================
    fun updateCurrentScreenDestination(

    ){
        _appUiState.update {
            it.copy(
                screenDestination = it.screenDestination.copy(
                    startScreenDestination = ScreenDestination.MAIN_REPORT
                )
            )
        }
        Log.d("MainActivity1", "[3] update screen destination: ScreenDestination.MAIN_REPORT")
    }

    fun updateMoreDetailCurrentScreenDestination(
        screenDestination: ScreenDestination
    ){
        _appUiState.update {
            it.copy(
                screenDestination = it.screenDestination.copy(
                    moreDetailStartScreenDestination = screenDestination
                )
            )
        }
    }

    fun updateCurrentTopLevelDestination(topLevelDestination: TopLevelDestination){
        _appUiState.update {
            it.copy(
                screenDestination = it.screenDestination.copy(
                    currentTopLevelDestination = topLevelDestination
                )
            )
        }
    }

    fun updateCurrentScreenDestination(
        screenDestination: ScreenDestination
    ) {

        when (screenDestination) {
            ScreenDestination.MAIN_REPORT -> {
                _appUiState.update {
                    it.copy(
                        screenDestination = it.screenDestination.copy(
                            currentTopLevelDestination = TopLevelDestination.REPORT,
                            currentScreenDestination = screenDestination
                        )
                    )
                }
            }
            ScreenDestination.MAIN_MY_RECORDS -> {
                _appUiState.update {
                    it.copy(
                        screenDestination = it.screenDestination.copy(
                            currentTopLevelDestination = TopLevelDestination.MY_RECORDS,
                            currentScreenDestination = screenDestination
                        )
                    )
                }
            }
            else -> {
                _appUiState.update {
                    it.copy(
                        screenDestination = it.screenDestination.copy(
                            currentScreenDestination = screenDestination
                        )
                    )
                }
            }
        }
    }




    //==============================================================================================
    //update user data =============================================================================
    fun updateUserData(
        userData: UserData?
    ) {
        _appUiState.update {
            it.copy(
                appUserData = userData
            )
        }
    }
}