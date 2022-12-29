package com.ironmeddie.feature_add_friend.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ironmeddie.feature_add_friend.presentation.SearchFriendsScreen


const val searchScreenRoute = "Search_screen_route"

fun NavController.navigateToSearchScreen(navOptions: NavOptions? = null){
    this.navigate(searchScreenRoute)
}

fun NavGraphBuilder.searchFriendsScreen(navigateToProfile :(id: String) -> Unit){
    composable(searchScreenRoute){
        SearchFriendsScreen(){
            navigateToProfile(it)
        }
    }
}