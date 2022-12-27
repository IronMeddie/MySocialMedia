package com.ironmeddie.data.domain.models

import com.google.firebase.Timestamp
import com.google.type.DateTime
import java.time.LocalDateTime
import java.util.*


data class Post(
    val id: String =  "",
    val author: String=  "",
    val timeStamp: Date = Date(),
    val descr: String=  "",
    var likes: Int = 0,
    val commentcount: Int = 0,
    val fileUrl: String = "https://i.pinimg.com/originals/fe/a5/6e/fea56edde1aa7e4d8874a03cd1a95bd6.jpg",
    val liked : List<String> = emptyList(),
    val time : LocalDateTime = LocalDateTime.now()
)

data class PostDto(
    val id: String =  "",
    val author: String=  "",
    val timeStamp: Timestamp = Timestamp.now(),
    val descr: String=  "",
    var likes: Int = 0,
    val commentcount: Int = 0,
    val fileUrl: String = "https://i.pinimg.com/originals/fe/a5/6e/fea56edde1aa7e4d8874a03cd1a95bd6.jpg",
    val liked : List<String> = emptyList(),
    val time : String = ""
)


