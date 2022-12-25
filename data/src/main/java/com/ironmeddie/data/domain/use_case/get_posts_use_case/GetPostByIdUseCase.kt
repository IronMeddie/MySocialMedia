package com.ironmeddie.data.domain.use_case.get_posts_use_case

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor( private val repository: MyRepository) {
    operator fun invoke(id: String) = repository.getPostById(id)
}