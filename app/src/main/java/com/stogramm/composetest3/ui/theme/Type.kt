package com.stogramm.composetest3.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.stogramm.composetest3.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(Font(com.ironmeddie.registerscreen.R.font.open_sans)),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
    ),
    body2 = TextStyle(
        fontFamily = FontFamily(Font(com.ironmeddie.registerscreen.R.font.open_sans)),
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
    ),


    subtitle1 = TextStyle(
        fontFamily = FontFamily(Font(com.ironmeddie.registerscreen.R.font.open_sans)),
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        color = GreyMain
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)