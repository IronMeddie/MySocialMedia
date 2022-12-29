package com.ironmeddie.data.domain.use_case.get_profile_use_case

import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.repository.MyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke(id: String? = null) = flow {
        if (id.isNullOrEmpty()) {
            val info = repository.getUserInformation(repository.getUserId() ?: return@flow) ?: UserInfo()
            emit(info)
        } else {
            val info = repository.getUserInformation(id) ?: UserInfo()
            emit(info)
        }


    }


}