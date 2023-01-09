package com.ironmeddie.data.domain.use_case.post_photo_use_case

import android.net.Uri
import android.util.Log
import com.ironmeddie.data.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PostNewPhotoUseCase @Inject constructor(private val repository: MyRepository) {
//    suspend operator fun invoke(fileUri: Uri, description: String) {
//        repository.newPost(fileUri, description)
//    }
     suspend operator fun invoke(fileUri: Uri, description: String) = repository.newPost(fileUri, description)

}

