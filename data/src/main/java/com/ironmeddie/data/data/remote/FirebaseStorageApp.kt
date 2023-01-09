package com.ironmeddie.data.data.remote

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FirebaseStorageApp {


    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.getReference()


    suspend fun setFileToStorage(uri: Uri, fileType: FileType, postId: String): String {
        val path = storageRef.child(fileType.node).child(postId)
        path.putFile(uri).await()
        val url = path.downloadUrl.await()
        return url.toString()
    }

//    fun setFileToStorageFlow(uri: Uri, fileType: FileType, postId: String) = flow {
//        val path = storageRef.child(fileType.node).child(postId)
//        val void = path.putFile(uri).await()
//        Log.d("UploadFlow", void.task.snapshot.bytesTransferred.toString())
//        val url = path.downloadUrl.await().toString()
//        emit(url)
//    }

    suspend fun deletePostMedia(id: String) {
        storageRef.child(FileType.PostMedia.node).child(id).delete().await()
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


