package com.example.deuHack.data.domain.repository

import android.util.Log
import com.example.deuHack.data.data.model.*
import com.example.deuHack.data.domain.api.ApiService
import com.example.deuHack.data.domain.model.UserModel
import com.example.deuHack.data.utils.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val apiService: ApiService):
    SearchRepository {
    override fun searchUser(token:String,nickName: String): Flow<Any> = handleFlowApi{
        apiService.getUser(token,nickName)
    }.catch {
        Log.d("test",it.message.toString())
    }.map {
        when(it){
            is ApiResult.Success->{
                it.result.asDomain()
            }
            else->{
            }
        }
    }

    override fun getAllUser(token: String): Flow<Any> = handleFlowApi {
        apiService.getAllUser(token)
    }.map {
        when(it){
            is ApiResult.Success->{
                it.result.asDomain()
            }
            else->{
            }
        }
    }

    override fun followeUser(token: String,nickName: String): Flow<ApiResult<UserFollowResponseDTO>> = handleFlowApi {
        apiService.followUser(token, UserFollowRequestDTO(nickName))
    }

    fun List<UserSearchRequestDTO>.asDomain() = map {
        it.asDomain()
    }

    fun UserSearchRequestDTO.asDomain() = UserModel(
        this.id,this.username,this.nickname,this.introduce,this.profile_image
    )
}