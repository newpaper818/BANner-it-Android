package com.fitfit.feature.more.account

import androidx.lifecycle.ViewModel
import com.fitfit.core.model.enums.ProviderId
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
//    private val userRepository: UserRepository,
): ViewModel() {


    suspend fun signOut(
        providerIdList: List<ProviderId>,
        signOutResult: (isSignOutSuccess: Boolean) -> Unit
    ){
        //TODO
//        userRepository.signOut(
//            providerIdList = providerIdList,
//            signOutResult = signOutResult
//        )
    }
}