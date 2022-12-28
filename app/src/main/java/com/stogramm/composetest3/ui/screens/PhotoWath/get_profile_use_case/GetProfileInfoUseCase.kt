package com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case

import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.data.domain.repository.MyRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke() = flow {
        val id = repository.getUserId() ?: return@flow
        val info = repository.getUserInformation(id) ?: UserInfo()
        emit(info)
    }


}