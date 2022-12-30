package com.stogramm.composetest3.ui.screens.userprofile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.data.remote.Friends
import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.use_case.delete_post.DeletePostUseCase
import com.ironmeddie.data.domain.use_case.get_posts_use_case.GetPostsUseCase
import com.ironmeddie.data.domain.utils.DataState
import com.ironmeddie.data.domain.use_case.login_logout_use_case.LogOutUseCase
import com.ironmeddie.data.domain.use_case.get_profile_use_case.GetProfileInfoUseCase
import com.ironmeddie.data.domain.use_case.get_profile_use_case.GetUserFriendsUseCase
import com.ironmeddie.data.domain.use_case.get_profile_use_case.UpdateAvatarUseCase
import com.ironmeddie.data.domain.use_case.like_use_case.LikeUseCase
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
    private val deletePostUseCase: DeletePostUseCase,
    private val getFriends: GetUserFriendsUseCase,
    private val likeUseCase: LikeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _state = MutableStateFlow<DataState<ProfileState>>(DataState.Loading)
    val state = _state.asStateFlow()

    private var job: Job? = null

    private var profile = ProfileState()

    private var profileId : String? = null

    init {
        profileId = savedStateHandle.get<String>("id")
        loadUserInfo()
        loadFriends()
        loadPosts()
    }

    fun loadPosts() {
        job?.cancel()
        job = getPosts(GetPostsUseCase.PostOption.UserPosts(profileId)).onEach {
            profile = profile.copy(posts = it)
            _state.value = DataState.Success(profile)
        }.launchIn(viewModelScope)
    }

    fun loadUserInfo() {
        getProfileInfoUseCase(profileId).onEach {
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
        Log.d("checkCode", profileId.toString())
        getFriends(profileId).onEach {
            profile = profile.copy(friends = it)
            _state.value = DataState.Success(profile)
        }.launchIn(viewModelScope)
    }

    fun deletePosto(id: String){
        viewModelScope.launch {
            deletePostUseCase(id)
        }
    }

    fun like(item: PostWithAuthor) {
        viewModelScope.launch {
            likeUseCase(item)
        }
        loadPosts()
    }

}

data class ProfileState(
    val posts: List<PostWithAuthor> = emptyList() ,
    val user: UserInfo = UserInfo(),
    val friends: Friends = Friends()
)

