package com.example.test1.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ironmeddie.data.models.UserInfo


@Database(entities = [UserInfo::class], version = 1, exportSchema = true)
abstract class UserDB: RoomDatabase() {
    abstract fun getUserDAO() : UserDao
}