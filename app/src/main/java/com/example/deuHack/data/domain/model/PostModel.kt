package com.example.deuHack.data.domain.model

data class PostModel(
    val id:Int = 0,
    val title:String = "d",
    var content:String ="",
    val profile:String?= "",
    var image:List<PostImage>? = listOf(PostImage("")),
    val created_at:String= "",
    val user:String= "",
    val heartNumber:List<LikePostUser>? =null,
    val reply:String= ""
)

data class PostImage(
    var image:String? =""
)

data class LikePostUser(
    val follower:String?
)
