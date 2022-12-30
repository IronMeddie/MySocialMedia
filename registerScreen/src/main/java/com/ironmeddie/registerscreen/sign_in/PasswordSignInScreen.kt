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
import com.ironmeddie.registerscreen.R
import com.ironmeddie.registerscreen.registration.registration_main_info.TextGrey
import com.ironmeddie.registerscreen.registration.registration_password.PasswordRegistrationScreen


@Composable
fun PasswordSignInScreen(
    viewModel: loginViewModel = hiltViewModel(),
    onNavigateToMainScreen: (String) -> Unit
) {
    val login = viewModel.login.collectAsState().value
    val password = viewModel.password.collectAsState().value
    var isLoading by remember{ mutableStateOf(false) }

    if (isLoading) CircularProgressIndicator()

    Scaffold() {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
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
                    Text(text = "Введите пароль", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    Text(
                        text = "Введите ваш текущий пароль, привязанный к $login",
                        color = TextGrey,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                    OutlinedTextField(
                        value = password,
                        label = { Text(text = "Введите пароль") },
                        onValueChange = { viewModel.updatePassword(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.signIn() {
                                isLoading = false
                                onNavigateToMainScreen(it)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
                        elevation = ButtonDefaults.elevation(4.dp)
                    ) {
                        Text(text = "Продолжить", color = Color.White)
                    }
                }

            }
        }

    }


}


@Preview(showBackground = true)
@Composable
fun prewipassword() {
    val viewModel: loginViewModel = hiltViewModel()
    PasswordSignInScreen(viewModel) {

    }
}