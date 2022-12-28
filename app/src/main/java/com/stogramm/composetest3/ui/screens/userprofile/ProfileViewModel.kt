package com.stogramm.composetest3.ui.screens.userprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.domain.use_case.get_posts_use_case.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getPosts: GetPostsUseCase) : ViewModel() {


    fun getPosts() {
//        getPosts(GetPostsUseCase.PostOption.UserPosts).onEach {
//
//        }.launchIn(viewModelScope)
    }


}