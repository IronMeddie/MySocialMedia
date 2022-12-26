package com.stogramm.composetest3.ui.screens.ItemDetails

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.ironmeddie.data.domain.use_case.get_posts_use_case.PostWithAuthor
import com.ironmeddie.data.models.Post
import com.stogramm.composetest3.R
import com.stogramm.composetest3.ui.screens.NewsFeed.ListVM
import com.stogramm.composetest3.ui.screens.NewsFeed.MainScreenState
import com.stogramm.composetest3.ui.screens.PhotoWath.photoViewerRoute
import com.stogramm.composetest3.ui.utilComposes.LikeButton


const val ItemViewerScreenRoute = "Item_viewer_route"

@Composable
fun LentaItemWatch(newsID : String?, vs: ListVM,navController: NavController, liked: (wellnessTask: Post) -> Unit) {

    val state = vs.tasks.collectAsState().value
    val wellnessTask =  if (state is MainScreenState.Success) state.data.firstOrNull{ it.post.id == newsID } ?: PostWithAuthor() else PostWithAuthor()

    Scaffold(modifier = Modifier.fillMaxSize()) { pad ->
        LazyColumn(
            modifier = Modifier.padding(pad)
        ) {
            item { Headermy(wellnessTask) }
            item { Body(wellnessTask, navController)}
            item {
                BottonsRow(wellnessTask) { liked(wellnessTask.post ) }
            }
        }
    }
}

@Composable
private fun Body(post: PostWithAuthor, navController: NavController) {
    val wellnessTask = post.post
    Log.d("checkCode", wellnessTask.fileUrl)
    Column(modifier = Modifier.padding(7.dp)) {
        Text(
            text = wellnessTask.descr
        )
        Image(
            painter = rememberAsyncImagePainter(wellnessTask.fileUrl),
            contentDescription = null,
            modifier = Modifier
                .defaultMinSize(minHeight = 100.dp)
                .fillMaxWidth()
                .animateContentSize()
                .clickable { navController.navigate(photoViewerRoute + "?id=${wellnessTask.id}") },
            contentScale = ContentScale.FillWidth
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_like),
                    contentDescription = null,
                    Modifier.size(25.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_front_like),
                    contentDescription = null,
                    Modifier.size(17.dp)
                )
            }
            Text(
                text = wellnessTask.likes.toString(),
                modifier = Modifier.padding(start = 5.dp)
            )
        }


    }

}

@Composable
private fun BottonsRow(wellnessTask: PostWithAuthor, liked: () -> Unit) {
    Row(modifier = Modifier.padding(7.dp)) {
        LikeButton(wellnessTask){
            liked()
        }

        Row(modifier = Modifier
            .padding(start = 23.dp)
            .clip(CircleShape)
            .clickable {
                //  reply
            }, verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(id = R.drawable.ic_rep),
                contentDescription = "rep",
                modifier = Modifier.size(30.dp)
            )

        }


    }
}

@Composable
private fun Headermy(wellnessTask: PostWithAuthor?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
    ) {
        AsyncImage(
            model = if (wellnessTask?.author?.avatarUrl.isNullOrEmpty())  R.drawable.ic_launcher_background else wellnessTask?.author?.avatarUrl,
            contentDescription = "avatar of author",
            Modifier
                .clip(shape = CircleShape)
                .size(54.dp)
        )
        Column(
            modifier = Modifier.padding(start = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = wellnessTask?.author?.username ?: "unknown user", fontSize = 18.sp, fontWeight = FontWeight(600))
            Text(
                text = wellnessTask?.post?.timeStamp.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight(300),
                color = Color.Gray
            )
        }

    }
}
