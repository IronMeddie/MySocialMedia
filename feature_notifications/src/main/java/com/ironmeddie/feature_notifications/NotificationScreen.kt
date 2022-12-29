package com.ironmeddie.feature_notifications

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ironmeddie.data.domain.models.MyNotification
import com.ironmeddie.data.domain.use_case.feature_notification_screen.NotificationItem

sealed class NavigationDestination{
    data class Profile(val userId : String): NavigationDestination()
    data class Post(val postId : String): NavigationDestination()
}

@Composable
fun NotificationScreen(
    navigateTo: (dest : NavigationDestination) -> Unit,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val list = viewModel.notifications.collectAsState().value
    Scaffold(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(it), verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(vertical = 8.dp)) {
            items(list, key = { it.timeStamp }) { notification ->
                NotificationFriendRequest(
                    notification,
                    onNavigate = {
                        when(notification.event){
                            MyNotification.EVENT_FRIEND_REQUEST -> navigateTo(NavigationDestination.Profile(notification.authorID))
                            MyNotification.EVENT_NEW_LIKE -> navigateTo(NavigationDestination.Post(notification.postId))
                            MyNotification.EVENT_NEW_COMMENT -> navigateTo(NavigationDestination.Post(notification.postId))
                        }
                    }, onNavigateToPRofile = {
                        navigateTo(NavigationDestination.Profile(notification.authorID))
                    }) {
                    if (!notification.isFriend) viewModel.friendShipConfirmed(notification.authorID)
                }

            }
        }
    }
    if (list.isEmpty()){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            Text(text = "there is no new notification", style = MaterialTheme.typography.h6)
        }
    }
}


@Composable
fun NotificationFriendRequest(
    notification: NotificationItem,
    onNavigate: () -> Unit,
    onNavigateToPRofile : ()-> Unit,
    onFriendshipConfirmed: () -> Unit
) {
    val userInfo = notification.userInfo
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    onNavigate()
                },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = userInfo.avatarUrl, contentDescription = "avatars", contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colors.secondary, CircleShape)
                    .clickable { onNavigateToPRofile() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.padding(end = 64.dp)) {
                Text(
                    text = notification.information,
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Clip
                )
                Text(
                    text = userInfo.username.toString(),
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Text(
                    text = notification.timeStamp,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
            }
        }
        if (notification.event == MyNotification.EVENT_FRIEND_REQUEST)
        IconButton(
            onClick = onFriendshipConfirmed,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(imageVector =if (!notification.isFriend) Icons.Default.Add else Icons.Default.Done, contentDescription = "add friend icon")
        }
    }


}