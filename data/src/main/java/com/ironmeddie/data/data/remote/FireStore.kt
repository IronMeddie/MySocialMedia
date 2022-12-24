package com.ironmeddie.data.data.remote

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ironmeddie.data.data.remote.utils.PostDTO
import com.ironmeddie.data.data.remote.utils.PostNodes
import com.ironmeddie.data.data.remote.utils.UserNodes
import com.ironmeddie.data.models.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

const val USERS_NODE = "users"
const val POSTS_NODE = "posts"
const val FRIENDS_NODE = "friendsList"


class MyFireStore {

    val db = Firebase.firestore

    suspend fun addNewUser(userInfo: UserInfo) {
        val currentUser = userInfo.id
        CoroutineScope(Dispatchers.IO).launch {
            db.collection(USERS_NODE).document(userInfo.id).set(userInfo).await()
            db.collection(FRIENDS_NODE).document((currentUser)).set(hashMapOf(
                "Friends" to emptyList<String>(),
                "Query" to emptyList()
            )).await()
        }

    }


    suspend fun getUserbyId(userId: String = Firebase.auth.currentUser?.uid.toString()
    ) =
        db.collection(USERS_NODE).document(userId).get().await().toObject(UserInfo::class.java)

    suspend fun newPost(description: String) = db.collection(POSTS_NODE).add(
        hashMapOf(
            PostNodes.descr to description,
            PostNodes.timeStamp to FieldValue.serverTimestamp(),
            PostNodes.author to  Firebase.auth.currentUser?.uid

    )
    ).await().id


    suspend fun getPosts(authorsId: List<String>) =
        db.collection(POSTS_NODE).whereIn(PostNodes.author, authorsId).get().await().toObjects(
            PostDTO::class.java
        ).map { it.toPost() }

    suspend fun deletePost(id: String) = db.collection("posts").document(id).delete().await()

    suspend fun updatePostLink(url: String, postId: String) {
        db.collection(POSTS_NODE).document(postId).update(PostNodes.fileUrl, url).await()
    }

    suspend fun updateUserInformation(information: UserInformationUpdate) {
        val currentUser = Firebase.auth.currentUser?.uid
        when (information) {
            is UserInformationUpdate.AddFriend -> {
                db.collection(USERS_NODE).document(currentUser.toString() + "/friendsList")
                    .set(information.friendId)
            }
            //todo
            is UserInformationUpdate.ChangeUsername -> {

            }
        }
    }

    suspend fun checkAlreadyUsed(value: UnicValue): Boolean {
        // проверяем есть ли уже в базе такие значения. Не уверен, что авсе будет работать правильно нужно протестить
        return when (value) {
            is UnicValue.Username -> db.collection(USERS_NODE)
                .whereEqualTo(UserNodes.username, value.username).get().await().isEmpty
            is UnicValue.Email -> db.collection(USERS_NODE)
                .whereEqualTo(UserNodes.email, value.email).get().await().isEmpty
        }
    }

    fun getUsersListByValue(str: String): Flow<List<UserInfo>> =
        flow {
            val list = mutableListOf<UserInfo>()
            list.addAll(
                db.collection(USERS_NODE)
                    .whereIn(UserNodes.username, listOf(str))
                    .get().await().toObjects(UserInfo::class.java)
            )
//            list.addAll(db.collection(USERS_NODE).whereIn(UserNodes.firstname, listOf(str)).get().await().toObjects(UserInfo::class.java))
//            list.addAll(db.collection(USERS_NODE).whereIn(UserNodes.secondname, listOf(str)).get().await().toObjects(UserInfo::class.java))
//            list.addAll(db.collection(USERS_NODE).whereIn(UserNodes.email, listOf(str)).get().await().toObjects(UserInfo::class.java))
//            if (str.contains(" ")){
//                val firs = db.collection(USERS_NODE).whereEqualTo(UserNodes.firstname, str.substringBefore(" ")).whereEqualTo(UserNodes.secondname,str.substringAfter(" ")).get().await().toObjects(UserInfo::class.java)
//                val sec = db.collection(USERS_NODE).whereEqualTo(UserNodes.secondname, str.substringBefore(" ")).whereEqualTo(UserNodes.firstname,str.substringAfter(" ")).get().await().toObjects(UserInfo::class.java)
//                list.addAll(firs)
//                list.addAll(sec)
//            }
            Log.d("checkCode", list.size.toString())
            emit(list)
        }

    suspend fun queryFriend(id: String) {
        db.collection(FRIENDS_NODE).document((Firebase.auth.currentUser?.uid ?: "current user is null")).update("Friends", FieldValue.arrayUnion(id)).await()
        db.collection(FRIENDS_NODE).document((id)).update("Query", FieldValue.arrayUnion(Firebase.auth.currentUser?.uid)).await()
    }

    suspend fun agreeToFriend(id: String){
        val currentUser = Firebase.auth.currentUser?.uid
//        db.collection("friendsList").document((currentUser ?: return)).update("Friends", FieldValue.arrayUnion(id)).await()
        db.collection(FRIENDS_NODE).document((id)).update("Query", FieldValue.arrayRemove(currentUser)).await()
    }


}

sealed class UnicValue {
    data class Username(val username: String) : UnicValue()
    data class Email(val email: String) : UnicValue()
}

sealed class UserInformationUpdate(information: String) {
    data class AddFriend(val friendId: String) : UserInformationUpdate(friendId)
    data class ChangeUsername(val username: String) : UserInformationUpdate(username)
}

