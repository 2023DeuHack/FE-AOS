package com.example.deuHack.data.domain.model

import androidx.databinding.ObservableField

data class LoginModel(
    val accessToken:String="",
    val refreshToken:String="",
    val email: ObservableField<String> = ObservableField(""),
    val password:ObservableField<String> = ObservableField("")

)