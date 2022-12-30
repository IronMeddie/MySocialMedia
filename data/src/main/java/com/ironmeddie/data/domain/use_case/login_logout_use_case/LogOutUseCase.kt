package com.ironmeddie.data.domain.use_case.login_logout_use_case

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: MyRepository) {

    suspend fun execute() {
        repository.deleteUserFromLocal()
        repository.logOut()
    }

}