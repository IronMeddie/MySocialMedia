package com.ironmeddie.data.data.utils

sealed class ResponseFire<out T> {
    data class Succes<out T>(val value: T) : ResponseFire<T>()
    data class Failure<out T>(val errorMessage : String): ResponseFire<Nothing>()
    object Loading : ResponseFire<Nothing>()
}