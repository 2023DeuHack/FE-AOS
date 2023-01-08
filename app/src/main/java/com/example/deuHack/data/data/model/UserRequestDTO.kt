package com.example.deuHack.data.data.model

import com.google.gson.annotations.SerializedName

data class UserRequestDTO(
    @SerializedName("nickname") val nickName:String?,
    @SerializedName("profile_image") val profileImage:String?,
    @SerializedName("introduce") val introduce:String?
)