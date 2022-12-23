package com.ironmeddie.registerscreen.registration.registration_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.repository.MyRepositoryImpl
import com.ironmeddie.domain.usecases.InvalidUserExeption
import com.ironmeddie.domain.usecases.RegisterNewUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordScreenViewModel @Inject constructor(
    private val registerNewUserUseCase: RegisterNewUserUseCase,
    private val repository: MyRepositoryImpl
) : ViewModel() {
    var password by mutableStateOf("")
        private set

    var repitedPassword by mutableStateOf("")
        private set

    var passwordCorrect by mutableStateOf(true)
        private set

    var exeption: String? = null

    fun setpassword(string: String) {
        password = string
    }

    fun setrepeatedpassword(string: String) {
        repitedPassword = string
        checkPasswords()
    }

    fun checkPasswords() {
        passwordCorrect = password == repitedPassword
    }

    fun registerNewUser() = viewModelScope.launch {
        try {
            registerNewUserUseCase.execute(password = password, user = repository.getUser())
        }catch (e: InvalidUserExeption){
            exeption = e.message
        }

    }


}