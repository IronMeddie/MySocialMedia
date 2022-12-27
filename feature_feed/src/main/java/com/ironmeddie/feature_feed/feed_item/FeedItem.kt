package com.ironmeddie.feature_feed.feed_item

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ironmeddie.data.domain.models.Post
import com.ironmeddie.data.domain.models.PostWithAuthor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun FeedItem(
    post: PostWithAuthor,
    onClikToAuthor: () -> Unit,
    onClikToBody: () -> Unit,
    onLike: () -> Unit,
    onClikComment: () -> Unit
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {  // head
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(11.dp)
            ) {
                AsyncImage(
                    model = post.author.avatarUrl,
                    contentDescription = "author s avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = post.author.username ?: "unknown",
                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                )
            }
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end = 11.dp)) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "post options")
            }
        }
        AsyncImage(
            model = post.post.fileUrl,
            contentDescription = "main content photo",
            modifier = Modifier.fillMaxWidth()
        ) // content

        Row(modifier = Modifier.fillMaxWidth()) {  // buttons: likes, comments
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "post like")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Comment, contentDescription = "post comment")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "post share")
            }
        }
        Box() {
            Text(
                text = post.post.time.toMyStringFormat(),
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

    }
}


fun LocalDateTime.toMyStringFormat(): String {
    val format =  DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm ")
    return this.format(format) ?: ""
}


@Composable
@Preview(showBackground = true)
fun PreviewFeedItem() {
    val post =
        PostWithAuthor(post = Post(fileUrl = "https://sun9-86.userapi.com/impg/YtJno0HvVLL5oVtkTkiAAvPWlBXZ_rDCyvNlkg/DsqaxiCtHMQ.jpg?size=967x2160&quality=95&sign=bea27644ea749d10069d674c55d29881&type=album"))
    FeedItem(post, onClikToAuthor = {}, onClikToBody = {}, onLike = {}, onClikComment = {})
}