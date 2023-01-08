package com.example.deuHack.data.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO(
    @SerializedName("access_token")val access_token:String,
    @SerializedName("refresh_token")val refresh_token:String,
    @SerializedName("user")val user: UserResponseDTO
)
