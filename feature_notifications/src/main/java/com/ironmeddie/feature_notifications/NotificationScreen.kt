package com.ironmeddie.feature_notifications

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun NotificationScreen(navController: NavController, viewModel: NotificationsViewModel = hiltViewModel()){
    val list = viewModel.notifications.collectAsState().value
    Scaffold(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.padding(it)){
            items(list){ notification->

            }
        }
    }
}