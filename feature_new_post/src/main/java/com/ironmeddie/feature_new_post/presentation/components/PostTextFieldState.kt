package com.ironmeddie.feature_new_post.presentation.components

data class PostTextFieldState (
        val text : String = "",
        val hint: String = "",
        val isHintVisible: Boolean = true
)