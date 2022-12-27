package com.ironmeddie.registerscreen.registration.registration_main_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.repository.MyRepositoryImpl
import com.ironmeddie.data.domain.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegistrationScreenViewModel @Inject constructor(
    private val repository: MyRepositoryImpl
    ) : ViewModel() {
    var username by mutableStateOf("")
        private set
    var firstName by mutableStateOf("")
        private set
    var secondName by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var age by mutableStateOf("")
        private set
    var sex by mutableStateOf("")
        private set


    fun setUserName(str: String) {
        username = str
        //todo chek for null and chek database to have the same value and chek for alien chars
    }
    fun setFirsName(str: String) {
        firstName = str
    }
    fun setSecon(str: String) {
        secondName = str
    }
    fun setEmai(str: String) {
        email = str
    }
    fun setAg(str: String) {
        age = str

    }
    fun setSe(str: String) {
        sex = str
    }


    fun saveUserInfo(){
        viewModelScope.launch {
            repository.saveUser(createUser())
        }
    }



    fun createUser(): UserInfo {

        return UserInfo(
            id = "0",
            username = username,
            firstname = firstName,
            secondname = secondName,
            age = age,
            sex = sex,
            email = email
        )


    }


}