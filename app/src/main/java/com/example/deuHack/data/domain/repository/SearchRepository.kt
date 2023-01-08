package com.example.deuHack.data.domain.repository

import com.example.deuHack.data.data.model.ApiResult
import com.example.deuHack.data.data.model.UserFollowResponseDTO
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchUser(token:String,nickName:String) : Flow<Any>

    fun getAllUser(token:String):Flow<Any>

    fun followeUser(token:String,nickName: String):Flow<ApiResult<UserFollowResponseDTO>>
}