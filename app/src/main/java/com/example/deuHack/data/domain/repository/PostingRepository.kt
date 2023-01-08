package com.example.deuHack.data.domain.repository

import com.example.deuHack.data.data.model.ApiResult
import com.example.deuHack.data.domain.model.PostModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostingRepository {
    fun posting(token:String):Flow<Any>

    fun newPosting(token:String,postModel: HashMap<String, RequestBody>,postingImage: MultipartBody.Part) : Flow<Any>

    fun lovePost(token:String,id:Int):Flow<String>

    fun getMyPosting(token:String):Flow<Any>

    fun getPostingReply(token:String,id:Int):Flow<Any>
}