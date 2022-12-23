package com.ironmeddie.data.domain.use_case.feature_add_friend

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class SearchFriendsUseCase @Inject constructor(private val repository: MyRepository) {

    suspend operator fun invoke(string: String) = repository.getUsersByValue(string)
}