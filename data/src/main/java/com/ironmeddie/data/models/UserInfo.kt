package com.ironmeddie.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "User")
data class UserInfo(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val username: String? = "",
    val firstname: String? = "",
    val secondname: String? = "",
    val age: String? = "",
    val email: String? = "",
    val sex: String? = "",
    val avatarUrl : String?= ""

) : Serializable

val friendsList : List<String> = listOf("")
