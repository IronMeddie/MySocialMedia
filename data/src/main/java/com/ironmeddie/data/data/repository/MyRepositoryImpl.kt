package com.ironmeddie.data.data.repository

import android.net.Uri
import com.example.test1.data.db.UserDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ironmeddie.data.data.remote.*
import com.ironmeddie.data.domain.repository.MyRepository
import com.ironmeddie.data.models.MyNotification
import com.ironmeddie.data.models.Post
import com.ironmeddie.data.models.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val articleDao: UserDao,
    private val firebaseAuthApp: FirebaseAuthApp,
    private val storage: FirebaseStorageApp,
    private val firestore: MyFireStore
) : MyRepository {

    private var userid = ""

    override suspend fun getUser() = articleDao.getUserInfo()

    override suspend fun saveUser(user: UserInfo) = articleDao.insert(user)

    override suspend fun deleteUserFromLocal(user: UserInfo) = articleDao.delete(user)

    override suspend fun signIn(email: String, password: String) {
        firebaseAuthApp.signIn(email = email, password = password)
    }

    override fun logOut() {
        firebaseAuthApp.signOut()
    }

    override suspend fun registration(user: UserInfo, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val id = firebaseAuthApp.registerNew(
                email = user.email ?: return@launch,
                password = password
            )
            if (id != null) {
                userid = id
                val newUser = user.copy(id = id)
                firestore.addNewUser(newUser)
            }
        }
    }

    override suspend fun newPost(fileUri: Uri, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val postId = firestore.newPost(description)
            storage.setFileToStorage(fileUri, FirebaseStorageApp.FileType.PostMedia, postId)
                .collectLatest {
                    firestore.updatePostLink(it, postId)
                }
        }
    }

    override fun getUserId() = Firebase.auth.currentUser?.uid

    override fun getUserFriendList(): Flow<Friends> = firestore.getFriendsList()

    override fun getNotifications(): Flow<List<MyNotification>> = firestore.getNotifications()

    override suspend fun getUserInformation(id: String) = firestore.getUserbyId(id)

    override fun getPosts(authorsList: List<String>) = firestore.getPosts(authorsList)

    override fun getUsersByValue(str: String) = firestore.getUsersListByValue(str)

    override suspend fun addFriend(id: String) {
        firestore.queryFriend(id)
    }

    override suspend fun agreeToFriend(id: String) {
        firestore.agreeToFriend(id)
    }

    override fun getPostById(id: String): Flow<Post> = firestore.getPostById(id)

    override fun loadAvatar(uri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentUser = getUserId() ?: ""
            storage.setFileToStorage(uri, FirebaseStorageApp.FileType.UserAvatar, currentUser)
                .collect() {
                    firestore.updateUserInformation(UserInformationUpdate.Avatar(it))
                }
        }


    }

    override suspend fun like(postId: String) {
        firestore.like(postId)
    }

    override fun getLikes(postId: String): Flow<List<String>> = firestore.getLikes(postId)
}