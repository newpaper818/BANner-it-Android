package com.fitfit.feature.more.account

import androidx.lifecycle.ViewModel
import com.fitfit.core.data.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
): ViewModel() {


    suspend fun signOut(
        signOutResult: (isSignOutSuccess: Boolean) -> Unit
    ){
        preferencesRepository.saveJwtPreference(null)
        preferencesRepository.getJwtPreference(
            onGet = { jwt ->
                if (jwt == null)
                    signOutResult(true)
                else
                    signOutResult(false)
            }
        )
    }
}