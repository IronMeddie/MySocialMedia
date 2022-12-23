package com.ironmeddie.data.domain.use_case.feature_add_friend

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class AddFriendUseCase @Inject constructor(private val repository: MyRepository) {

    suspend operator fun invoke(userId: String){
        repository.addFriend(userId)
    }
}