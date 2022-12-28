package com.stogramm.composetest3.ui.screens.userprofile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.remote.Friends
import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.use_case.get_posts_use_case.GetPostsUseCase
import com.ironmeddie.data.domain.utils.DataState
import com.ironmeddie.domain.usecases.LogOutUseCase
import com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case.GetProfileInfoUseCase
import com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case.GetUserFriendsUseCase
import com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case.UpdateAvatarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getPosts: GetPostsUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val updateAvatarUseCase: UpdateAvatarUseCase,
    private val getFriends: GetUserFriendsUseCase
) : ViewModel() {

//
//    private val _posts = MutableStateFlow<DataState<List<PostWithAuthor>>>(DataState.Loading)
//    val posts = _posts.asStateFlow()
//
//    private val _user = MutableStateFlow<DataState<UserInfo>>(DataState.Loading)
//    val user = _user.asStateFlow()
//
//    private val _friends = MutableStateFlow<DataState<Friends>>(DataState.Loading)
//    val friends = _friends.asStateFlow()

    private val _state = MutableStateFlow<DataState<ProfileState>>(DataState.Loading)
    val state = _state.asStateFlow()

    private var job: Job? = null

    private var profile = ProfileState()

    init {
        loadUserInfo()
        loadFriends()
        loadPosts()
    }

    fun loadPosts() {
        job?.cancel()
        job = getPosts(GetPostsUseCase.PostOption.UserPosts).onEach {
//            _posts.value = DataState.Success(it)
            profile = profile.copy(posts = it)
            _state.value = DataState.Success(profile)
        }.launchIn(viewModelScope)
    }

    fun loadUserInfo() {
        getProfileInfoUseCase().onEach {
//            _user.value = DataState.Success(it)
            profile = profile.copy(user = it)
            _state.value = DataState.Success(profile)
        }.launchIn(viewModelScope)
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase.execute()
        }
    }

    fun updateAvatar(uri: Uri) {
        viewModelScope.launch {
            updateAvatarUseCase(uri)
        }
    }

    fun loadFriends(){
        getFriends().onEach {
//            _friends.value = DataState.Success(it)
            profile = profile.copy(friends = it)
            _state.value = DataState.Success(profile)
        }.launchIn(viewModelScope)
    }


}

data class ProfileState(
    val posts: List<PostWithAuthor> = emptyList() ,
    val user: UserInfo = UserInfo(),
    val friends: Friends = Friends()
)

