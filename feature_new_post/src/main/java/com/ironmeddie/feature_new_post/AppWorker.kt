package com.ironmeddie.feature_new_post

import android.content.Context
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.constance.Constance.WORKER_DESC
import com.example.constance.Constance.WORKER_ERROR_MSG
import com.example.constance.Constance.WORKER_URI
import com.ironmeddie.data.domain.use_case.post_photo_use_case.PostNewPhotoUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.random.Random


@HiltWorker
class AppWorker @AssistedInject constructor(
    private val newPhotoUseCase: PostNewPhotoUseCase,
    @Assisted private val context: Context,
    @Assisted private val params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        startForegroundService()
        val description = params.inputData.getString(WORKER_DESC) ?: ""
        val uri = Uri.parse(params.inputData.getString(WORKER_URI))
        return withContext(Dispatchers.IO){
            try {
               newPhotoUseCase(uri,description)
            }catch (e: IOException){
                return@withContext Result.failure(
                    workDataOf(
                        WORKER_ERROR_MSG to e.localizedMessage
                    )
                )
            }
            Result.success()
        }

    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, "download_channel")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentText("Downloading...")
                    .setContentTitle("Download in progress")
                    .build()
            )
        )
    }

}