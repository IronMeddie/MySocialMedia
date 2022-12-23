package com.ironmeddie.feature_add_friend.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.domain.use_case.feature_add_friend.AddFriendUseCase
import com.ironmeddie.data.domain.use_case.feature_add_friend.SearchFriendsUseCase
import com.ironmeddie.data.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFriendsViewModel @Inject constructor(
    private val searchFriendsUseCase: SearchFriendsUseCase,
    private val addFriendUseCase: AddFriendUseCase
) : ViewModel() {

    private val _resultList = MutableStateFlow<List<UserInfo>>(emptyList())
    val resultList = _resultList.asStateFlow()

    private val _request = MutableStateFlow("")
    val request = _request.asStateFlow()

    private var job : Job? = null

    fun searchForUser(){
        if (request.value.isNotEmpty()){
            job?.cancel()
            job = viewModelScope.launch {
                searchFriendsUseCase(_request.value).onEach {
                    _resultList.value = it
                }
            }
        }
    }

    fun addFriend(id: String){
        viewModelScope.launch {
            addFriendUseCase(id)
        }
    }

}