package com.example.deuHack.data.domain.model

data class ReplyModel (
    val id:Int,
    val postId:Int,
    val user:String,
    val created_at:String,
    val comment:String
        )