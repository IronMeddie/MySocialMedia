package com.ironmeddie.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle


@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    hintAlign: Alignment = Alignment.TopStart,
    bodyAlign: Alignment = Alignment.TopStart,
    maxLnes : Int = 20,
    onFocusChange: (FocusState)-> Unit
) {
    Box(modifier = modifier) {
        BasicTextField(value = text, onValueChange = onValueChange, singleLine = singleLine, textStyle = textStyle, maxLines = maxLnes, modifier = Modifier
            .matchParentSize()
            .onFocusChanged { onFocusChange(it) }
            .align(bodyAlign)
        )
        if (isHintVisible){
            Text(text = hint, style = textStyle, color = Color.DarkGray, modifier = Modifier.align(hintAlign))
        }

    }



}