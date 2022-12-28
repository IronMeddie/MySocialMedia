package com.stogramm.composetest3.ui.screens.userprofile

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.remote.Friends
import com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case.GetProfileInfoUseCase
import com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case.GetUserFriendsUseCase
import com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case.GetUserPostsUseCase
import com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case.UpdateAvatarUseCase
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.use_case.get_posts_use_case.GetPostsUseCase
import com.ironmeddie.domain.usecases.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getPosts: GetPostsUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val getUserInfoUseCase: GetUserPostsUseCase,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val updateAvatarUseCase: UpdateAvatarUseCase,
    private val getFriends: GetUserFriendsUseCase,

    ) : ViewModel() {


    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        getUserInfo()
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase.execute()
        }
    }

    fun getUserInfo() {
        // вероятно стоит объединить эти потоки ? хз пока пишу так потом буду исправлять как всегда
        getUserInfoUseCase().onEach {
            _state.value = state.value.copy(posts = it.toMutableStateList())
        }.launchIn(viewModelScope)
        getProfileInfoUseCase().onEach {
            _state.value = state.value.copy(userInfo = it ?: UserInfo())
        }.launchIn(viewModelScope)
        getFriends().onEach {
            _state.value = state.value.copy(friends = it)
        }.launchIn(viewModelScope)
    }

    fun updateAvatar(uri: Uri) {
        viewModelScope.launch {
            updateAvatarUseCase(uri)
        }
    }
}

data class ProfileState(
    val posts: SnapshotStateList<Post> = mutableStateListOf<Post>(),
    val userInfo: UserInfo = UserInfo(),
    val friends: Friends = Friends()
)