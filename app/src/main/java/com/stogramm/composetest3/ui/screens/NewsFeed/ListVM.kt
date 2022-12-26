package com.stogramm.composetest3.ui.screens.NewsFeed

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.remote.FirebaseAuthApp
import com.ironmeddie.data.domain.use_case.get_posts_use_case.GetPostsUseCase
import com.ironmeddie.data.domain.use_case.get_posts_use_case.PostWithAuthor
import com.ironmeddie.data.domain.use_case.like_use_case.GetLikesUseCase
import com.ironmeddie.data.domain.use_case.like_use_case.LikeUseCase
import com.ironmeddie.data.models.Post
import com.ironmeddie.data.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListVM @Inject constructor(
    private val cauth: FirebaseAuthApp,
    private val getPosts: GetPostsUseCase,
    private val likeUseCase: LikeUseCase,
    private val getLikesUseCase: GetLikesUseCase

    ) : ViewModel() {
    private var _tasks = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val tasks get() = _tasks

    var loginState: Boolean? by mutableStateOf(null)
        private set

    private var job: Job? = null

    init {
        checkAuth()
        getNews()
    }


    fun checkAuth() {
        viewModelScope.launch {
            loginState = cauth.checkAuth()
        }
    }

    fun remove(item: Post) {
//        _tasks.remove(item)
    }


    fun liked(item: Post) {
        viewModelScope.launch {
            likeUseCase(item.id)
        }
        getNews()
    }


    fun getNews() {
        if (loginState == true) {
            job?.cancel()
            job = getPosts().onEach {
                _tasks.value = MainScreenState.Success(data = it)
            }.catch { e ->
                _tasks.value = MainScreenState.Error(e.message.toString())
            }.launchIn(viewModelScope)
        } else {
            _tasks.value = MainScreenState.Error("login state is not true")
        }
    }


    fun getLikes(postId: String){
        getLikesUseCase(postId).onEach {
            Log.d("checkCode", it.toString())
        }.launchIn(viewModelScope)
    }
}


sealed class MainScreenState {
    object Loading : MainScreenState()
    data class Success(val data: List<PostWithAuthor>) : MainScreenState()
    data class Error(val message: String) : MainScreenState()
}

