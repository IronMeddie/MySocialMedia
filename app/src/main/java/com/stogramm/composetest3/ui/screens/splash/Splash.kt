package com.stogramm.composetest3.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable


const val Splash = "splash_screen"

fun NavController.navigateToSplash(navOptions: NavOptions? = null){
    this.navigate(Splash, navOptions)

}

fun NavGraphBuilder.Splash(){
    composable(route = Splash){
        Splash()
    }
}


@Composable
fun Splash() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Stogramm", color = MaterialTheme.colors.onSurface)
    }
}