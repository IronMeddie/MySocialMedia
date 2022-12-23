package com.ironmeddie.domain.usecases

import com.ironmeddie.data.data.repository.MyRepositoryImpl
import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repository: MyRepository) {

    suspend fun execute() = repository.logOut()

}