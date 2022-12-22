package com.stogramm.composetest3.ui.screens.userprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.stogramm.composetest3.R
import com.stogramm.composetest3.ui.screens.login.navigateToLoginScreen
import com.stogramm.composetest3.ui.navigation.mainScreenNavigationRoute
import com.stogramm.composetest3.ui.theme.TextGrey
import com.stogramm.composetest3.ui.theme.White100

@Composable
fun UserProfile(viewModel: ProfileScreenViewModel = hiltViewModel(), onLogOut : () -> Unit) {
    val list = viewModel.list
    var itemsInRow by remember{ mutableStateOf(1) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(itemsInRow),
        modifier = Modifier.fillMaxSize(),

    ) {
        item(span = { GridItemSpan(maxLineSpan) }) { TopBarProfile(){ viewModel.logOut()
            onLogOut()} }
        item(span = { GridItemSpan(maxLineSpan) }) { HeaderProfile() }
        item(span = { GridItemSpan(maxLineSpan) }) { ProfileDescription() }
        item(span = { GridItemSpan(maxLineSpan) }) { ButtonsRow() }
        item(span = { GridItemSpan(maxLineSpan) }) { HistoriesRow() }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
                Box(modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .clickable { itemsInRow = 1 }, contentAlignment = Alignment.Center){
                    Icon(painter = painterResource(id = R.drawable.ic_posts), contentDescription = null)
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable { itemsInRow = 3 }, contentAlignment = Alignment.Center){
                    Icon(painter = painterResource(id = R.drawable.ic_grid), contentDescription = null)
                }
            }
        }
        items(list, contentType = {it}){
            AsyncImage(model = it.fileUrl, contentDescription = null, contentScale = ContentScale.Crop)
        }
    }
}

@Composable
fun TopBarProfile(logOut: () -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        val buttonscolor = ButtonDefaults.buttonColors(White100)
        val buttonElevation = ButtonDefaults.elevation(0.dp)
        val buttonBorder = ButtonDefaults.outlinedBorder
        Button(onClick = { logOut() },
            colors = buttonscolor,
            elevation = buttonElevation,
            border = buttonBorder) { Text(text = "Sign Out") }
    }
}


@Composable
fun HeaderProfile() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "https://sun9-49.userapi.com/impg/dumo-sHwZAWYqvoYtuGZOLpG2QneZboefOhpCw/ilH2Qqmra_I.jpg?size=748x744&quality=96&sign=d8c5c4a797e03fa9f9b032e22b863fa9&type=album"),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(shape = CircleShape)
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "123", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Подписчики", color = TextGrey, fontSize = 12.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "7893", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Подписан", color = TextGrey, fontSize = 12.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "123456", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "Публикации", color = TextGrey, fontSize = 12.sp)
        }
    }
}

@Composable
fun ProfileDescription() {
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
        Button(onClick = { /*TODO*/ },
            colors = buttonscolor,
            elevation = buttonElevation,
            border = buttonBorder) { Text(text = "Edit profile") }
        Button(onClick = { /*TODO*/ },
            colors = buttonscolor,
            elevation = buttonElevation,
            border = buttonBorder) { Text(text = "Promotion") }
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


@Composable
fun UserPhotoRow() {


    Row(modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        , horizontalArrangement = Arrangement.SpaceEvenly) {


        AsyncImage(model = "https://freelance.ru/img/portfolio/pics/00/3A/11/3805488.jpg", contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .fillMaxHeight()
            , contentScale = ContentScale.Crop)

        AsyncImage(model ="https://www.doersempire.com/wp-content/uploads/2019/11/Instagram-New-Ad-Feature-on-Explore-Tab.jpg" , contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(), contentScale = ContentScale.Crop
        )
        AsyncImage(model ="https://vsegda-pomnim.com/uploads/posts/2022-04/1649133264_9-vsegda-pomnim-com-p-priroda-krasivo-foto-10.jpg" , contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(), contentScale = ContentScale.Crop
        )

    }



}


fun listpgotos(): List<String> {
    val url = "https://freelance.ru/img/portfolio/pics/00/3A/11/3805488.jpg"
    val list = mutableListOf(url)
    for (i in 0..60) {
        list.add(url)
    }
    return list
}



@Preview(showBackground = true)
@Composable
fun PreviewUserProfile() {
    UserProfile(){

    }
}





