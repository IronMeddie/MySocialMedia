package com.ironmeddie.data.domain.models

import java.util.*

data class MyNotification(
    val recieverId: String = "",
    val event: String = "",
    val authorID: String = "",
    val timeStamp: Date = Date(),
    val isViewed: Boolean = false,
    val information: String = ""
) {
    companion object {
        const val EVENT_FRIEND_REQUEST = "friendRequest"
        const val EVENT_NEW_LIKE = "like"
        const val EVENT_NEW_COMMENT = "comment"
    }
}