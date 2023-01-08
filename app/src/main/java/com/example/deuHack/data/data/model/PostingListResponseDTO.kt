package com.example.deuHack.data.data.model

data class PostingListResponseDTO(
    val id :Int =0,
    val title:String,
    val content:String,
    val created_at:String,
    val images:List<PostingImageDTO>,
    val user:String,
    val profile:PostingProfileImageDTO,
    val like:List<String>
)

data class PostingImageDTO(
    val image:String?
)

data class PostingProfileImageDTO(
    val profile_image:String?
)

