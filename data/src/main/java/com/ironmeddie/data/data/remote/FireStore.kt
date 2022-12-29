package com.ironmeddie.data.data.remote

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.ironmeddie.data.data.dto.PostDto
import com.ironmeddie.data.data.remote.utils.PostNodes
import com.ironmeddie.data.data.remote.utils.UserNodes
import com.ironmeddie.data.domain.models.MyNotification
import com.ironmeddie.data.domain.models.MyNotification.Companion.EVENT_FRIEND_REQUEST
import com.ironmeddie.data.domain.models.MyNotification.Companion.EVENT_NEW_LIKE
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.models.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.time.*

const val USERS_NODE = "users"
const val POSTS_NODE = "posts"
const val FRIENDS_NODE = "friendsList"
const val NOTIFI_NODE = "notifications"
const val LIKES_CHILD = "likes"


class MyFireStore {

    val db = Firebase.firestore

    suspend fun addNewUser(userInfo: UserInfo) {
        val currentUser = userInfo.id
        db.collection(USERS_NODE).document(userInfo.id).set(userInfo).await()
        db.collection(FRIENDS_NODE).document(currentUser).set(
            hashMapOf(
                "Friends" to emptyList<String>(),
                "Query" to emptyList()
            )
        ).await()
    }

    @Throws(NoAuthExeption::class)
    suspend fun getUserbyId(
        userId: String = Firebase.auth.currentUser?.uid
            ?: throw NoAuthExeption("getUserbyId failure")
    ) =
        db.collection(USERS_NODE).document(userId).get().await().toObject(UserInfo::class.java)


    @Throws(NoAuthExeption::class)
    suspend fun newPost(description: String): String {
        val currentUser = Firebase.auth.currentUser?.uid ?: throw NoAuthExeption("newPost failure")
        return db.collection(POSTS_NODE).add(
            hashMapOf(
                PostNodes.descr to description,
                PostNodes.timeStamp to FieldValue.serverTimestamp(),
                PostNodes.author to currentUser
            )
        ).await().id
    }


    fun getPosts(authorsId: List<String>) = flow {
        val currentUser = Firebase.auth.currentUser?.uid ?: throw NoAuthExeption("newPost failure")
        val querry = db.collection(POSTS_NODE).whereIn(PostNodes.author, authorsId).get().await()
        val list = querry.map {
            val post = it.toObject(PostDto::class.java).copy(id = it.id).toPost()
            post.copy(isAuthor = post.author == currentUser)
        }
        emit(list)
    }


    suspend fun updatePostLink(url: String, postId: String) {
        db.collection(POSTS_NODE).document(postId).update(PostNodes.fileUrl, url).await()
    }

