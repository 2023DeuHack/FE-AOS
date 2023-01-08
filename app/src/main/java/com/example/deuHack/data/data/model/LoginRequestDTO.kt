package com.example.deuHack.data.data.model

data class LoginRequestDTO(
    val email:String,
    val password:String
)

data class TokenRequestDTO(
    val token:String
)