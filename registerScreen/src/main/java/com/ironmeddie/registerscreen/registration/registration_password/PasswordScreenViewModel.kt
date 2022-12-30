package com.ironmeddie.registerscreen.registration.registration_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.remote.utils.UserNodes.firstname
import com.ironmeddie.data.data.repository.MyRepositoryImpl
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.use_case.login_logout_use_case.InvalidUserExeption
import com.ironmeddie.data.domain.use_case.login_logout_use_case.RegisterNewUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordScreenViewModel @Inject constructor(
    private val registerNewUserUseCase: RegisterNewUserUseCase,
    private val savedStateHandle: SavedStateHandle
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

    fun registerNewUser(onRegistrationSuccess : (id: String)-> Unit) = viewModelScope.launch {
        val user = UserInfo(
            username = savedStateHandle.get<String>("username"),
            firstname = savedStateHandle.get<String>("firstname"),
            secondname = savedStateHandle.get<String>("secondname"),
            age = savedStateHandle.get<String>("age"),
            sex = savedStateHandle.get<String>("sex"),
            email = savedStateHandle.get<String>("email"),
        )
        try {
            registerNewUserUseCase(password = password, user = user){
                onRegistrationSuccess(it)
            }
        }catch (e: InvalidUserExeption){
            exeption = e.message
        }

    }


}