package com.stogramm.composetest3.ui.screens.PhotoWath

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.use_case.get_posts_use_case.GetPostByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoWatchViewModel @Inject constructor(savedStateHandle: SavedStateHandle,
private val getPost: GetPostByIdUseCase
): ViewModel(){

    var url by mutableStateOf("")
    private set

    var state : MutableState<PhotoState> = mutableStateOf(PhotoState.Loading)
    private set

    init {
        val data = savedStateHandle.get<String>("id")
        getPostUrlById(data ?: "")
    }

    fun getPostUrlById(id: String){
        viewModelScope.launch {
            try {
                getPost(id).collectLatest {
                    state.value = PhotoState.Success(it)
                }
            }catch (e: Exception){
                state.value = PhotoState.Error(e.localizedMessage ?: "")
            }

        }
    }


}

sealed class PhotoState{
    object Loading: PhotoState()
    data class Success(val post : Post): PhotoState()
    data class Error(val message : String): PhotoState()
}