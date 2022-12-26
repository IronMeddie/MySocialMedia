package com.stogramm.composetest3.ui.screens.PhotoWath.get_profile_use_case

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class GetUserFriendsUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke() = repository.getUserFriendList()
}