package com.ironmeddie.registerscreen.registration.registration_main_info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ironmeddie.registerscreen.R


@Composable
fun RegistrationScreen(viewModel : RegistrationScreenViewModel = hiltViewModel(), onNavigatetoPassword : () -> Unit){

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(R.string.username))
        TextField(
            modifier = Modifier
                .padding(16.dp)
                .height(45.dp)
                .fillMaxWidth()
                .shadow(4.dp, MaterialTheme.shapes.large),
            shape = MaterialTheme.shapes.large,
            colors = MyAppTextFieldColors(),
            value = viewModel.username,
            onValueChange = {
                viewModel.setUserName(it)
                },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = darkBlue
            )
        )


        Text(text = stringResource(R.string.first_name))
        TextField(
            modifier = Modifier
                .padding(16.dp)
                .height(45.dp)
                .fillMaxWidth()
                .shadow(4.dp, MaterialTheme.shapes.large),
            shape = MaterialTheme.shapes.large,
            colors = MyAppTextFieldColors(),
            value = viewModel.firstName,
            onValueChange = { viewModel.setFirsName(it) },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = darkBlue
            )
        )




        Text(text = stringResource(R.string.second_name))
        TextField(
            modifier = Modifier
                .padding(16.dp)
                .height(45.dp)
                .fillMaxWidth()
                .shadow(4.dp, MaterialTheme.shapes.large),
            shape = MaterialTheme.shapes.large,
            colors = MyAppTextFieldColors(),
            value = viewModel.secondName,
            onValueChange = { viewModel.setSecon(it) },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 12.sp,
                color = darkBlue
            )
        )


    Text(text = stringResource(R.string.email))
    TextField(
        modifier = Modifier
            .padding(16.dp)
            .height(45.dp)
            .fillMaxWidth()
            .shadow(4.dp, MaterialTheme.shapes.large),
        shape = MaterialTheme.shapes.large,
        colors = MyAppTextFieldColors(),
        value = viewModel.email,
        onValueChange = { viewModel.setEmai(it) },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 12.sp,
            color = darkBlue
        )
    )
    Text(text = stringResource(R.string.age))
    TextField(
        modifier = Modifier
            .padding(16.dp)
            .height(45.dp)
            .fillMaxWidth()
            .shadow(4.dp, MaterialTheme.shapes.large),
        shape = MaterialTheme.shapes.large,
        colors = MyAppTextFieldColors(),
        value = viewModel.age,
        onValueChange = { viewModel.setAg(it) },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 12.sp,
            color = darkBlue
        )
    )
    Text(text = stringResource(R.string.sex))

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        val male = stringResource(R.string.male)
        Box(
            modifier = Modifier
                .shadow(
                    if (viewModel.sex != male) 20.dp else 0.dp,
                    CircleShape,
                    spotColor = Color.Black,
                    ambientColor = Color.White
                )
                .clip(CircleShape)
                .size(if (viewModel.sex != male) 71.dp else 105.dp)
                .background(if (viewModel.sex == male) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary)
                .clickable { viewModel.setSe(male) } ,contentAlignment = Alignment.Center,
        ) {
            Text(text = male ,  color = Color.White)
        }
        val female = stringResource(R.string.female)
        Box(

            modifier = Modifier
                .shadow(
                    if (viewModel.sex != female) 20.dp else 0.dp,
                    CircleShape,
                    spotColor = Color.Black,
                    ambientColor = Color.White
                )
                .clip(CircleShape)
                .size(if (viewModel.sex != female) 71.dp else 105.dp)
                .background(if (viewModel.sex == female) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary)
                .clickable { viewModel.setSe(female) } ,contentAlignment = Alignment.Center,
        ) {
            Text(text = female ,  color = Color.White)
        }
        val idiot = stringResource(R.string.idiot)
        Box(
            modifier = Modifier
                .shadow(
                    if (viewModel.sex != idiot) 20.dp else 0.dp,
                    CircleShape,
                    spotColor = Color.Black,
                    ambientColor = Color.White
                )
                .clip(CircleShape)
                .size(if (viewModel.sex != idiot) 71.dp else 105.dp)
                .background(if (viewModel.sex == idiot) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary)
                .clickable { viewModel.setSe(idiot) } ,contentAlignment = Alignment.Center,
        ) {
            Text(text = idiot ,  color = Color.White)
        }
    }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colors.secondary)
            .clickable {
                viewModel.saveUserInfo()
                       onNavigatetoPassword()
            }, contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.next), fontSize = 22.sp)
        }

    }
}




val TextGrey = Color(0xFF7C7C7C)
val white = Color(0xFFFFFFFF)
val orangeerror = Color(0xFFFF8147)
val darkBlue = Color(0xFF000916)
val transparent = Color(0x00FF8147)


@Composable
fun MyAppTextFieldColors(
    textColor: Color = MaterialTheme.colors.primaryVariant,
    disabledTextColor: Color = TextGrey,
    backgroundColor: Color = white,
    cursorColor: Color = MaterialTheme.colors.primaryVariant,
    errorCursorColor: Color = orangeerror,
    placeholderColor: Color = transparent,
    disabledPlaceholderColor:Color= transparent,
    focusedBorderColor: Color = transparent,
    unfocusedBorderColor: Color = transparent
) = TextFieldDefaults.textFieldColors(
    textColor = textColor,
    disabledTextColor = disabledTextColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor,
    errorCursorColor = errorCursorColor,
    placeholderColor = placeholderColor,
    disabledPlaceholderColor = disabledPlaceholderColor,
    focusedIndicatorColor = focusedBorderColor,
    unfocusedIndicatorColor = unfocusedBorderColor
)

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview(){
    RegistrationScreen(){

    }
}