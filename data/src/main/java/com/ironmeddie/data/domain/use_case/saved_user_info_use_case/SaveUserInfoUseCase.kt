package com.ironmeddie.data.domain.use_case.saved_user_info_use_case

import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class SaveUserInfoUseCase @Inject constructor(private val repository: MyRepository){
    suspend operator fun invoke(id: String){
        val user: UserInfo = repository.getUserInformation(id) ?: UserInfo(id = id)
        repository.saveUser(user)
    }
}