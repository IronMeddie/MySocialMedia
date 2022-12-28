package com.stogramm.composetest3.ui.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ironmeddie.feature_notifications.NotificationScreen
import com.stogramm.composetest3.ui.screens.NewsFeed.ListVM
import com.stogramm.composetest3.ui.screens.NewsFeed.NewsFeedScreen
import com.stogramm.composetest3.ui.screens.userprofile.Profile


const val mainScreenNavigationRoute = "main_screen_route"
const val newsFeedNavigationRoute = "news_feed_screen_route"
const val userProfileNavigationRoute = "profile_screen_route"
const val NotificationNavigationRoute = "notification_screen_route"


fun NavController.navigateToMainScreen(navOptions: NavOptions? = null) {
    this.navigate(mainScreenNavigationRoute, navOptions)
}

fun NavGraphBuilder.mainScreen(
    viewModel: ListVM, navController: NavController, onNavigateToDetails: (postId: String) -> Unit
) {

    navigation(route = mainScreenNavigationRoute, startDestination = newsFeedNavigationRoute) {
        composable(route = newsFeedNavigationRoute) {
            NewsFeedScreen(viewModel,navController) {
                onNavigateToDetails(it.id)
            }
        }
        composable(route = userProfileNavigationRoute) {
            Profile(navController)
        }
        composable(route = NotificationNavigationRoute) {
            NotificationScreen(navController = navController)
        }
    }


}

