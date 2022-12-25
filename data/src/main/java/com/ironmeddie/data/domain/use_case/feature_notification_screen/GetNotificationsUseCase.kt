package com.ironmeddie.data.domain.use_case.feature_notification_screen


import com.ironmeddie.data.domain.repository.MyRepository
import com.ironmeddie.data.models.UserInfo
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(private val repository: MyRepository) {
      operator fun invoke() = repository.getNotifications().combine(repository.getUserFriendList()) { list, friends->
              list.map { notification ->
                  val userInfo = repository.getUserInformation(notification.authorID) ?: UserInfo(
                      secondname = "Unknown user"
                  )
                  NotificationItem(
                      userInfo = userInfo,
                      recieverId = notification.recieverId,
                      event = notification.event,
                      authorID = notification.authorID,
                      timeStamp = notification.timeStamp.toDate().toString(),
                      isViewed = notification.isViewed,
                      information = userInfo.secondname + " " + userInfo.firstname + " хочет дружить",
                      isFriend = friends.Friends.contains(notification.authorID)
                  )
              }.sortedByDescending { it.timeStamp }
          }
      }



data class NotificationItem(
    val userInfo: UserInfo,
    val recieverId: String = "",
    val event: String = "",
    val authorID: String = "",
    val timeStamp: String = "",
    val isViewed: Boolean = false,
    val information: String = "",
    val isFriend: Boolean = false
)
