package com.stogramm.composetest3.ui.screens.userprofile

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.models.Post
import com.ironmeddie.domain.usecases.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(private val logOutUseCase: LogOutUseCase, private val getUserInfoUseCase: GetUserInfoUseCase) :ViewModel() {


    var list = mutableStateListOf<Post>()


    init {
        getUserInfo()
    }

    fun logOut(){
        viewModelScope.launch {
            logOutUseCase.execute()
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            list.addAll(getUserInfoUseCase())
        }
    }
}