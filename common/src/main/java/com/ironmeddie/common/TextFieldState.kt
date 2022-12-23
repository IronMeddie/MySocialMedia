package com.ironmeddie.common

data class TextFieldState (
        val text : String = "",
        val hint: String = "",
        var isHintVisible: Boolean = true
)