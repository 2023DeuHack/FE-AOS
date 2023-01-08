package com.example.deuHack.data.domain.api

import android.media.session.MediaSession.Token
import com.example.deuHack.data.data.*
import com.example.deuHack.data.data.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface ApiService {
    @POST("registration/")
    suspend fun register(@Body userInfo: RegisterRequestDTO)

    @POST("dj-rest-auth/login/")
    suspend fun login(@Body userInfo: LoginRequestDTO): LoginResponseDTO

    @POST("dj-rest-auth/token/verify/")
    suspend fun autoLogin(@Body token:TokenRequestDTO) : Boolean

    @POST("post/")
    suspend fun posting(@Header("Authorization") token:String,@Body item: PostingDTO)

    @GET("dj-rest-auth/user/")
    suspend fun getUserInfo(@Header("Authorization") token:String): UserResponseDTO

    @Multipart
    @PUT("dj-rest-auth/user/")
    suspend fun setUserInfo(
        @Header("Authorization") token:String,
        @PartMap userChangeInfo: HashMap<String, RequestBody>,
        @Part profile_image : MultipartBody.Part?
    ): UserResponseDTO

    @GET("accounts/search/")
    suspend fun getUser(@Header("Authorization") token:String,@Query("search") nickname:String):List<UserSearchRequestDTO>

    @GET("accounts/search/")
    suspend fun getAllUser(@Header("Authorization") token:String):List<UserSearchRequestDTO>

    @POST("accounts/follow/")
    suspend fun followUser(@Header("Authorization") token:String,@Body nickname:UserFollowRequestDTO):UserFollowResponseDTO

    @GET("accounts/follow/")
    suspend fun getMyFollowList(@Header("Authorization") token:String) : FollowResponseDTO

    @GET("post/home")
    suspend fun getPostingList(@Header("Authorization") token:String):List<PostingListResponseDTO>
}