package com.stogramm.composetest3.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.stogramm.composetest3.context
import com.stogramm.composetest3.ui.navigation.*
import com.stogramm.composetest3.ui.navigation.bottomNavigationMenu.BottomNavigation
import com.stogramm.composetest3.ui.screens.ItemDetails.ItemViewerScreenRoute
import com.stogramm.composetest3.ui.screens.ItemDetails.LentaItemWatch
import com.stogramm.composetest3.ui.screens.NewsFeed.ListVM
import com.stogramm.composetest3.ui.screens.PhotoWath.PhotoWatch
import com.stogramm.composetest3.ui.screens.PhotoWath.photoViewerRoute
import com.stogramm.composetest3.ui.screens.login.loginScreen
import com.stogramm.composetest3.ui.screens.login.navigateToLoginScreen
import com.stogramm.composetest3.ui.screens.splash.Splash
import com.stogramm.composetest3.ui.screens.splash.navigateToSplash
import com.stogramm.composetest3.ui.theme.Composetest3Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContent {
            Composetest3Theme {
                MainNavHost()
            }
        }
    }
}


@Composable
fun MainNavHost(views: ListVM = hiltViewModel()) {


    val listOfBottomScreens =
        listOf(newsFeedNavigationRoute, userProfileNavigationRoute, NotificationNavigationRoute)
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottombar = currentDestination?.route in listOfBottomScreens


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {

        Scaffold(bottomBar = {
            if (bottombar) {
                BottomNavigation(navController)
            }
        }, modifier = Modifier.fillMaxSize()) { innerPadding ->
            LaunchedEffect(key1 = true) {

                if (views.loginState == false) {
                    navController.navigateToLoginScreen()
                }else if (views.loginState == null){
                    navController.navigateToSplash()
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
                            NavOptions.Builder().setLaunchSingleTop(true).setPopUpTo(navController.graph.id,true).build()
                        )
                    })
                composable(ItemViewerScreenRoute + "/{${Const.ITEM_ID}}") { backst ->
                    LentaItemWatch(
                        newsID = backst.arguments?.getString(Const.ITEM_ID),
                        views,
                        navController,
                        liked = { views.liked(it) })
                }
                composable(photoViewerRoute + "?id={id}", arguments =  listOf(
                    navArgument(name = "id"){
                        type = NavType.StringType
                        defaultValue = "-1" })) {
                    PhotoWatch()
                }
                mainScreen(views, navController) {
                    navController.navigate(ItemViewerScreenRoute + "/${it}")
                }
                newPostScreen(navController)
                Splash()
                searchFriendsScreen()

            }

        }

    }

}


