package com.stogramm.composetest3.ui.screens.ItemDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ironmeddie.data.domain.utils.DataState
import com.ironmeddie.feature_feed.feed_item.FeedItem
import com.stogramm.composetest3.ui.screens.PhotoWath.photoViewerRoute
import com.stogramm.composetest3.ui.screens.userprofile.navigateToProfile


const val ItemViewerScreenRoute = "Item_viewer_route"

@Composable
fun LentaItemWatch(
    navController: NavController,
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(modifier = Modifier.fillMaxSize()) { pad ->
        when (state) {
            is DataState.Success -> {
                val post = state.data
                LazyColumn(
                    modifier = Modifier.padding(pad)
                ) {
                    item {
                        var news by remember { mutableStateOf(post) }
                        FeedItem(
                            news,
                            onClikToAuthor = { navController.navigateToProfile(post.author.id) },
                            onClikToBody = { },
                            onLike = {
                                news = news.copy(liked = !news.liked)
                                viewModel.like(post)
                            },
                            onClikComment = {},
                            onClikShare = {},
                            onClikDelete = {
                                viewModel.deletePost(post.post.id)
                            },
                            onClickToPhoto = { navController.navigate(photoViewerRoute + "?id=${post.post.id}") },
                            isDetailsScreen = true
                        )
                    }

                }
            }
            is DataState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is DataState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(state.message)
                }
            }
        }

    }

}

