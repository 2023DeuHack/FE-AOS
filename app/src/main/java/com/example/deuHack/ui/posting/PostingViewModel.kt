package com.example.deuHack.ui.posting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deuHack.data.domain.model.PostImage
import com.example.deuHack.data.domain.model.PostModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PostingViewModel @Inject constructor():ViewModel(){

    val mutablePostModel = MutableLiveData<PostModel>(PostModel())

    val mutableImage = MutableLiveData(PostImage())

}