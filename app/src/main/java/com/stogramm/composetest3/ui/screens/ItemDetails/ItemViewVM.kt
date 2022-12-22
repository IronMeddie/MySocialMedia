package com.stogramm.composetest3.ui.screens.ItemDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.ironmeddie.data.models.Post


class ItemViewVM(savedStateHandle: SavedStateHandle, application : Application) : AndroidViewModel(application) {
    private val userId: Post = checkNotNull(savedStateHandle["task"])

    fun liked(item: Post) {
//        item.liked = !item.liked
//        if (item.liked) item.likes++ else item.likes--
    }
}

