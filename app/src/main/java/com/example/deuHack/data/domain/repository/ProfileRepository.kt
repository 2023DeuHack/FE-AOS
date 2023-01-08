package com.example.deuHack.data.domain.repository

import com.example.deuHack.data.data.model.ApiResult
import com.example.deuHack.data.data.model.FollowResponseDTO
import com.example.deuHack.data.data.model.UserResponseDTO
import com.example.deuHack.data.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProfileRepository {
    fun modifyProfileInfo(token:String, userHashmap: HashMap<String, RequestBody>, profileImg: MultipartBody.Part):Flow<Any>
    fun getMyFollowInfo(token:String):Flow<ApiResult<FollowResponseDTO>>
    fun getMyInfo(token:String):Flow<ApiResult<UserResponseDTO>>
    fun getMyAllInfo(token:String):Flow<UserModel>
}