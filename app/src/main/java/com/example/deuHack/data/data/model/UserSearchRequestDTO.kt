package com.example.deuHack.data.data.model

data class UserSearchRequestDTO(
    val id:Int=0,
    val password : String="",
    val last_login:String="",
    val email:String="",
    val username:String="",
    val nickname:String="",
    val profile_image:String?="",
    val introduce:String="",
    val is_active:Boolean=false,
    val is_admin:Boolean=false
)
