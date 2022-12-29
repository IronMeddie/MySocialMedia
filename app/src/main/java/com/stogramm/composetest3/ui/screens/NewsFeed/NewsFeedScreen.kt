package com.stogramm.composetest3.ui.screens.NewsFeed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.utils.DataState
import com.ironmeddie.feature_add_friend.navigation.navigateToSearchScreen
import com.ironmeddie.feature_feed.feed_item.FeedItem
import com.ironmeddie.feature_new_post.presentation.navigation.navigateToNewPostScreen
import com.stogramm.composetest3.ui.screens.ItemDetails.navigateToItemDetails
import com.stogramm.composetest3.ui.screens.userprofile.navigateToProfile
import kotlinx.coroutines.delay


@Composable
fun NewsFeedScreen(
    ListVM: ListVM,
    navController: NavController,
    view: (wellnessTask: Post) -> Unit
) {

    val state = ListVM.tasks.collectAsState().value
    LaunchedEffect(key1 = true) {
        delay(1000)
        ListVM.getNews()
    }
    when (state) {
        is DataState.Success -> {
            Scaffold(
                topBar = {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Stogramm",
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Row(modifier = Modifier.padding(end = 16.dp)) {
                            IconButton(onClick = { navController.navigateToNewPostScreen() }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "new post"
                                )
                            }
                            IconButton(onClick = { navController.navigateToSearchScreen() }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search"
                                )
                            }
                        }
                    }

                }) { paddingValues ->
                LazyColumn(
                    modifier = Modifier.padding(paddingValues)
                ) {

                    items(state.data, key = { it.post.id }) { post ->
                        var news by remember { mutableStateOf(post) }
                        FeedItem(news,
                            onClikToAuthor = { navController.navigateToProfile(post.author.id) },
                            onClikToBody = { navController.navigateToItemDetails(post.post.id) },
                            onLike = {
                                news = news.copy(liked = !news.liked)
                                ListVM.liked(post)
                            },
                            onClikComment = {},
                            onClikShare = {},
                            onClikDelete = { ListVM.deletePost(post.post.id) },
                            onClickToPhoto = {})
                    }
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
                Text(text = state.message)
            }
        }
    }


}




@Preview
@Composable
fun prewitemlenta() {
//    val task = Post(id = "s")
//    NewsCard(task, {}, {}, {})
}