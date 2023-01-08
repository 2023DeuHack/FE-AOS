package com.example.deuHack.data.data.model

import com.google.gson.annotations.SerializedName

data class UserResponseDTO(
    @SerializedName("pk") val pk:Int,
    @SerializedName("email") val email:String,
    @SerializedName("nickname") val nickName:String,
    @SerializedName("username") val userName:String,
    @SerializedName("profile_image") val profileImage:String,
    @SerializedName("introduce") val introduce:String
)
