package com.ironmeddie.data.domain.use_case.delete_post

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(private val repository: MyRepository){

    suspend operator fun invoke(id: String)= repository.deletePost(id)

}