package com.ironmeddie.domain.usecases

import androidx.core.text.isDigitsOnly
import com.ironmeddie.data.data.repository.MyRepository
import com.ironmeddie.data.models.UserInfo
import javax.inject.Inject


class RegisterNewUserUseCase @Inject constructor(
    private val repository: MyRepository
) {


    @Throws(InvalidUserExeption::class)
    suspend fun execute(user: UserInfo, password: String) {
        if (password.isBlank() || password.length < 6) {
            throw InvalidUserExeption("Wrong password! min size = 6")
        }
        if (user.firstname.isNullOrBlank()) {
            throw InvalidUserExeption("first name can't be empty")
        }
        if (user.secondname.isNullOrBlank()) {
            throw InvalidUserExeption("second name can't be empty")
        }
        if (user.email.isNullOrBlank()) {
            throw InvalidUserExeption("email can't be empty")
        }
        if (user.age.isNullOrBlank() || user.age?.isDigitsOnly() == false) {
            throw InvalidUserExeption("age can't be empty it must be number")
        }
        repository.registration(user = user, password = password)
    }
}


class InvalidUserExeption(message: String) : Exception(message)