    @Throws(NoAuthExeption::class)
    suspend fun updateUserInformation(information: UserInformationUpdate) {
        val currentUser =
            Firebase.auth.currentUser?.uid ?: throw NoAuthExeption("updateUserInformation failure")
        when (information) {
            is UserInformationUpdate.ChangeUsername -> {
                db.collection(USERS_NODE).document(currentUser)
                    .update(UserNodes.username, information.username).await()
            }
            is UserInformationUpdate.Avatar -> {
                db.collection(USERS_NODE).document(currentUser)
                    .update(UserNodes.avatarUrl, information.fileUrl).await()
            }
            is UserInformationUpdate.About -> {
                db.collection(USERS_NODE).document(currentUser)
                    .update(UserNodes.about, information.desc).await()
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
            emit(list)
        }

    @Throws(NoAuthExeption::class)
    suspend fun queryFriend(id: String) {
        val currentUser =
            Firebase.auth.currentUser?.uid ?: throw NoAuthExeption("queryFriend failure")
        db.collection(FRIENDS_NODE).document(currentUser)
            .update("Friends", FieldValue.arrayUnion(id)).await()
        db.collection(FRIENDS_NODE).document(id).update("Query", FieldValue.arrayUnion(currentUser))
            .await()
//        db.collection(NOTIFI_NODE).add(MyNotification(
//            event = EVENT_FRIEND_REQUEST,
//            authorID = currentUser,
//            recieverId = id
//        ))
        db.collection(NOTIFI_NODE).add(
            hashMapOf(
                "recieverId" to id,
                "event" to EVENT_FRIEND_REQUEST,
                "authorID" to currentUser,
                "timeStamp" to FieldValue.serverTimestamp(),
                "isViewed" to false
            )
        )
    }

    @Throws(NoAuthExeption::class)
    suspend fun agreeToFriend(id: String) {
        val currentUser =
            Firebase.auth.currentUser?.uid ?: throw NoAuthExeption("agreeToFriend failure")
        db.collection(FRIENDS_NODE).document(currentUser)
            .update("Query", FieldValue.arrayRemove(id)).await()
        db.collection(FRIENDS_NODE).document(currentUser)
            .update("Friends", FieldValue.arrayUnion(id)).await()
    }

    @Throws(NoAuthExeption::class)
    fun getNotifications(): Flow<List<MyNotification>> { // or get friendsList
        val currentUser =
            Firebase.auth.currentUser?.uid ?: throw NoAuthExeption("getNotifications failure")
        return flow {
            val list =
                db.collection(NOTIFI_NODE).whereEqualTo("recieverId", currentUser).get().await()
                    .toObjects(MyNotification::class.java).toMutableList()
            list.removeIf { it.authorID == currentUser }
            emit(list)
        }
    }


    @Throws(NoAuthExeption::class)
    fun getFriendsList(id: String? = null): Flow<Friends> {
        val currentUser = if (id?.isNotBlank() == true) id else Firebase.auth.currentUser?.uid
            ?: throw NoAuthExeption("getNotifications failure")
        return flow {
            val list = db.collection(FRIENDS_NODE).document(currentUser).get().await()
                .toObject(Friends::class.java)
            emit(list ?: Friends())
        }
    }


    fun getPostById(id: String) =
        flow {
            val document = db.collection(POSTS_NODE).document(id).get().await()
            val post = document.toObject(PostDto::class.java)?.toPost()?.copy(id = document.id) ?: Post()
            emit(post)
        }

    @Throws(NoAuthExeption::class)
    suspend fun like(postId: String, postAuthor: String) {
        val currentUser = Firebase.auth.currentUser?.uid ?: throw NoAuthExeption("like failure")
        db.collection(POSTS_NODE).document(postId)
            .update(LIKES_CHILD, FieldValue.arrayUnion(currentUser)).await()
        db.collection(NOTIFI_NODE).add(
            hashMapOf(
                "recieverId" to postAuthor,
                "event" to EVENT_NEW_LIKE,
                "authorID" to currentUser,
                "timeStamp" to FieldValue.serverTimestamp(),
                "postId" to postId
            )
        )
    }

    suspend fun getLikes(postId: String) = emptyList<String>() /*TODO*/


    suspend fun deletePost(id: String) {
        db.collection(POSTS_NODE).document(id).delete().await()
    }

    suspend fun removeLike(id: String) {
        val currentUser = Firebase.auth.currentUser?.uid ?: throw NoAuthExeption("like failure")
        db.collection(POSTS_NODE).document(id)
            .update(LIKES_CHILD, FieldValue.arrayRemove(currentUser)).await()
    }


}

sealed class UnicValue {
    data class Username(val username: String) : UnicValue()
    data class Email(val email: String) : UnicValue()
}

sealed class UserInformationUpdate(information: String) {
    data class ChangeUsername(val username: String) : UserInformationUpdate(username)
    data class Avatar(val fileUrl: String) : UserInformationUpdate(fileUrl)
    data class About(val desc: String) : UserInformationUpdate(desc)
}

data class Likes(val id: List<String> = emptyList())

class NoAuthExeption(message: String) : Exception(message)


data class Friends(
    val Friends: List<String> = emptyList(),
    val Query: List<String> = emptyList()
)



