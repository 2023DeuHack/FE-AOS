package com.example.deuHack.data.data.model


sealed class ApiResult<out T> {
    object Loading : ApiResult<Nothing>()
    data class Success<out T>(val result:T): ApiResult<T>()
    data class Fail(val code:String,val message:String, val status:String): ApiResult<Nothing>()
    data class Exception(val e:Throwable): ApiResult<Nothing>()
}