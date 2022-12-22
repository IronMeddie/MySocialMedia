package com.ironmeddie.data.data.remote.utils

import com.google.firebase.Timestamp
import com.ironmeddie.data.models.Post
import java.util.*

data class PostDTO(
    val id: String =  "",
    val author: String=  "",
    val timeStamp: Timestamp = Timestamp(Date()),
    val descr: String=  "",
    var likes: Int = 0,
    val commentcount: Int = 0,
    val fileUrl: String = "https://i.pinimg.com/originals/fe/a5/6e/fea56edde1aa7e4d8874a03cd1a95bd6.jpg",
    val liked : List<String> = emptyList(),
    val friendsList: List<String> = emptyList()
){
    fun toPost(): Post {
        return Post(
             id =  id,
         author= author,
         timeStamp = timeStamp.toString(),
         descr=  descr,
         likes = likes,
         commentcount = commentcount,
         fileUrl = fileUrl,
         liked  = liked
        )
    }
}