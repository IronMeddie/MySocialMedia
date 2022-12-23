package com.ironmeddie.data.data.repository

import android.net.Uri
import com.example.test1.data.db.UserDao
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ironmeddie.data.data.remote.FirebaseAuthApp
import com.ironmeddie.data.data.remote.FirebaseStorageApp
import com.ironmeddie.data.data.remote.MyFireStore
import com.ironmeddie.data.domain.repository.MyRepository
import com.ironmeddie.data.models.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val articleDao: UserDao,
    private val firebaseAuthApp: FirebaseAuthApp,
    private val storage: FirebaseStorageApp,
    private val firestore: MyFireStore
) : MyRepository {

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
            val id = firebaseAuthApp.registerNew(email = user.email ?: return@launch, password = password)
            val newUser = user.copy(id = id?: "can't get uid")
            firestore.addNewUser(newUser)
        }
    }

    override suspend fun newPost(fileUri: Uri, description: String){
       CoroutineScope(Dispatchers.IO).launch {
           val postId = firestore.newPost(description)
           storage.setFileToStorage(fileUri, FirebaseStorageApp.FileType.PostMedia, postId).collectLatest {
               firestore.updatePostLink(it, postId)
           }
       }
   }

    override fun getUserId() = Firebase.auth.currentUser?.uid

    override fun getUserFriendList() : List<String> = emptyList() // todo get user friends


    override suspend fun getUserInformation() = firestore.getUserbyId()

    override suspend fun getPosts(authorsList: List<String>) = firestore.getPosts(authorsList)

    override suspend fun getUsersByValue(str:String) = firestore.getUsersListByValue(str)

    override suspend fun addFriend(id: String) {
        firestore.addFriend(id)
    }
}