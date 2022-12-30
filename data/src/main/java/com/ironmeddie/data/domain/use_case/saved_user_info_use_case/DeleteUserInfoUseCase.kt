package com.ironmeddie.data.domain.use_case.saved_user_info_use_case

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class DeleteUserInfoUseCase @Inject constructor(private val repository: MyRepository) {
    suspend operator fun invoke() = repository.deleteUserFromLocal()
}