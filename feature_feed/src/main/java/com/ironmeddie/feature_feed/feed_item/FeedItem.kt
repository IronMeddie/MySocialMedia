package com.ironmeddie.feature_feed.feed_item


import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.models.PostWithAuthor
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


@Composable
fun FeedItem(
    post: PostWithAuthor,
    onClikToAuthor: () -> Unit,
    onClikToBody: () -> Unit,
    onLike: () -> Unit,
    onClikComment: () -> Unit,
    onClikShare: () -> Unit,
    onClikDelete: () -> Unit,
) {
    Column(modifier = Modifier.clickable { onClikToBody() }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {  // head
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onClikToAuthor() }
            ) {
                AsyncImage(
                    model = post.author.avatarUrl,
                    contentDescription = "author s avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Black, CircleShape),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = post.author.username ?: "unknown",
                    style = MaterialTheme.typography.body1
                )
            }
            // todo проверить на авторство пост дабы функция удаления была только у автора
            var expanded by remember { mutableStateOf(false) }
            IconButton(onClick = { expanded = !expanded }, modifier = Modifier.padding(end = 11.dp)) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "post options")
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(onClick = {
                        onClikDelete()
                        expanded = !expanded
                    }) {
                        Text(text = "Delete post")
                    }
                }
            }
        }

        AsyncImage(
            model = post.post.fileUrl,
            contentDescription = "main content photo",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        ) // content
        Text(text = post.post.descr, style = MaterialTheme.typography.body1, modifier = Modifier.padding(start = 8.dp, top = 4.dp), maxLines = 3)
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp)) {
            if (post.likes.isNotEmpty()) Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = "likes " + post.likes.size.toString(),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = post.post.time.toMyStringFormat(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }




        Row(modifier = Modifier.fillMaxWidth()) {  // buttons: likes, comments

            val scope = rememberCoroutineScope()
            val image = if (!post.liked) Icons.Default.FavoriteBorder else Icons.Default.Favorite
            val targetcolor = Color(0xFFAA2D2D)
            val initialColor = Color.Black
            val color = remember { Animatable(initialColor) }
            IconButton(onClick = {onLike()
                scope.launch {
                    color.animateTo(if (!post.liked) targetcolor else initialColor, animationSpec = tween(600))

                }
                } ){
                Icon(imageVector = image, contentDescription = "post like", tint = color.value)
            }
            IconButton(onClick = onClikComment) {
                Icon(imageVector = Icons.Default.Comment, contentDescription = "post comment", tint = initialColor)
            }
            IconButton(onClick = onClikShare) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "post share", tint = initialColor)
            }
        }


    }
}


fun LocalDateTime.toMyStringFormat(): String {
    val format = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm", Locale.getDefault())
    return this.format(format) ?: ""
}


@Composable
@Preview(showBackground = true)
fun PreviewFeedItem() {
    val post =
        PostWithAuthor(post = Post(fileUrl = "https://sun9-86.userapi.com/impg/YtJno0HvVLL5oVtkTkiAAvPWlBXZ_rDCyvNlkg/DsqaxiCtHMQ.jpg?size=967x2160&quality=95&sign=bea27644ea749d10069d674c55d29881&type=album"))
//    FeedItem(
//        post,
//        onClikToAuthor = {},
//        onClikToBody = {},
//        onLike = {},
//        onClikComment = {},
//        onClikShare = {})

}


