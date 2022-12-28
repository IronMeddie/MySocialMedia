package com.stogramm.composetest3.ui.screens.userprofile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stogramm.composetest3.ui.screens.login.navigateToLoginScreen

@Composable
fun Profile(navController: NavController, viewModel: ProfileScreenViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            TopBarProfile(state) {
                viewModel.logOut()
                navController.navigateToLoginScreen()
            }
        }
        item {
            HeaderProfile(state) { uri -> viewModel.updateAvatar(uri) }
        }
        item { ProfileDescription(state) }

        items(state.posts) {

        }
    }
}