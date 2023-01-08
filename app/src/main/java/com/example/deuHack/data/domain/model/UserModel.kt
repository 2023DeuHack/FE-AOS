package com.example.deuHack.data.domain.model

data class UserModel(
    val userId:Int=0,
    val userName:String="",
    val userNickName:String="",
    val userIntroduce:String="",
    var userProfileImage:String?="",
    var following:List<String>?=null,
    var followers:List<String>?=null
)

