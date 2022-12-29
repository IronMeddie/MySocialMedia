package com.stogramm.composetest3.ui.screens

import androidx.lifecycle.ViewModel
import com.ironmeddie.data.data.remote.FirebaseAuthApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckAuthViewModel @Inject constructor(private val authApp: FirebaseAuthApp) : ViewModel(){

    fun checkAuth() = authApp.checkAuth()
}