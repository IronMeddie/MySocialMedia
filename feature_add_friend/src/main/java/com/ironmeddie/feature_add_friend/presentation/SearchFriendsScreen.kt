package com.ironmeddie.feature_add_friend.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ironmeddie.common.TransparentHintTextField
import com.ironmeddie.data.models.UserInfo


@Composable
fun SearchFriendsScreen(viewModel : SearchFriendsViewModel = hiltViewModel()) {

    val state = viewModel.resultList
    val request = viewModel.request.collectAsState().value

    Scaffold(topBar = {
        SearchBar(request){
            viewModel.searchForUser(it)
        }
    }) {

        when(state){
            is SearchScreenState.NoWork -> {
                Box(
                    Modifier
                        .fillMaxSize().padding(40.dp)) {
                    Text(text = "search", modifier = Modifier.align(Alignment.TopCenter))
                }
            }
            is SearchScreenState.Loading -> {
                Box(
                    Modifier
                        .fillMaxSize().padding(40.dp)) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.TopCenter))
                }
            }
            is SearchScreenState.Success->{
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(it)){
                    items(state.list){ userinfo->
                        SearchFriendListItem(userinfo){
                            viewModel.addFriend(userinfo.id)
                        }
                    }
                }
            }
            is SearchScreenState.Error ->{
                Box(
                    Modifier
                        .fillMaxSize()) {
                    Text(text = "Cant get data", modifier = Modifier.align(Alignment.TopCenter), style = MaterialTheme.typography.h6)
                }
            }
        }

    }
}

@Composable
private fun SearchBar(value: String, onValueChanged : (String) -> Unit){



    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .background(Color.Blue)) {
        TransparentHintTextField(text = value,
            hint = "search" ,
            onValueChange = {str-> onValueChanged(str) },
            onFocusChange = {  },
            isHintVisible = value.isBlank()
        , modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(Color.White)
                .padding(vertical = 8.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.h6,
            hintAlign = Alignment.CenterStart,
            bodyAlign = Alignment.Center,
        )
    }
}

@Composable
private fun SearchFriendListItem(userInfo: UserInfo, addFriend:()->Unit){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(model = userInfo.avatarUrl, contentDescription = "avatars",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.padding(end = 64.dp)) {
                Text(text = userInfo.username.toString(), style = MaterialTheme.typography.h6, maxLines = 1, overflow = TextOverflow.Clip)
                Text(text = userInfo.secondname + " " + userInfo.firstname, style = MaterialTheme.typography.body2, maxLines = 1, overflow = TextOverflow.Clip )
            }
        }
        IconButton(onClick = addFriend, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add friend icon",)
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun prewiw(){
    SearchFriendListItem(UserInfo(username = "Ogdfsdfsdasdasdassadsadsfdsdfsd", firstname = "Oleg", secondname = "Chickov")){

    }
}

@Preview(showBackground = true)
@Composable
private fun prewiw1(){
    SearchBar(""){

    }
}