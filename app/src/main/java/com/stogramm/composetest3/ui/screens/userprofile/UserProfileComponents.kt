package com.stogramm.composetest3.ui.screens.userprofile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ironmeddie.common.util.compressUri
import com.stogramm.composetest3.ui.theme.TextGrey
import com.stogramm.composetest3.ui.theme.White100


@Composable
fun TopBarProfile(userInfo: String, logOut: () -> Unit) {
    Box(Modifier.fillMaxWidth()) {

        var expanded by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.width(16.dp))

        Text(text = userInfo, style = MaterialTheme.typography.h6, modifier = Modifier.align(Alignment.Center))

        IconButton(onClick = { expanded = !expanded }, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "drop down menu profile")
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = { logOut() }) {
                    Text(text = "Sign Out")
                }
            }
        }

    }
}


@Composable
fun HeaderProfile(state: ProfileState, onAvatarChange : (uri: Uri) -> Unit) {
    val context = LocalContext.current
    val contract = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()){
        if (it != null){
            onAvatarChange(compressUri(it, context = context) ?: Uri.EMPTY)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = state.user.avatarUrl ?: "https://sun9-49.userapi.com/impg/dumo-sHwZAWYqvoYtuGZOLpG2QneZboefOhpCw/ilH2Qqmra_I.jpg?size=748x744&quality=96&sign=d8c5c4a797e03fa9f9b032e22b863fa9&type=album"),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(shape = CircleShape)
                .clickable {
                    contract.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = state.friends.Friends.size.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Подписан", color = TextGrey, fontSize = 12.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = (state.friends.Query.size + state.friends.Friends.size).toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Подписчики", color = TextGrey, fontSize = 12.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = state.posts.size.toString(), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Публикации", color = TextGrey, fontSize = 12.sp)
        }
    }
}

@Composable
fun ProfileDescription(state: ProfileState) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Text(text = "Заголовок", fontWeight = FontWeight.Bold, maxLines = 2)

        Text(text = "- Описание" + "\n" + "- Дарим радость и улыбки" + "\n" + "- Оригинальный подарок" + "\n" + "yandex.ru")
    }
}

@Composable
fun ButtonsRow() {
    val buttonscolor = ButtonDefaults.buttonColors(White100)
    val buttonElevation = ButtonDefaults.elevation(0.dp)
    val buttonBorder = ButtonDefaults.outlinedBorder

    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { /*TODO*/ },
            colors = buttonscolor,
            elevation = buttonElevation,
            border = buttonBorder
        ) { Text(text = "Edit profile") }
        Button(
            onClick = { /*TODO*/ },
            colors = buttonscolor,
            elevation = buttonElevation,
            border = buttonBorder
        ) { Text(text = "Promotion") }
        Button(
            onClick = { /*TODO*/ },
            colors = buttonscolor,
            elevation = buttonElevation,
            border = buttonBorder
        ) { Text(text = "Contact") }
    }
}


@Composable
fun HistoriesRow() {
    LazyRow() {
        items(9) {
            HistoriesItem()
        }
    }
}

@Composable
fun HistoriesItem() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "https://sun9-49.userapi.com/impg/dumo-sHwZAWYqvoYtuGZOLpG2QneZboefOhpCw/ilH2Qqmra_I.jpg?size=748x744&quality=96&sign=d8c5c4a797e03fa9f9b032e22b863fa9&type=album"),
            contentDescription = null,
            modifier = Modifier
                .size(75.dp)
                .clip(shape = CircleShape)
        )
        Text(text = "Заголовок", maxLines = 1, modifier = Modifier.padding(top = 3.dp))

    }
}








