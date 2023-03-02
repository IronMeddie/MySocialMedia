package com.stogramm.composetest3.ui.screens.NewsFeed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.use_case.delete_post.DeletePostUseCase
import com.ironmeddie.data.domain.use_case.get_posts_use_case.GetPostsUseCase
import com.ironmeddie.data.domain.use_case.like_use_case.LikeUseCase
import com.ironmeddie.data.domain.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListVM @Inject constructor(
    private val getPosts: GetPostsUseCase,
    private val likeUseCase: LikeUseCase,
    private val deletePostUseCase: DeletePostUseCase

) : ViewModel() {
    private var _tasks = MutableStateFlow<DataState<List<PostWithAuthor>>>(DataState.Loading)
    val tasks get() = _tasks

    private var job: Job? = null

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        getNews()
    }
    fun liked(item: PostWithAuthor) {
        viewModelScope.launch {
            likeUseCase(item)
        }
        getNews()
    }

    fun getNews() {
        try {
            job?.cancel()
            _loading.value = true
            job = getPosts().onEach {
                _tasks.value = DataState.Success(it)
                _loading.value = false
            }.launchIn(viewModelScope)
        }catch (e : Exception){
            _loading.value = false
            _tasks.value = DataState.Error(e.localizedMessage ?: e.message ?: "error")
            e.localizedMessage?.let { Log.d("checkCode", it) }
        }

    }

    fun deletePost(id: String) {
        viewModelScope.launch {
            deletePostUseCase(id)
        }
        getNews()
    }


}




