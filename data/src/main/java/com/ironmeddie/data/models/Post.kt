package com.ironmeddie.data.models


data class Post(
    val id: String =  "",
    val author: String=  "",
    val timeStamp: String = "",
    val descr: String=  "",
    var likes: Int = 0,
    val commentcount: Int = 0,
    val fileUrl: String = "https://i.pinimg.com/originals/fe/a5/6e/fea56edde1aa7e4d8874a03cd1a95bd6.jpg",
    val liked : List<String> = emptyList()
)


