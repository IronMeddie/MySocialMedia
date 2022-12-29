package com.stogramm.composetest3.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.stogramm.composetest3.ui.navigation.navHost.MainNavHost
import com.stogramm.composetest3.ui.theme.Composetest3Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Composetest3Theme {
                MainNavHost()
            }
        }
    }
}




