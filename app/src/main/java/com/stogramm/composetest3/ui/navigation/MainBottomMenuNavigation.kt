package com.stogramm.composetest3.ui.navigation


import androidx.navigation.*
import androidx.navigation.compose.composable
import com.ironmeddie.feature_notifications.NotificationScreen
import com.stogramm.composetest3.ui.screens.NewsFeed.ListVM
import com.stogramm.composetest3.ui.screens.NewsFeed.NewsFeedScreen
import com.stogramm.composetest3.ui.screens.PhotoWath.PhotoWatch
import com.stogramm.composetest3.ui.screens.PhotoWath.photoViewerRoute
import com.stogramm.composetest3.ui.screens.userprofile.Profile


const val mainScreenNavigationRoute = "main_screen_route"
const val newsFeedNavigationRoute = "news_feed_screen_route"
const val userProfileNavigationRoute = "profile_screen_route"
const val NotificationNavigationRoute = "notification_screen_route"


fun NavController.navigateToMainScreen(navOptions: NavOptions? = null) {
    this.navigate(mainScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(navController: NavController) {

    navigation(route = mainScreenNavigationRoute, startDestination = newsFeedNavigationRoute) {
        composable(route = newsFeedNavigationRoute) {
            NewsFeedScreen(navController = navController)
        }
        composable(route = userProfileNavigationRoute + "?id={id}", arguments =  listOf(
            navArgument(name = "id"){
                type = NavType.StringType
                defaultValue = "" })) {
            Profile(navController)
        }

        composable(route = NotificationNavigationRoute) {
            NotificationScreen(navController = navController)
        }
    }


}

