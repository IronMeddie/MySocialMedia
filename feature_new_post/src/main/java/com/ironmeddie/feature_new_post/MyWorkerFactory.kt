package com.ironmeddie.feature_new_post

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.ironmeddie.data.domain.use_case.post_photo_use_case.PostNewPhotoUseCase
import javax.inject.Inject

class MyWorkerFactory @Inject constructor (private val newPhotoUseCase: PostNewPhotoUseCase) : WorkerFactory() {

     override fun createWorker(
         appContext: Context,
         workerClassName: String,
         workerParameters: WorkerParameters
    ): ListenableWorker {
        return AppWorker(newPhotoUseCase,appContext,workerParameters)
    }
}