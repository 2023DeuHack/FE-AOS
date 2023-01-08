package com.example.deuHack.data.domain.repository

import com.example.deuHack.data.data.model.*
import com.example.deuHack.data.domain.api.ApiService
import com.example.deuHack.data.domain.model.UserFollowModel
import com.example.deuHack.data.domain.model.UserModel
import com.example.deuHack.data.utils.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService
    ): ProfileRepository {

    override fun modifyProfileInfo(
        token:String,
        userHashMap: HashMap<String,RequestBody>,
        profileImg: MultipartBody.Part
    ): Flow<Any> = handleFlowApi{
        apiService.setUserInfo(token, userHashMap ,profileImg)
    }.map {
        when(it){
            is ApiResult.Success->{
                it.result.asDomain()
            }
            else->{

            }
        }
    }

    override fun getMyFollowInfo(token:String): Flow<ApiResult<FollowResponseDTO>> = handleFlowApi {
        apiService.getMyFollowList(token)
    }

    override fun getMyInfo(token:String): Flow<ApiResult<UserResponseDTO>> = handleFlowApi {
        apiService.getUserInfo(token)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getMyAllInfo(token:String) =
        getMyInfo(token).flatMapLatest {
            flow{
                when(it){
                    is ApiResult.Success -> {
                        emit(it.result)
                    }
                    else ->{

                    }
                }
            }.zip(getMyFollowInfo(token).flatMapLatest {
                flow{
                    when(it){
                        is ApiResult.Success -> {
                            emit(it.result)
                        }
                        else ->{

                        }
                    }
                }
            }){info,follow->
                UserModel(
                    info.pk,
                    info.userName,
                    info.nickName,
                    info.introduce,
                    info.profileImage,
                    follow.following,
                    follow.followers
                )
            }
        }



    fun UserModel.asDTO() = UserChangeInfoRequestDTO(
        this.userName,
        this.userIntroduce
    )

    fun UserResponseDTO.asDomain() = UserModel(
        this.pk,
        "",
        this.nickName,
        this.introduce,
        this.profileImage
    )

    fun FollowResponseDTO.asDomain() = UserFollowModel(
        this.following,
        this.followers
    )
}