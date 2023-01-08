package com.example.deuHack.data.data.model

data class PostingReplyResponseDTO (
    val id:Int,
    val post:Int,
    val user:String,
    val created_at:String,
    val comment:String
)