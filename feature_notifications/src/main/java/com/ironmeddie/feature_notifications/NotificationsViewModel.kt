package com.ironmeddie.feature_notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.data.domain.use_case.feature_add_friend.ConfirmFriendshipUseCase
import com.ironmeddie.data.domain.use_case.feature_notification_screen.GetNotificationsUseCase
import com.ironmeddie.data.domain.use_case.feature_notification_screen.NotificationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class NotificationsViewModel @Inject constructor(private val getNotificationsUseCase: GetNotificationsUseCase,
                                                 private val confirmFriend : ConfirmFriendshipUseCase
) : ViewModel() {

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications = _notifications.asStateFlow()

    init {
        getNotifications()
    }

    fun getNotifications(){
        getNotificationsUseCase().onEach {
            _notifications.value = it
        }.launchIn(viewModelScope)
    }

    fun friendShipConfirmed(userId : String){
        viewModelScope.launch {
            confirmFriend(userId)
        }
    }

}