package com.ironmeddie.data.domain.use_case.get_posts_use_case

import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.repository.MyRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor( private val repository: MyRepository) {
    operator fun invoke(id: String) = repository.getPostById(id).map { post->
        PostWithAuthor(
            post = post,
            author = repository.getUserInformation(post.author) ?: UserInfo(),
            likes = post.likes
                .map { id -> repository.getUserInformation(id) ?: UserInfo() },
            liked = repository.getUserId() in post.likes,
        )
    }
}