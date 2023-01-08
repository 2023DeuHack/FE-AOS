package com.example.deuHack.ui.posting

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deuHack.data.domain.model.PostImage
import com.example.deuHack.data.domain.model.PostModel
import com.example.deuHack.data.domain.repository.PostingRepository
import com.example.deuHack.ui.Utils.getToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PostingViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val postingRepository: PostingRepository
):ViewModel(){
    val token = getToken(context)
    val mutablePostModel = MutableLiveData<PostModel>(PostModel())
    val postModel get() = mutablePostModel

    fun postNewPosting(){
        viewModelScope.launch {
            val file= File(postModel.value?.image?.get(0)?.image)
            val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
            val postImage = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val title: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),postModel.value?.title!!)
            val content: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(),postModel.value?.content!!)

            postingRepository.newPosting(
                token,
                hashMapOf(
                    Pair("title",title),
                    Pair("content",content),
                ),
                postImage
            ).collectLatest {
                when(it){
                    is PostModel ->{
                        Toast.makeText(context,"게시글이 작성되었습니다.",Toast.LENGTH_SHORT).show()
                    }
                    else->{

                    }
                }
                postingRepository.posting(token)
            }
        }
    }

}