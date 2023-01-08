package com.example.deuHack.data.domain.repository

import com.example.deuHack.data.data.model.*
import com.example.deuHack.data.domain.api.ApiService
import com.example.deuHack.data.domain.model.LikePostUser
import com.example.deuHack.data.domain.model.PostImage
import com.example.deuHack.data.domain.model.PostModel
import com.example.deuHack.data.domain.model.UserFollowModel
import com.example.deuHack.data.utils.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostingRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    PostingRepository {
    override fun posting(token:String): Flow<Any> = handleFlowApi{
        apiService.getPostingList(token)
    }.map {
        when(it){
            is ApiResult.Success->{
                it.result.asDomain()
            }
            else->{

            }
        }
    }

    override fun newPosting(
        token: String,
        postModel: HashMap<String,RequestBody>,
        postingImage: MultipartBody.Part): Flow<Any> = handleFlowApi {
        apiService.postNewPosting(token,postModel,postingImage)
    }.map {
        when(it){
            is ApiResult.Success->{
                it.result.asDomain()
            }
            is ApiResult.Fail->{
                it
            }
            is ApiResult.Exception->{
                it
            }
            else -> {

            }
        }
    }

    override fun lovePost(token: String, id: Int): Flow<String> = handleFlowApi {
        apiService.lovePost(token,id)
    }.map {
        when(it){
            is ApiResult.Success->{
                it.result.nickname
            }
            is ApiResult.Fail->{
                it.message
            }
            is ApiResult.Exception->{
                it.e.message.toString()
            }
            else -> {"error"}
        }
    }

    override fun getMyPosting(token: String): Flow<Any> = handleFlowApi {
        apiService.getMyPosting(token)
    }.map {
        when(it){
            is ApiResult.Success->{
                it.result.asDomain()
            }
            is ApiResult.Fail -> {
                it
            }
            is ApiResult.Exception->{
                it
            }
            else->{it}
        }
    }

    override fun getPostingReply(token: String, id: Int): Flow<Any> = handleFlowApi {
        apiService.getPostingReply(token,id)
    }.map {
        when(it){
            is ApiResult.Success ->{

            }
            is ApiResult.Fail -> {
                it
            }
            is ApiResult.Exception->{
                it
            }
            else->{it}
        }
    }

    fun List<PostingListResponseDTO>.asDomain() = map { it.asDomain() }

    fun PostingListResponseDTO.asDomain() = PostModel(
        this.id,
        this.title,
        this.content,
        this.profile.asDomain(),
        this.images.asDomain(),
        this.created_at,
        this.user,
        this.like.asDomainLike(),
        ""
    )

    fun List<PostingLikeResponseDTO>.asDomainLike() = map {
        it.asDomainLike()
    }

    fun PostingLikeResponseDTO.asDomainLike() = LikePostUser(
        this.nickname
    )

    fun PostingProfileImageDTO.asDomain() = this.profile_image

    @JvmName("asDomainPostingImageDTO")
    fun List<PostingImageDTO>.asDomain() = map { it.asDomain() }

    fun PostingResponseDTO.asDomain() = PostModel(
        this.id,
        this.title,
        this.content,
        "",
        this.images.asDomain(),
        this.created_at,
        this.user,
        null,""
    )

    fun PostingImageDTO.asDomain() = PostImage(
        this.image
    )

    fun PostModel.asDTO() = PostingRequestDTO(
        this.title,this.content,this.image?.asDTO()
    )

    fun List<PostImage>.asDTO() = map { it.asDTO() }

    fun PostImage.asDTO() = PostingImageDTO(this.image)
}