package com.ironmeddie.data.domain.use_case.login_logout_use_case

import androidx.core.text.isDigitsOnly
import com.ironmeddie.data.domain.repository.MyRepository
import com.ironmeddie.data.domain.models.UserInfo
import javax.inject.Inject


class RegisterNewUserUseCase @Inject constructor(
    private val repository: MyRepository
) {


    @Throws(InvalidUserExeption::class)
    suspend operator fun invoke(user: UserInfo, password: String, onRegistrationSuccess : (id : String) -> Unit): String? {
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
        val id = repository.registration(user = user, password = password)
        repository.saveUser(user.copy(id ?: "0"))
        if (!id.isNullOrEmpty()) onRegistrationSuccess(id)
        return id
    }
}


class InvalidUserExeption(message: String) : Exception(message)


