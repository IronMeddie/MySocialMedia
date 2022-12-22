package com.example.test1.data.db

import androidx.room.*
import com.ironmeddie.data.models.UserInfo


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getUserInfo() : UserInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserInfo)

    @Delete
    suspend fun delete(user: UserInfo)
}