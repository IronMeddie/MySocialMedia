package com.ironmeddie.data.data.remote


import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseAuthApp {

    val auth = Firebase.auth


    suspend fun checkAuth() = Firebase.auth.currentUser != null

    suspend fun signIn(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password).await()

    suspend fun registerNew(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password).await().user?.uid

    fun signOut() {
        Firebase.auth.signOut()
    }
}


