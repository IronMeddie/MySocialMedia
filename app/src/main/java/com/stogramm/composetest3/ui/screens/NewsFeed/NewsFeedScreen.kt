package com.stogramm.composetest3.ui.screens.NewsFeed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ironmeddie.data.models.Post
import com.ironmeddie.feature_new_post.presentation.navigation.navigateToNewPostScreen
import com.stogramm.composetest3.R
import com.stogramm.composetest3.ui.utilComposes.LikeButton


@Composable
fun NewsFeedScreen(
    ListVM: ListVM,
    navController: NavController,
    view: (wellnessTask: Post) -> Unit
) {
    Scaffold(
        topBar = {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Stogramm", style = MaterialTheme.typography.h6, modifier = Modifier.padding(start = 16.dp))
            Row(modifier = Modifier.padding(end = 16.dp)) {
                IconButton(onClick = { navController.navigateToNewPostScreen() }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "new post")
                }
            }
        }

        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(ListVM.tasks, key = { it.id }) { itemscope ->
                NewsCard(
                    itemscope,
                    { ListVM.liked(itemscope) },
                    {  },
                    { view(it) })
            }
        }
    }


}


@Composable
fun NewsCard(
    wellnessTask: Post,
    liked: () -> Unit,
    bodycheked: () -> Unit,
    view: (wellnessTask: Post) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable { view(wellnessTask) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                Modifier
                    .clip(shape = CircleShape)
                    .size(54.dp)
            )
            Column(
                modifier = Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = wellnessTask.author, fontSize = 18.sp, fontWeight = FontWeight(600))
                Text(
                    text = wellnessTask.timeStamp.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(300),
                    color = Color.Gray
                )
            }
        }
        Text(text = wellnessTask.descr,
            maxLines = 3 ,
            modifier = Modifier
                .padding(start = 7.dp, end = 7.dp, top = 5.dp)
                .clickable { bodycheked() })
        AsyncImage(
            model = wellnessTask.fileUrl,
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
            Row(modifier = Modifier
                .padding(start = 23.dp)
                .size(35.dp)
                .clip(CircleShape)
                .clickable { },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_comment),
                    contentDescription = "comment",
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = if (wellnessTask.commentcount > 0) wellnessTask.commentcount.toString() else "",
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
    val task = Post(id = "s")
    NewsCard(task, {}, {}, {})
}