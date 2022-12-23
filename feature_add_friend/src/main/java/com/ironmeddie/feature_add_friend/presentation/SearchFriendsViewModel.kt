package com.ironmeddie.feature_add_friend.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.domain.use_case.feature_add_friend.AddFriendUseCase
import com.ironmeddie.data.domain.use_case.feature_add_friend.SearchFriendsUseCase
import com.ironmeddie.data.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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

    fun searchForUser(str: String){
        if(!str.contains("\n"))_request.value = str
        job?.cancel()
        job = viewModelScope.launch {
            delay(300)
            if (_request.value.isNotEmpty()){
                searchFriendsUseCase(_request.value).collectLatest {
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