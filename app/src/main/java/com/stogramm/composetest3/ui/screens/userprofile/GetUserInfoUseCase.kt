package com.stogramm.composetest3.ui.screens.userprofile

import com.ironmeddie.data.data.repository.MyRepositoryImpl
import com.ironmeddie.data.models.Post
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: MyRepositoryImpl) {
    suspend operator fun invoke(): List<Post> {
        val list = mutableListOf(repository.getUserId().toString())
        return repository.getPosts(list)
    }
}