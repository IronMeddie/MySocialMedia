package com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case

import android.net.Uri
import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class UpdateAvatarUseCase @Inject constructor(private val repository: MyRepository) {
    suspend operator fun invoke(uri: Uri){
        repository.loadAvatar(uri)
    }
}