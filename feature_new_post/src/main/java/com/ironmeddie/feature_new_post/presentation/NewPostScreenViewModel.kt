package com.ironmeddie.feature_new_post.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.constance.Constance.WORKER_DESC
import com.example.constance.Constance.WORKER_URI
import com.ironmeddie.data.domain.use_case.post_photo_use_case.PostNewPhotoUseCase
import com.ironmeddie.feature_new_post.AppWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPostViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val newPhotoUseCase: PostNewPhotoUseCase
) : ViewModel() {

    private val workManager = WorkManager.getInstance(context)

    private val _description = MutableStateFlow(
        com.ironmeddie.common.TextFieldState(
            text = "",
            hint = "Enter post description..."
        )
    )
    val description = _description.asStateFlow()


    fun onEvent(event: PostScreenEvent) {
        when (event) {
            is PostScreenEvent.UpdateDescription -> _description.value =
                description.value.copy(text = event.str)
            is PostScreenEvent.FocusChanged -> _description.value =
                description.value.copy(isHintVisible = !event.focusState.isFocused && _description.value.text.isBlank())
            is PostScreenEvent.PublishPost -> {
                val data = Data.Builder()
                    .putString(WORKER_URI, event.uri.toString())
                    .putString(WORKER_DESC, _description.value.text)
                    .build()
                val downloadRequest = OneTimeWorkRequestBuilder<AppWorker>()
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .setInputData(data)
                    .build()


                workManager.beginUniqueWork("Upload", ExistingWorkPolicy.APPEND, downloadRequest)
                    .enqueue()
//                viewModelScope.launch {
//                    newPhotoUseCase(event.uri, _description.value.text)
//                }
            }
        }
    }
}

sealed class PostScreenEvent() {
    data class UpdateDescription(val str: String) : PostScreenEvent()
    data class FocusChanged(val focusState: FocusState) : PostScreenEvent()
    data class PublishPost(val uri: Uri) : PostScreenEvent()
}