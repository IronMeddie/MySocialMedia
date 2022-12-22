package com.ironmeddie.feature_new_post.domain.use_case

import android.net.Uri
import com.ironmeddie.data.data.repository.MyRepository
import javax.inject.Inject

class PostNewPhotoUseCase @Inject constructor(private val repository: MyRepository) {
    suspend operator fun invoke(fileUri: Uri, description: String){
        repository.newPost(fileUri,description)
        }



}