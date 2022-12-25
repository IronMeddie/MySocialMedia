package com.ironmeddie.data.domain.use_case.feature_notification_screen

import com.ironmeddie.data.data.remote.MyNotification.Companion.EVENT_FRIEND_REQUEST
import com.ironmeddie.data.domain.repository.MyRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(private val repository: MyRepository) {
    operator fun invoke() = repository.getNotifications().map { list ->
        list.map { notification ->
            when (notification.event) {
                EVENT_FRIEND_REQUEST -> {
                    val userInfo = repository.getUserInformation(notification.authorID)
                    notification.copy(information = userInfo?.secondname + " " + userInfo?.firstname + " хочет дружить")
                }
                else -> { notification }
            }


        }
    }
}