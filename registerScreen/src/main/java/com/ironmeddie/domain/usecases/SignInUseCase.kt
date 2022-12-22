package com.ironmeddie.domain.usecases

import com.ironmeddie.data.data.repository.MyRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: MyRepository) {

    @Throws(InvalidLoginExeption::class)
    suspend fun execute(email: String, password: String){
        if (email.isBlank()){
            throw InvalidLoginExeption("email cant ba empty")
        }
        if (password.isBlank() || password.length <6){
            throw InvalidLoginExeption("wrong password")
        }
        repository.signIn(email, password)
    }
}



class InvalidLoginExeption(message: String): Exception(message)