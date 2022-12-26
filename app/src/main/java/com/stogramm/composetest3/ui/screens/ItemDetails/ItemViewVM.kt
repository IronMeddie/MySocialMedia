package com.stogramm.composetest3.ui.screens.ItemDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ironmeddie.data.models.Post


class ItemViewVM(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val userId: Post = checkNotNull(savedStateHandle["task"])

    fun liked(item: Post) {
//        item.liked = !item.liked
//        if (item.liked) item.likes++ else item.likes--
    }
}

