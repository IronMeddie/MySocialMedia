package com.ironmeddie.feature_new_post.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ironmeddie.feature_new_post.presentation.NewPostScreen
import dagger.hilt.android.qualifiers.ApplicationContext


const val newPostScreenRoute = "new_post_screen_route"

fun NavController.navigateToNewPostScreen(navOptions: NavOptions? = null){
    this.navigate(newPostScreenRoute, navOptions)
}

fun NavGraphBuilder.newPostScreen(navController: NavController){
    composable(newPostScreenRoute){
        NewPostScreen(navController = navController)
    }
}