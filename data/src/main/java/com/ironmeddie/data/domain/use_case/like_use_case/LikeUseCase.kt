package com.ironmeddie.data.domain.use_case.like_use_case

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class LikeUseCase @Inject constructor(private val repository: MyRepository) {
    suspend operator fun invoke(postId: String){
        repository.like(postId)
    }
}