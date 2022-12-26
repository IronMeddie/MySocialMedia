package com.ironmeddie.data.domain.use_case.like_use_case

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class GetLikesUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke(postId: String) = repository.getLikes(postId)
}