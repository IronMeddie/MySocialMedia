package com.ironmeddie.data.data.repository

import android.net.Uri
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.example.test1.data.db.UserDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ironmeddie.data.data.remote.*
import com.ironmeddie.data.domain.models.MyNotification
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.repository.MyRepository
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

    override suspend fun deleteUserFromLocal() = articleDao.delete()

    override suspend fun signIn(email: String, password: String) =
        firebaseAuthApp.signIn(email = email, password = password)

    override fun logOut() {
        firebaseAuthApp.signOut()
    }

    override suspend fun registration(user: UserInfo, password: String): String? {
        val id = firebaseAuthApp.registerNew(
            email = user.email ?: return null,
            password = password
        )
        if (id != null) {
            userid = id
            val newUser = user.copy(id = id)
            firestore.addNewUser(newUser)
        }
        return id
    }

    override suspend fun newPost(fileUri: Uri, description: String) {
            val postId = firestore.newPost(description)
            val url = storage.setFileToStorage(fileUri, FirebaseStorageApp.FileType.PostMedia, postId)
            firestore.updatePostLink(url, postId)
//        CoroutineScope(Dispatchers.IO).launch {
//        }
    }

    override fun getUserId() = Firebase.auth.currentUser?.uid

    override fun getUserFriendList(id: String?): Flow<Friends> {
        val idFlow = flow {
            val userId = if (id.isNullOrEmpty()) {
                getUser().id
            } else id
            emit(userId)
        }
        return idFlow.flatMapLatest {
            firestore.getFriendsList(it)
        }
    }


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

    override suspend fun loadAvatar(uri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentUser = getUserId() ?: ""
            val url =
                storage.setFileToStorage(uri, FirebaseStorageApp.FileType.UserAvatar, currentUser)
            firestore.updateUserInformation(UserInformationUpdate.Avatar(url))
        }
    }

    override suspend fun like(postId: String, postAuthor: String) {
        firestore.like(postId, postAuthor)
    }

    override suspend fun getLikes(postId: String): List<String> = firestore.getLikes(postId)

    override suspend fun deletePost(id: String) {
        storage.deletePostMedia(id)
        firestore.deletePost(id)
    }

    override suspend fun removeLike(id: String) = firestore.removeLike(id)


//    override fun newPostFlow(
//        fileUri: Uri, description: String
//    ): Flow<String> = firestore.newPostFlow(description).flatMapLatest { postId ->
//        storage.setFileToStorageFlow(
//            uri = fileUri,
//            postId = postId,
//            fileType = FirebaseStorageApp.FileType.PostMedia
//        ).onEach { url->
//            if (!url.isDigitsOnly()) firestore.updatePostLinkFlow(url,postId)
//        }
//    }

}