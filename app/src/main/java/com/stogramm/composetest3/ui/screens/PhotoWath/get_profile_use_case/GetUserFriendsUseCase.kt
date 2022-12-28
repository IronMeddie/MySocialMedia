package com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case

import android.util.Log
import com.ironmeddie.data.domain.repository.MyRepository
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class GetUserFriendsUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke(id: String? = null) = repository.getUserFriendList(id).catch { Log.d("checkCode", it.message.toString()) }
}