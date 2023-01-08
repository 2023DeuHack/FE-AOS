package com.example.deuHack.data.data.model

data class PostingResponseDTO(
    val id:Int,
    val title:String,
    val content:String,
    val created_at:String,
    val images:List<PostingImageDTO>,
    val user:String
)

data class PostingRequestDTO(
    val title:String,
    val content:String,
    val images:List<PostingImageDTO>?
)