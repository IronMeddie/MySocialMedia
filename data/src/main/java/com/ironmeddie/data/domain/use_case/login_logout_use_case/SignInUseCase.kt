package com.ironmeddie.data.domain.use_case.login_logout_use_case

import com.ironmeddie.data.data.remote.utils.UserNodes.email
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: MyRepository) {

    @Throws(InvalidLoginExeption::class)
    suspend fun execute(email: String, password: String, onSignInSuccess: (String) ->Unit): String? {
        if (email.isBlank()){
            throw InvalidLoginExeption("email cant ba empty")
        }
        if (password.isBlank() || password.length <6){
            throw InvalidLoginExeption("wrong password")
        }
        val id = repository.signIn(email, password)
        repository.saveUser(UserInfo(id = id ?: "0"))
        if (!id.isNullOrEmpty()) onSignInSuccess(id)
        return id
    }
}



class InvalidLoginExeption(message: String): Exception(message)