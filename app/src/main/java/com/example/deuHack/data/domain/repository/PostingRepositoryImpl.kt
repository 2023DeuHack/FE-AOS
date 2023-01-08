package com.example.deuHack.data.domain.repository

import com.example.deuHack.data.data.model.ApiResult
import com.example.deuHack.data.data.model.PostingImageDTO
import com.example.deuHack.data.data.model.PostingListResponseDTO
import com.example.deuHack.data.data.model.PostingProfileImageDTO
import com.example.deuHack.data.domain.api.ApiService
import com.example.deuHack.data.domain.model.PostImage
import com.example.deuHack.data.domain.model.PostModel
import com.example.deuHack.data.utils.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    fun List<PostingListResponseDTO>.asDomain() = map { it.asDomain() }

    fun PostingListResponseDTO.asDomain() = PostModel(
        this.id,
        this.title,
        this.content,
        this.profile.asDomain(),
        this.images.asDomain(),
        this.created_at,
        this.user,
        this.like.size,
        ""
    )

    fun PostingProfileImageDTO.asDomain() = this.profile_image

    @JvmName("asDomainPostingImageDTO")
    fun List<PostingImageDTO>.asDomain() = map { it.asDomain() }

    fun PostingImageDTO.asDomain() = PostImage(
        this.image
    )
}

