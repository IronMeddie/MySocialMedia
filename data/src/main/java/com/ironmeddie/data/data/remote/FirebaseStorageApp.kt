package com.ironmeddie.data.data.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseStorageApp {


    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.getReference()


    suspend fun setFileToStorage(uri: Uri, fileType: FileType, postId: String) =
        flow {
            val path = storageRef.child(fileType.node).child(postId)
            path.putFile(uri).await()
            val url = path.downloadUrl.await()
            emit(url.toString())
        }


    sealed class FileType(val node: String) {
        object PostMedia : FileType("PostsMedia")
        object UserAvatar : FileType("UsersAvatar")
        object MessageMedia : FileType("MessageMedia")
    }


    companion object {
        const val ERROR_STORAGE = "can't put file to storage"
    }
}

