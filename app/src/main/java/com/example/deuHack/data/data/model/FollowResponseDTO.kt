package com.example.deuHack.data.data.model

data class FollowResponseDTO(
    val following:List<String>,
    val followers:List<String>
)

data class UserFollowResponseDTO(
    val follow:String
)

data class UserFollowRequestDTO(
    val nickname:String
)
