package com.ironmeddie.data.domain.use_case.get_posts_use_case

import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.repository.MyRepository
import com.ironmeddie.data.domain.utils.DataState
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject



class GetPostsUseCase @Inject constructor(private val repository: MyRepository) {


    operator fun invoke(option: PostOption = PostOption.Feed) = if (option is PostOption.Feed) {
        repository.getUserFriendList().flatMapLatest {
            val list = mutableListOf(repository.getUserId() ?: repository.getUser().id)
            list.addAll(it.Friends)
            repository.getPosts(list).map { posts ->
                posts.sortedByDescending { it.time }.map { post ->
                    PostWithAuthor(
                        post = post,
                        author = repository.getUserInformation(post.author) ?: UserInfo(),
                        likes = post.likes
                            .map { id -> repository.getUserInformation(id) ?: UserInfo() },
                        liked = repository.getUserId() in post.likes
                    )
                }
            }
        }
    } else {
        val list = if (option is PostOption.UserPosts && option.id?.isNotBlank() == true) listOf<String>(option.id) else listOf(repository.getUserId().toString())
        repository.getPosts(list).map { posts ->
            posts.sortedByDescending { it.time }.map { post ->
                PostWithAuthor(
                    post = post,
                    author = repository.getUserInformation(post.author) ?: UserInfo(),
                    likes = post.likes
                        .map { id -> repository.getUserInformation(id) ?: UserInfo() },
                    liked = repository.getUserId() in post.likes
                )
            }
        }
    }

    sealed class PostOption {
        data class UserPosts(val id: String? = null) : PostOption()
        object Feed : PostOption()
    }


}







