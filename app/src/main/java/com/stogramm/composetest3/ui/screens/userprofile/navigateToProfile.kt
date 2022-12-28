package com.stogramm.composetest3.ui.screens.userprofile

import androidx.navigation.NavController
import com.stogramm.composetest3.ui.navigation.userProfileNavigationRoute

fun NavController.navigateToProfile(id: String? = null){
    this.navigate(userProfileNavigationRoute + "?id=${id}")
}