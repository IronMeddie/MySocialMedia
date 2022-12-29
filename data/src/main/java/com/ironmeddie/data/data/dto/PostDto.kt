package com.ironmeddie.data.data.dto

import com.google.firebase.Timestamp
import com.ironmeddie.data.domain.models.Post
import java.time.Instant
import java.time.ZoneId

data class PostDto(
    val id: String = "",
    val author: String=  "",
    val timeStamp: Timestamp = Timestamp.now(),
    val descr: String=  "",
    val fileUrl: String = "https://i.pinimg.com/originals/fe/a5/6e/fea56edde1aa7e4d8874a03cd1a95bd6.jpg",
    val likes : List<String> = emptyList(),
    val comments: List<String> = emptyList(),
){
    fun toPost() : Post{
       return Post(
            id = this.id,
            author = this.author,
            descr = this.descr,
            fileUrl = this.fileUrl,
            time = Instant.ofEpochSecond(this.timeStamp.seconds, this.timeStamp.nanoseconds.toLong() ).atZone(
                ZoneId.systemDefault()).toLocalDateTime(),
            likes = this.likes

        )
    }
}