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
    navOptions: NavOptions? = NavOptions.Builder().setPopUpTo(this.graph.id, true, true)
        .setLaunchSingleTop(true).build()
) {
    this.navigate(loginMainHost, navOptions)
}


fun NavGraphBuilder.loginScreen(navController: NavController, onRegistrationComplete: () -> Unit) {

    navigation(
        startDestination = SignInScreenRoute,
        route = loginMainHost
    ) {

        composable(SignInScreenRoute) {
            val viewModel: loginViewModel = hiltViewModel()
            SignInScreen(viewModel, navController)
        }
        composable(
            SignInPasswordScreenRoute + "?email={email}", arguments = listOf(
                navArgument(name = "email") {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) {
            val viewModel: loginViewModel = hiltViewModel()
            PasswordSignInScreen(viewModel) {
                onRegistrationComplete()
            }
        }
        composable(route = RegistrationScreenRoute) {
            RegistrationScreen() {
                navController.navigate(
                    RegistrationPasswordScreenRoute +
                            "?username=${it.username}" +
                            "&firstname=${it.firstname}" +
                            "&secondname=${it.secondname}" +
                            "&age=${it.age}" +
                            "&sex=${it.sex}" +
                            "&email=${it.email}"
                )
            }
        }
        composable(
            route = RegistrationPasswordScreenRoute
                    + "?username={username}&firstname={firstname}&secondname={secondname}&age={age}&sex={sex}&email={email}",
            arguments = listOf(
                navArgument(name = "username") {
                    type = NavType.StringType
                    defaultValue = "-1"
                },
                navArgument(name = "firstname") {
                    type = NavType.StringType
                    defaultValue = "-1"
                },
                navArgument(name = "secondname") {
                    type = NavType.StringType
                    defaultValue = "-1"
                },
                navArgument(name = "age") {
                    type = NavType.StringType
                    defaultValue = "-1"
                },
                navArgument(name = "sex") {
                    type = NavType.StringType
                    defaultValue = "-1"
                },
                navArgument(name = "email") {
                    type = NavType.StringType
                    defaultValue = "-1"
                },
            )
        ) {
            PasswordRegistrationScreen() {
                onRegistrationComplete()
            }
        }


    }
}

