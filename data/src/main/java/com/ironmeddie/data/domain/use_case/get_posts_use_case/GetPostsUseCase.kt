package com.ironmeddie.data.domain.use_case.get_posts_use_case

import com.ironmeddie.data.domain.repository.MyRepository
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.models.UserInfo
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke() =
        repository.getUserFriendList().flatMapLatest {
            val list = mutableListOf<String>(repository.getUserId().toString())
            list.addAll(it.Friends)
            repository.getPosts(list).map { listPosts ->
                listPosts.sortedByDescending { it.timeStamp }.map {
                    val likes = repository.getLikes(it.id)
                        .map { id -> repository.getUserInformation(id) ?: UserInfo() }
                    PostWithAuthor(
                        post = it,
                        author = repository.getUserInformation(it.author) ?: UserInfo(),
                        likes = likes,
                        liked = likes.firstOrNull { it.id == repository.getUserId() } != null
                    )
                }
            }
        }


}

data class PostWithAuthor(
    val post: Post = Post(),
    val author: UserInfo = UserInfo(),
    val likes: List<UserInfo> = emptyList(),
    val liked: Boolean = false
)