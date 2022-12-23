package com.ironmeddie.feature_new_post.presentation

import android.net.Uri
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.feature_new_post.domain.use_case.PostNewPhotoUseCase
import com.ironmeddie.common.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(private val newPhotoUseCase: PostNewPhotoUseCase) : ViewModel() {

    private val _description = MutableStateFlow(
        com.ironmeddie.common.TextFieldState(
            text = "",
            hint = "Enter post description..."
        )
    )
    val description = _description.asStateFlow()



    fun onEvent(event: PostScreenEvent){
        when (event){
            is PostScreenEvent.UpdateDescription -> _description.value = description.value.copy(text = event.str)
            is PostScreenEvent.FocusChanged -> _description.value = description.value.copy(isHintVisible = !event.focusState.isFocused && _description.value.text.isBlank())
            is PostScreenEvent.PublishPost -> {
                viewModelScope.launch {
                    newPhotoUseCase(event.uri, _description.value.text)
                }
            }
        }
    }
}

sealed class PostScreenEvent(){
    data class UpdateDescription(val str: String) :PostScreenEvent()
    data class FocusChanged(val focusState: FocusState) : PostScreenEvent()
    data class PublishPost(val uri: Uri) : PostScreenEvent()
}