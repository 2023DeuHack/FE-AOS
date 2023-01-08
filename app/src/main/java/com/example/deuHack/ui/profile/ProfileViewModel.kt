package com.example.deuHack.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deuHack.data.domain.model.UserModel
import com.example.deuHack.data.domain.repository.ProfileRepository
import com.example.deuHack.ui.Utils.getToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import kotlin.Pair


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    @ApplicationContext private val context: Context
) :ViewModel(){
    private val mutableUserState = MutableStateFlow(UserModel())
    val userState : StateFlow<UserModel> = mutableUserState.asStateFlow()
    private val token = getToken(context)

    init {
        getUserAllInfo()
    }

    fun getUserAllInfo(){
        viewModelScope.launch {
            profileRepository.getMyAllInfo(token)
                .flowOn(Dispatchers.IO)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(3000L),
                    initialValue = UserModel()
                ).collectLatest {
                    mutableUserState.emit(it)
                }
        }

    }

    fun convertInputStreamToFile(input: InputStream?): File? {
        val tempFile = File.createTempFile(java.lang.String.valueOf(input.hashCode()), ".tmp")
        tempFile.deleteOnExit()
        return tempFile
    }

    fun modifyUserInfo(userModel: UserModel){
        viewModelScope.launch {
            val file=File(userModel.userProfileImage)

            val username:RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userModel.userName)
            val nickname:RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userModel.userNickName)
            val introduce:RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),userModel.userIntroduce)
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val profileImg = MultipartBody.Part.createFormData("profile_image", file.name, requestFile)

            profileRepository.modifyProfileInfo(
                token,
                hashMapOf(
                    Pair("username",username),
                    Pair("introduce",introduce),
                    Pair("nickname",nickname)
                ),
                profileImg
            ).collectLatest {
                /*when(it){
                    is UserModel->{
                        mutableUserInfo.emit(it)
                    }
                    is ApiResult.Fail->{
                        Log.d("test",it.message.toString())
                    }
                    is ApiResult.Exception->{
                        Log.d("test",it.e.message.toString())
                    }
                }*/
            }
            getUserAllInfo()
        }
    }
}