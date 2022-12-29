package com.stogramm.composetest3.ui.navigation.navHost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ironmeddie.data.data.utils.Const
import com.ironmeddie.feature_add_friend.navigation.searchFriendsScreen
import com.ironmeddie.feature_new_post.presentation.navigation.newPostScreen
import com.stogramm.composetest3.ui.navigation.*
import com.stogramm.composetest3.ui.navigation.bottomNavigationMenu.BottomNavigation
import com.stogramm.composetest3.ui.screens.CheckAuthViewModel
import com.stogramm.composetest3.ui.screens.ItemDetails.ItemViewerScreenRoute
import com.stogramm.composetest3.ui.screens.ItemDetails.LentaItemWatch
import com.stogramm.composetest3.ui.screens.PhotoWath.PhotoWatch
import com.stogramm.composetest3.ui.screens.PhotoWath.photoViewerRoute
import com.stogramm.composetest3.ui.screens.login.loginScreen
import com.stogramm.composetest3.ui.screens.login.navigateToLoginScreen
import com.stogramm.composetest3.ui.screens.splash.Splash
import com.stogramm.composetest3.ui.screens.userprofile.navigateToProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MainNavHost(checkAuthViewModel: CheckAuthViewModel = hiltViewModel()) {

    val listOfBottomScreens =
        listOf(newsFeedNavigationRoute, userProfileNavigationRoute, NotificationNavigationRoute)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottombar =
        currentDestination?.route?.contains(newsFeedNavigationRoute) == true || currentDestination?.route?.contains(
            userProfileNavigationRoute
        ) == true || currentDestination?.route?.contains(NotificationNavigationRoute) == true




    Scaffold(bottomBar = {
        if (bottombar) {
            BottomNavigation(navController)
        }
    }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        LaunchedEffect(key1 = true) {
            if (checkAuthViewModel.checkAuth() == false) {
                navController.navigateToLoginScreen()
            }
        }
        NavHost(
            navController = navController,
            startDestination = mainScreenNavigationRoute,
            Modifier.padding(innerPadding)
        ) {
            loginScreen(navController = navController,
                onRegistrationComplete = {
                    navController.navigateToMainScreen(
                        NavOptions.Builder().setLaunchSingleTop(true)
                            .setPopUpTo(navController.graph.id, true).build()
                    )
                })
            composable(ItemViewerScreenRoute + "/{${Const.ITEM_ID}}") {
                LentaItemWatch(navController)
            }
            composable(
                photoViewerRoute + "?id={id}", arguments = listOf(
                    navArgument(name = "id") {
                        type = NavType.StringType
                        defaultValue = "-1"
                    })
            ) {
                PhotoWatch()
            }
            mainScreen(navController)
            newPostScreen(navController)
            Splash()
            searchFriendsScreen(){
                navController.navigateToProfile(it)
            }

        }

    }


}