package com.ironmeddie.data.domain.repository

import android.net.Uri
import com.ironmeddie.data.data.remote.Friends
import com.ironmeddie.data.domain.models.MyNotification
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.models.UserInfo
import kotlinx.coroutines.flow.Flow

interface MyRepository {

    suspend fun getUser() : UserInfo

    suspend fun saveUser(user: UserInfo)

    suspend fun deleteUserFromLocal(user: UserInfo)

    suspend fun signIn(email: String, password: String)

    fun logOut()

    suspend fun registration(user: UserInfo, password: String)

    suspend fun newPost(fileUri: Uri, description: String)

    fun getUserId() :String?

    fun getUserFriendList(id: String? = null) : Flow<Friends>

    suspend fun getUserInformation(id: String) : UserInfo?

    fun getPosts(authorsList: List<String>) : Flow<List<Post>>

    fun getUsersByValue(str:String) : Flow<List<UserInfo>>

    fun getNotifications() : Flow<List<MyNotification>>

    suspend fun addFriend(id: String)

    suspend fun agreeToFriend(id: String)

    fun getPostById(id: String): Flow<Post>

    suspend fun loadAvatar(uri: Uri)

    suspend fun like(postId: String , postAuthor:String)

    suspend fun getLikes(postId: String): List<String>

    suspend fun deletePost(id: String)

    suspend fun removeLike(id: String)

}