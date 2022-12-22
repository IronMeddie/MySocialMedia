package com.ironmeddie.data.data.remote.utils

sealed class MyNodes{
    data class Posts(val postId: String) : MyNodes()
    data class Username(val username: String): MyNodes()
    data class Email(val email: String): MyNodes()
    data class FirstName(val firstName: String): MyNodes()
    data class SecondName(val secondname: String): MyNodes()
    data class BirthDay(val birthday: String): MyNodes()
    data class Sex(val sex: String): MyNodes()
}