package com.stogramm.composetest3.ui.screens.userprofile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ironmeddie.data.domain.utils.DataState
import com.ironmeddie.feature_feed.feed_item.FeedItem
import com.stogramm.composetest3.ui.screens.ItemDetails.navigateToItemDetails
import com.stogramm.composetest3.ui.screens.login.navigateToLoginScreen

@Composable
fun Profile(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (state is DataState.Success){
            item {

                TopBarProfile(state.data.user.username ?: "unknown") {
                    viewModel.logOut()
                    navController.navigateToLoginScreen()
                }
            }
            item {
                HeaderProfile(state.data) { uri -> viewModel.updateAvatar(uri) }
            }
            item { ProfileDescription(state.data) }

            items(state.data.posts){ post->
                FeedItem(
                    post = post,
                    onClikToAuthor = { /*TODO*/ },
                    onClikToBody = { navController.navigateToItemDetails(post.post.id) },
                    onLike = { /*TODO*/ },
                    onClikComment = { /*TODO*/ },
                    onClikDelete = {viewModel.deletePosto(post.post.id)},
                    onClikShare = {/*TODO*/}
                )
            }
        }
    }
}