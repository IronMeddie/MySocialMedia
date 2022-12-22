package com.ironmeddie.registerscreen.sign_in

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ironmeddie.registerscreen.R
import com.ironmeddie.registerscreen.registration.registration_main_info.TextGrey
import com.stogramm.composetest3.ui.screens.login.SignInPasswordScreenRoute
import com.stogramm.composetest3.ui.screens.login.RegistrationScreenRoute


@Composable
fun SignInScreen(viewModel : loginViewModel, navControllerMain : NavController) {
    var bool by remember { mutableStateOf(false) }
    val login = viewModel.login.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_comment),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(top = 50.dp)
                )
                Text(text = "Sign in Stogramm", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                OutlinedTextField(value = login.value,
                    label = { Text(text = "Телефон или почта") },
                    onValueChange = {
                        viewModel.updateLogin(it)
                        if (prowerkavvodalogin(it)) bool = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                Button(
                    onClick = { navControllerMain.navigate(SignInPasswordScreenRoute + "?email=${login.value}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                    elevation = ButtonDefaults.elevation(4.dp), enabled = bool
                ) {
                    Text(text = "Войти", color = Color.White)
                }
            }

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "После регистрации вы получите доступ ко всем возможностям STOGRAMM",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    textAlign = TextAlign.Center,
                    color = TextGrey
                )
                Button(
                    onClick = { navControllerMain.navigate(RegistrationScreenRoute) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
                ) {
                    Text(text = "Зарегистрироваться", color = Color.White)
                }
            }

        }

    }


}


@Preview(showBackground = true)
@Composable
fun pr() {
    val navControllerMain = rememberNavController()
    val viewModel: loginViewModel = hiltViewModel()
    SignInScreen(viewModel, navControllerMain)
}


fun prowerkavvodalogin(string: String): Boolean {
    if (string.isEmpty()) return false
    return true
}


