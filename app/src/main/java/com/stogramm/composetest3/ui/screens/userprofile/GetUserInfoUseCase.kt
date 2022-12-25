package com.stogramm.composetest3.ui.screens.userprofile

import com.ironmeddie.data.data.repository.MyRepositoryImpl
import com.ironmeddie.data.models.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val repository: MyRepositoryImpl) {
     operator fun invoke(): Flow<List<Post>> {
        val list = mutableListOf(repository.getUserId().toString())
        return repository.getPosts(list)
    }
}