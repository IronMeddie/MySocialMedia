package com.stogramm.composetest3.ui.screens.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.ironmeddie.registerscreen.registration.registration_main_info.RegistrationScreen
import com.ironmeddie.registerscreen.registration.registration_password.PasswordRegistrationScreen
import com.ironmeddie.registerscreen.sign_in.PasswordSignInScreen
import com.ironmeddie.registerscreen.sign_in.SignInScreen
import com.ironmeddie.registerscreen.sign_in.loginViewModel


const val loginMainHost = "Screenfgsdfgst.Lentgfsdga.routgfdgsdfge"
const val SignInScreenRoute = "login_signin_screen"
const val SignInPasswordScreenRoute = "password_registration_screen"
const val RegistrationScreenRoute = "registration_screen_route"
const val RegistrationPasswordScreenRoute = "password_signin_screen"

fun NavController.navigateToLoginScreen(
    navOptions: NavOptions? = NavOptions.Builder().setPopUpTo(this.graph.id, true,true).setLaunchSingleTop(true).build()
){
    this.navigate(loginMainHost, navOptions)
}


fun NavGraphBuilder.loginScreen(navController:NavController, onRegistrationComplete: ()-> Unit) {

    navigation(
        startDestination = SignInScreenRoute,
        route = loginMainHost
    ) {

        composable(SignInScreenRoute) {
            val viewModel: loginViewModel = hiltViewModel()
            SignInScreen(viewModel, navController)
        }
        composable(SignInPasswordScreenRoute + "?email={email}", arguments =  listOf(
            navArgument(name = "email"){
                type = NavType.StringType
                defaultValue = ""
            })) {
            val viewModel: loginViewModel = hiltViewModel()
            PasswordSignInScreen(viewModel){
                onRegistrationComplete()
            }
        }
        composable(route = RegistrationScreenRoute) {
            RegistrationScreen() {
                navController.navigate(RegistrationPasswordScreenRoute)
            }
        }
        composable(route = RegistrationPasswordScreenRoute) {
            PasswordRegistrationScreen() {
                onRegistrationComplete()
            }
        }


    }
}

