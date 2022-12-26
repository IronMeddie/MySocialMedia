package com.stogramm.composetest3.ui.screens.ItemDetails

import androidx.navigation.NavController

fun NavController.navigateToItemDetails(id: String){
    this.navigate(ItemViewerScreenRoute + "/${id}")
}