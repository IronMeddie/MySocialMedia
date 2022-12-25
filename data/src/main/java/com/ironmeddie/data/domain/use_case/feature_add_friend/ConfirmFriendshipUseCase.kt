package com.ironmeddie.data.domain.use_case.feature_add_friend

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class ConfirmFriendshipUseCase @Inject constructor(private val repository: MyRepository) {
    suspend operator fun invoke(id: String) = repository.agreeToFriend(id)
}