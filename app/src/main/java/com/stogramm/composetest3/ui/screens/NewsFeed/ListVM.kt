package com.stogramm.composetest3.ui.screens.NewsFeed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.remote.FirebaseAuthApp
import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.use_case.delete_post.DeletePostUseCase
import com.ironmeddie.data.domain.use_case.get_posts_use_case.GetPostsUseCase
import com.ironmeddie.data.domain.use_case.like_use_case.LikeUseCase
import com.ironmeddie.data.domain.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListVM @Inject constructor(
    private val cauth: FirebaseAuthApp,
    private val getPosts: GetPostsUseCase,
    private val likeUseCase: LikeUseCase,
    private val deletePostUseCase: DeletePostUseCase

    ) : ViewModel() {
    private var _tasks = MutableStateFlow<DataState<List<PostWithAuthor>>>(DataState.Loading)
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

    fun liked(item: PostWithAuthor) {
        viewModelScope.launch {
            likeUseCase(item)
        }
        getNews()
    }

    fun getNews() {
        if (loginState == true) {
            job?.cancel()
            job = getPosts().onEach {
                _tasks.value = DataState.Success(it)
            }.launchIn(viewModelScope)
        } else {
            _tasks.value = DataState.Error("login state is not true")
        }
    }

    fun deletePost(id:String){
        viewModelScope.launch {
            deletePostUseCase(id)
        }
        getNews()
    }


}




