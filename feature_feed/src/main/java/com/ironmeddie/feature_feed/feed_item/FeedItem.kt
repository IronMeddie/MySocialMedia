package com.ironmeddie.feature_feed.feed_item


import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ironmeddie.data.domain.models.PostWithAuthor
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
    onClickToPhoto: () -> Unit,
    isDetailsScreen: Boolean = false,
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

            if (post.post.isAuthor) {
                var expanded by remember { mutableStateOf(false) }
                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.padding(end = 11.dp)
                ) {
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
        }

        val model = ImageRequest.Builder(LocalContext.current)
            .data(post.post.fileUrl)
            .size(Size.ORIGINAL)
            .crossfade(true) .build()
        val painter = rememberAsyncImagePainter(model)
        Image( modifier = Modifier.fillMaxWidth().defaultMinSize(100.dp).clickable {
                    if (isDetailsScreen)
                        onClickToPhoto()
                    else onClikToBody()
                },
            painter = painter, contentDescription = "main content photo", contentScale = ContentScale.FillWidth )


//        AsyncImage(
//            model = post.post.fileUrl,
//            contentDescription = "main content photo",
//            modifier = Modifier
//                .fillMaxWidth()
//                .clickable {
//                    if (isDetailsScreen)
//                        onClickToPhoto()
//                    else onClikToBody()
//                },
//            contentScale = ContentScale.FillWidth
//        ) // content
        Text(
            text = post.post.descr,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp),
            maxLines = if (!isDetailsScreen) 3 else Int.MAX_VALUE
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 4.dp)
        ) {
            if (post.likes.isNotEmpty()) Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = "likes " + post.post.likes.size.toString(),
                style = MaterialTheme.typography.body2
            )
            Text(
                text = post.post.time.toMyStringFormat(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }




        Row(modifier = Modifier.fillMaxWidth()) {  // buttons: likes, comments

            val image = if (!post.liked) Icons.Default.FavoriteBorder else Icons.Default.Favorite
            val targetcolor = Color(0xFFAA2D2D)
            val initialColor = Color.Black
            IconButton(onClick = {
                onLike()
            }) {
                Icon(
                    imageVector = image,
                    contentDescription = "post like",
                    tint = if (!post.liked) initialColor else targetcolor
                )
            }
            if (!isDetailsScreen) IconButton(onClick = onClikComment) {
                Icon(
                    imageVector = Icons.Default.Comment,
                    contentDescription = "post comment",
                    tint = initialColor
                )
            }
            IconButton(onClick = onClikShare) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "post share",
                    tint = initialColor
                )
            }
        }


    }
}


fun LocalDateTime.toMyStringFormat(): String {
    val format = DateTimeFormatter.ofPattern("MMMM d',' yyyy hh':'mm", Locale.getDefault())
    return this.format(format) ?: ""
}




