package com.example.deuHack.data.data.model

import com.google.gson.annotations.SerializedName

data class RegisterRequestDTO(
    @SerializedName("username") val userName:String,
    @SerializedName("email") val email:String,
    @SerializedName("password1") val passWord1:String,
    @SerializedName("password2") val passWord2:String,
    @SerializedName("nickname") val nickName:String
    )