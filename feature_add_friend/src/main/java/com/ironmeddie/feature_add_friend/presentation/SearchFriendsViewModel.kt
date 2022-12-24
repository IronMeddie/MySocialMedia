package com.ironmeddie.feature_add_friend.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.domain.use_case.feature_add_friend.AddFriendUseCase
import com.ironmeddie.data.domain.use_case.feature_add_friend.SearchFriendsUseCase
import com.ironmeddie.data.models.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFriendsViewModel @Inject constructor(
    private val searchFriendsUseCase: SearchFriendsUseCase,
    private val addFriendUseCase: AddFriendUseCase
) : ViewModel() {

    //    private val _resultList = mutableStateListOf<UserInfo>()
    var resultList by mutableStateOf<SearchScreenState>(SearchScreenState.NoWork)
        private set

    private val _request = MutableStateFlow("")
    val request = _request.asStateFlow()

    private var job: Job? = null

    fun searchForUser(str: String) {

        if (!str.contains("\n")) _request.value = str
        job?.cancel()
        if (_request.value.isNotEmpty()) {
            resultList = SearchScreenState.Loading
            job = searchFriendsUseCase(str).catch {
                    exception -> resultList = SearchScreenState.Error(exception.message.toString())
            }.onEach {
                resultList = SearchScreenState.Success(it)
            }.launchIn(viewModelScope)
        }else{
            resultList = SearchScreenState.NoWork
        }
    }

    fun addFriend(id: String) {
        viewModelScope.launch {
            addFriendUseCase(id)
        }
    }



//    getNotesJob?.cancel()
//    getNotesJob = useCases.getNotesUseCase(noteOrder).onEach { notes ->
//        _state.value = state.value.copy(notes = notes, noteOrder = noteOrder)
//    }.launchIn(viewModelScope)

}

sealed class SearchScreenState{
    object Loading: SearchScreenState()
    object NoWork: SearchScreenState()
    data class Success(val list : List<UserInfo>) : SearchScreenState()
    data class Error(val message : String) : SearchScreenState()
}