package com.ironmeddie.data.domain.use_case.get_posts_use_case

import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.repository.MyRepository
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: MyRepository) {

    sealed class PostOption {
        object UserPosts : PostOption()
        object Feed : PostOption()
    }


    operator fun invoke(option: PostOption = PostOption.Feed) =if(option is PostOption.Feed){
        repository.getUserFriendList().flatMapLatest {
                val list = mutableListOf(repository.getUserId().toString())
                list.addAll(it.Friends)
                repository.getPosts(list).map { posts ->
                    posts.sortedByDescending { it.time }.map { post -> PostWithAuthor(
                    post = post,
                    author = repository.getUserInformation(post.author) ?: UserInfo(),
                    likes = repository.getLikes(post.id)
                        .map { id -> repository.getUserInformation(id) ?: UserInfo() },
                    liked = repository.getUserId() in repository.getLikes(post.id)
                ) }
                }
            }
    }else{
        repository.getPosts(listOf(repository.getUserId().toString())).map { posts ->
            posts.sortedByDescending { it.time }.map { post -> PostWithAuthor(
                post = post,
                author = repository.getUserInformation(post.author) ?: UserInfo(),
                likes = repository.getLikes(post.id)
                    .map { id -> repository.getUserInformation(id) ?: UserInfo() },
                liked = repository.getUserId() in repository.getLikes(post.id)
            ) }
        }
    }



}







