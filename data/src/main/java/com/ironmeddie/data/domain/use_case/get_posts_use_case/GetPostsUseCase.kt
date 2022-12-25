package com.ironmeddie.data.domain.use_case.get_posts_use_case

import android.util.Log
import com.ironmeddie.data.domain.repository.MyRepository
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke() =
        repository.getUserFriendList().flatMapLatest {
            val list = mutableListOf<String>(repository.getUserId().toString())
            list.addAll(it.Friends)
            Log.d("checkCode", list.toString())
                repository.getPosts(list).map { listPosts->
                    listPosts.sortedByDescending { it.timeStamp }
                }
        }
}