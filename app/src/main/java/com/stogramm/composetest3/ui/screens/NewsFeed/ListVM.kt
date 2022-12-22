package com.stogramm.composetest3.ui.screens.NewsFeed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.remote.FirebaseAuthApp
import com.ironmeddie.data.models.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListVM @Inject constructor(private val cauth :FirebaseAuthApp) : ViewModel() {
    private val _tasks = getWellnessTasks().toMutableStateList()
    val tasks: List<Post>
        get() = _tasks

    var loginState : Boolean? by mutableStateOf(null)

    private set

    init {
        checkAuth()
    }


    fun checkAuth(){
        viewModelScope.launch {
            loginState = cauth.checkAuth()
        }
    }

    fun remove(item: Post) {
        _tasks.remove(item)
    }


    fun liked(item: Post) {
//        item.liked = !item.liked
//        if (item.liked) item.likes++ else item.likes--
    }



    fun getWellnessTasks() = List(30) { i ->
        Post(id = i.toString(), author = "$i mik", descr = "fsdafdasf fdfasd")
    }
}

