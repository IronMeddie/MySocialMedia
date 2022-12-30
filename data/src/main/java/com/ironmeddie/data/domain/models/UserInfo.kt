package com.ironmeddie.data.domain.models

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
    val avatarUrl : String?= "",
    val about : String = ""
) : Serializable
