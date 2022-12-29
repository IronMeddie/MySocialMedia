package com.stogramm.composetest3.ui.screens.ItemDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.utils.Const
import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.use_case.delete_post.DeletePostUseCase
import com.ironmeddie.data.domain.use_case.get_posts_use_case.GetPostByIdUseCase
import com.ironmeddie.data.domain.use_case.like_use_case.LikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val likeUseCase: LikeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PostWithAuthor())
    val state =_state.asStateFlow()

    init {
        val id = checkNotNull(savedStateHandle.get<String>(Const.ITEM_ID))
        getPost(id)
    }

    fun getPost(id: String) {
        getPostByIdUseCase(id).onEach {
            _state.value = it
        }.catch {

        }.launchIn(viewModelScope)
    }

    fun deletePost(id: String){
        viewModelScope.launch {
            deletePostUseCase(id)
        }
    }

    fun like(post :PostWithAuthor){
        viewModelScope.launch {
            likeUseCase(post)
        }
    }
}

