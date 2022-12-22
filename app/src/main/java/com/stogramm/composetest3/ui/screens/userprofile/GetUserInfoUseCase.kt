package com.stogramm.composetest3.ui.screens.userprofile

import com.ironmeddie.data.data.repository.MyRepository
import com.ironmeddie.data.models.Post
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: MyRepository) {
    suspend operator fun invoke(): List<Post> {
        val list = mutableListOf(repository.getUserId().toString())
        return repository.getPosts(list)
    }
}