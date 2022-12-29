package com.stogramm.composetest3.ui.screens.ItemDetails

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.utils.DataState
import com.ironmeddie.feature_feed.feed_item.FeedItem
import com.stogramm.composetest3.ui.screens.NewsFeed.ListVM
import com.stogramm.composetest3.ui.screens.PhotoWath.photoViewerRoute
import com.stogramm.composetest3.ui.screens.userprofile.navigateToProfile


const val ItemViewerScreenRoute = "Item_viewer_route"

@Composable
fun LentaItemWatch(
    newsID: String?,
    vs: ListVM,
    navController: NavController,
) {

    val state = vs.tasks.collectAsState().value
    val post = if (state is DataState.Success) state.data.firstOrNull { it.post.id == newsID }
        ?: PostWithAuthor() else PostWithAuthor()

    Scaffold(modifier = Modifier.fillMaxSize()) { pad ->
        LazyColumn(
            modifier = Modifier.padding(pad)
        ) {
            item {
                var news by remember { mutableStateOf(post) }
                FeedItem(
                    news,
                    onClikToAuthor = { navController.navigateToProfile(post.author.id) },
                    onClikToBody = { },
                    onLike = { news = news.copy(liked = !news.liked) },
                    onClikComment = {},
                    onClikShare = {},
                    onClikDelete = {
                        vs.deletePost(post.post.id)
                    },
                    onClickToPhoto = { navController.navigate(photoViewerRoute + "?id=${post.post.id}") },
                    isDetailsScreen = true
                )
            }

        }
    }
}

