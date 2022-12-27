package com.stogramm.composetest3.ui.utilComposes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ironmeddie.data.domain.use_case.get_posts_use_case.PostWithAuthor
import com.ironmeddie.data.domain.models.Post
import com.stogramm.composetest3.R
import com.stogramm.composetest3.ui.theme.GreyButtoncont

@Composable
fun LikeButton(wellnessTask : PostWithAuthor, liked:  () -> Unit) {
    Row(modifier = Modifier.padding(start = 23.dp).fillMaxHeight().clip(CircleShape).clickable { liked() }, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter =
            if (!wellnessTask.liked)
                painterResource(id = R.drawable.ic_like_bord)
            else painterResource(id = R.drawable.ic_like)
            ,
            contentDescription = "likes",
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = if (wellnessTask.likes.size > 0) wellnessTask.likes.size.toString() else "",
            color = GreyButtoncont
        )
    }
}