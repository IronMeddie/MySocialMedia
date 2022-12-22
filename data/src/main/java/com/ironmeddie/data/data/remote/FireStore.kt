package com.ironmeddie.data.data.remote

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ironmeddie.data.data.remote.utils.PostDTO
import com.ironmeddie.data.data.remote.utils.PostNodes
import com.ironmeddie.data.data.remote.utils.UserNodes
import com.ironmeddie.data.models.UserInfo
import kotlinx.coroutines.tasks.await

const val USERS_NODE = "users"
const val POSTS_NODE = "posts"



class MyFireStore {

    val db = Firebase.firestore
    private val currentUser = Firebase.auth.currentUser?.uid


    suspend fun addNewUser(userInfo: UserInfo) = db.collection(USERS_NODE).document(currentUser.toString()).set(userInfo).await()

    suspend fun getUserbyId(userId: String = currentUser.toString()) = db.collection(USERS_NODE).document(userId).get().await().toObject(UserInfo::class.java)

    suspend fun newPost(description: String) = db.collection(POSTS_NODE).add(hashMapOf(
        PostNodes.descr to description,
        PostNodes.timeStamp to FieldValue.serverTimestamp(),
        PostNodes.author to currentUser
    )).await().id


    suspend fun getPosts(authorsId : List<String>) = db.collection(POSTS_NODE).whereIn(PostNodes.author,authorsId).get().await().toObjects(
        PostDTO::class.java).map { it.toPost() }

    suspend fun deletePost(id: String) = db.collection("posts").document(id).delete().await()

    suspend fun updatePostLink(url: String, postId: String) {
        db.collection(POSTS_NODE).document(postId).update(PostNodes.fileUrl,url).await()
    }

    suspend fun updateUserInformation(information: UserInformationUpdate){
        when(information){
            is UserInformationUpdate.AddFriend ->{
                db.collection(USERS_NODE).document(currentUser.toString() + "/friendsList").set(information.friendId)
            }
            is UserInformationUpdate.ChangeUsername->{

            }
        }
    }

}

sealed class UserInformationUpdate(information: String){
    data class AddFriend(val friendId: String) : UserInformationUpdate(friendId)
    data class ChangeUsername(val username: String) : UserInformationUpdate(username)
}

