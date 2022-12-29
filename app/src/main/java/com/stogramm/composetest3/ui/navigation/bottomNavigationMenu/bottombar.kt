package com.stogramm.composetest3.ui.navigation.bottomNavigationMenu


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stogramm.composetest3.ui.navigation.NotificationNavigationRoute
import com.stogramm.composetest3.ui.navigation.newsFeedNavigationRoute
import com.stogramm.composetest3.ui.navigation.userProfileNavigationRoute



@Composable
fun BottomNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val navigation: (id: String) -> Unit = {
        navController.navigate(it) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    val home = currentDestination?.hierarchy?.any { s -> s.route == newsFeedNavigationRoute }
    val profile = currentDestination?.hierarchy?.any { s -> s.route?.contains(userProfileNavigationRoute) == true }
    val notification =
        currentDestination?.hierarchy?.any { s -> s.route == NotificationNavigationRoute }


    val boxSize = 100.dp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(modifier = Modifier.clip(
            CircleShape).size(boxSize).clickable { navigation(newsFeedNavigationRoute) }, contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "home",
                tint = if (home == true) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
            )

        }
        Box(modifier = Modifier.clip(
            CircleShape).size(boxSize).clickable { navigation(userProfileNavigationRoute) }, contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "profile",
                tint = if (profile == true) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
            )

        }
        Box(modifier = Modifier.clip(
            CircleShape).size(boxSize).clickable { navigation(NotificationNavigationRoute) }, contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "notifications",
                tint = if (notification == true) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground
            )

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun pr() {

    BottomNavigation(rememberNavController())
}