package com.ironmeddie.data.domain.use_case

import com.ironmeddie.data.domain.repository.MyRepository
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke() {
        repository.getUserFriendList().onEach {
            repository.getPosts(it.Friends)
        }
    }


}