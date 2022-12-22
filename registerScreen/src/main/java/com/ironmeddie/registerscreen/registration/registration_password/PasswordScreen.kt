package com.ironmeddie.registerscreen.registration.registration_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ironmeddie.registerscreen.registration.registration_main_info.MyAppTextFieldColors
import com.ironmeddie.registerscreen.registration.registration_main_info.darkBlue

@Composable
fun PasswordRegistrationScreen(viewModel: PasswordScreenViewModel = hiltViewModel(), onRegistrationComplete: ()-> Unit){
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {

        Text(text = "Password")
        TextField(
            modifier = Modifier
                .padding(16.dp)
                .height(45.dp)
                .fillMaxWidth()
                .shadow(4.dp, MaterialTheme.shapes.large),
            shape = MaterialTheme.shapes.large,
            colors = MyAppTextFieldColors(),
            value = viewModel.password,
            onValueChange = { viewModel.setpassword(it) },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = darkBlue
            )
        )
        Text(text = "Repeat password")
        TextField(
            modifier = Modifier
                .padding(16.dp)
                .height(45.dp)
                .fillMaxWidth()
                .shadow(4.dp, MaterialTheme.shapes.large),
            shape = MaterialTheme.shapes.large,
            colors = MyAppTextFieldColors(),
            value = viewModel.repitedPassword,
            onValueChange = { viewModel.setrepeatedpassword(it) },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = darkBlue
            ), isError = viewModel.passwordCorrect

        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable { viewModel.registerNewUser()
                onRegistrationComplete()}) {
            Text(text = "Register", fontSize = 22.sp)
        }


    }
}



@Preview(showBackground = true)
@Composable
fun PasswordScreenPreview(){
    PasswordRegistrationScreen(){

    }
}