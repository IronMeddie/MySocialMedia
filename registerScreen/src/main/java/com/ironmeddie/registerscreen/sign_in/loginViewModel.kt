package com.ironmeddie.registerscreen.sign_in

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.domain.usecases.InvalidLoginExeption
import com.ironmeddie.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class loginViewModel @Inject constructor(
    private val signInUseCAse: SignInUseCase,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {

    private val _login = MutableStateFlow("")
    val login = _login.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()


    fun updateLogin(str:String){
        _login.value = str
    }
    fun updatePassword(str:String){
        _password.value = str
    }

    init {
        _login.value = savedStateHandle.get("email") ?: ""
    }


    fun signIn(): Boolean{
        val email = login.value
        val passwords = password.value
        return try {
            viewModelScope.launch { signInUseCAse.execute(email,passwords) }
            true
        } catch (e : InvalidLoginExeption){
            false
        } catch (e: IOException){
            false
        }
    }



}