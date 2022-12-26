package com.ironmeddie.data.domain.use_case.get_posts_use_case

import com.ironmeddie.data.domain.repository.MyRepository
import com.ironmeddie.data.models.Post
import com.ironmeddie.data.models.UserInfo
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke() =
        repository.getUserFriendList().flatMapLatest {
            val list = mutableListOf<String>(repository.getUserId().toString())
            list.addAll(it.Friends)
                repository.getPosts(list).map { listPosts->
                    listPosts.sortedByDescending { it.timeStamp }.map { PostWithAuthor(
                        post = it,
                        author = repository.getUserInformation(it.author) ?: UserInfo()
                    ) }
                }
        }
}

data class PostWithAuthor(
    val post: Post,
    val author: UserInfo
)