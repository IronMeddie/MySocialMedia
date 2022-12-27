package com.stogramm.composetest3.ui.screens.NewsFeed

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.models.PostWithAuthor
import com.ironmeddie.data.domain.models.UserInfo
import com.ironmeddie.feature_add_friend.navigation.navigateToSearchScreen
import com.ironmeddie.feature_feed.feed_item.FeedItem
import com.ironmeddie.feature_new_post.presentation.navigation.navigateToNewPostScreen
import com.stogramm.composetest3.R
import com.stogramm.composetest3.ui.utilComposes.LikeButton
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
        is MainScreenState.Success -> {
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
                    item {
                        val post = PostWithAuthor(
                            post = Post(fileUrl = "https://s1.1zoom.me/big3/147/Waterfalls_Summer_Rivers_Rays_of_light_563524_2800x1874.jpg"),
                        author = UserInfo(username = "IronMeddie", avatarUrl = "https://selam.org/galeri/?image=Diger/Wet_rocks_1920x1200.jpg")
                        )
                        FeedItem(post, onClikToAuthor = {}, onClikToBody = {},onLike = {}, onClikComment= {} )
                    }
                    items(state.data, key = { it.post.id }) { post ->
                        NewsCard(
                            post,
                            { ListVM.liked(post.post) },
                            { },
                            { view(it) })
                    }
                }
            }
        }
        is MainScreenState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is MainScreenState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message)
            }
        }
    }


}


@Composable
fun NewsCard(
    wellnessTask: PostWithAuthor,
    liked: () -> Unit,
    bodycheked: () -> Unit,
    view: (wellnessTask: Post) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { view(wellnessTask.post) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = if (!wellnessTask.author.avatarUrl.isNullOrEmpty()) wellnessTask.author.avatarUrl else R.drawable.ic_launcher_background,
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(54.dp),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = wellnessTask.author.username.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight(600)
                )

                val time = wellnessTask.post.time.dayOfMonth.toString() +" "+ wellnessTask.post.time.month.toString() + "    " + wellnessTask.post.time.hour.toString() + ":" + wellnessTask.post.time.minute
                Text(
                    text = time,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(300),
                    color = Color.Gray
                )
            }
        }
        Text(text = wellnessTask.post.descr,
            maxLines = 3,
            modifier = Modifier
                .padding(start = 7.dp, end = 7.dp, top = 5.dp)
                .clickable { bodycheked() })
        AsyncImage(
            model = wellnessTask.post.fileUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )

        Row(
            modifier = Modifier
                .padding(7.dp)
                .height(35.dp)
        ) {
            LikeButton(wellnessTask) {
                liked()
            }
            Row(
                modifier = Modifier
                    .padding(start = 23.dp)
                    .size(35.dp)
                    .clip(CircleShape)
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_comment),
                    contentDescription = "comment",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = if (wellnessTask.post.commentcount > 0) wellnessTask.post.commentcount.toString() else "",
                    modifier = Modifier.padding(start = 2.dp)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_rep),
                contentDescription = "rep",
                modifier = Modifier
                    .padding(start = 23.dp)
                    .size(30.dp)
                    .clip(CircleShape)
                    .clickable { }

            )


        }
    }

}


@Preview
@Composable
fun prewitemlenta() {
//    val task = Post(id = "s")
//    NewsCard(task, {}, {}, {})
}