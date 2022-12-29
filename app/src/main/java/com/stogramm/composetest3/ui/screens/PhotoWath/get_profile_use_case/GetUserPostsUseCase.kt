package com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case

import com.ironmeddie.data.data.repository.MyRepositoryImpl
import com.ironmeddie.data.domain.models.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

//class GetUserPostsUseCase @Inject constructor(private val repository: MyRepositoryImpl) {
//     operator fun invoke(): Flow<List<Post>> {
//        val list = mutableListOf(repository.getUserId().toString())
//        return repository.getPosts(list).map { it.sortedByDescending{ it.time } }
//    }
//}