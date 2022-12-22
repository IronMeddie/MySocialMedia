package com.ironmeddie.feature_new_post.domain.repository

import android.net.Uri

interface RemoteStorageRepository {

    suspend fun postPhoto(fileUri: Uri, description: String)
}