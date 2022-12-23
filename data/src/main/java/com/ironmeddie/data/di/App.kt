package com.stogramm.composetest3.di

import android.content.Context
import androidx.room.Room
import com.example.test1.data.db.UserDao
import com.example.test1.data.db.UserDB
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.ironmeddie.data.data.remote.FirebaseAuthApp
import com.ironmeddie.data.data.remote.FirebaseStorageApp
import com.ironmeddie.data.data.remote.MyFireStore
import com.ironmeddie.data.data.repository.MyRepositoryImpl
import com.ironmeddie.data.domain.repository.MyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun firebaseStorage(): FirebaseStorageApp = FirebaseStorageApp()

    @Provides
    fun provideRepo() : FirebaseAuthApp = FirebaseAuthApp()

    @Provides
    fun provideFireStore() : MyFireStore = MyFireStore()

    @Provides
    @Singleton
    fun providerepository(userDao: UserDao, firebaseAuth: FirebaseAuthApp,storage: FirebaseStorageApp, fireStore: MyFireStore ) : MyRepository = MyRepositoryImpl(userDao,firebaseAuth,storage,fireStore)

    @Provides
    @Singleton
    fun provideArticleDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(context, UserDB::class.java,"profile_database").build()

    @Provides
    fun provideArticleDao(appDatabase: UserDB) : UserDao = appDatabase.getUserDAO()



}