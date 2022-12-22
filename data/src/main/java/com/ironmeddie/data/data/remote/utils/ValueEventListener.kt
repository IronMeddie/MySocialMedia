package com.ironmeddie.data.data.remote.utils

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyValueEventListener(val onSnapshot: (snapshot: DataSnapshot)-> Unit) : ValueEventListener {
    override fun onDataChange(snapshot: DataSnapshot) {
        onSnapshot(snapshot)
    }

    override fun onCancelled(error: DatabaseError) {
        Log.d("checkCode", error.message)
    }
}

