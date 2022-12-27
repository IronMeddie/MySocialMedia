package com.ironmeddie.data.domain.models

data class PostWithAuthor(
    val post: Post = Post(),
    val author: UserInfo = UserInfo(),
    val likes: List<UserInfo> = emptyList(),
    val liked: Boolean = false
)