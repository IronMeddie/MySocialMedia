package com.stogramm.composetest3.ui.navigation.bottomNavigationMenu

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.stogramm.composetest3.ui.navigation.NotificationNavigationRoute
import com.stogramm.composetest3.ui.navigation.newsFeedNavigationRoute
import com.stogramm.composetest3.ui.navigation.userProfileNavigationRoute

@Composable
fun bottombar(navController: NavController, click: (st: String) -> Unit) {
    val bottomitems =
        listOf(newsFeedNavigationRoute, userProfileNavigationRoute, NotificationNavigationRoute)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation() {
        bottomitems.forEach {
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { s -> s.route == it } == true,
                onClick = { click(it) },
                label = { Text(text = it) },
                icon = { })
        }
    }
}