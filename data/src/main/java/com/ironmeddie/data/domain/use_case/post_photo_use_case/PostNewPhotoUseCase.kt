package com.ironmeddie.data.domain.use_case.post_photo_use_case

import android.net.Uri
import com.ironmeddie.data.data.repository.MyRepositoryImpl
import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class PostNewPhotoUseCase @Inject constructor(private val repository: MyRepository) {
    suspend operator fun invoke(fileUri: Uri, description: String) {
        repository.newPost(fileUri, description)
    }


}