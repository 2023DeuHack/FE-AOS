package com.example.deuHack.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deuHack.data.data.model.ApiResult
import com.example.deuHack.data.domain.model.LoginModel
import com.example.deuHack.data.domain.model.PostModel
import com.example.deuHack.data.domain.repository.LoginRepository
import com.example.deuHack.data.domain.repository.PostingRepository
import com.example.deuHack.ui.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val postingRepository: PostingRepository,
    @ApplicationContext private val context:Context
    ) :ViewModel(){
    private val token = Utils.getToken(context)
    private val mutablePostingListState = MutableStateFlow(listOf(PostModel()))
    val positngListState get() = mutablePostingListState.asStateFlow()

    init {
        viewModelScope.launch {
            getPostList()
        }
    }

    fun getPostList(){
        viewModelScope.launch {
            postingRepository.posting(token).collectLatest {
                when(it){
                    is List<*> ->{
                        mutablePostingListState.emit(it as List<PostModel>)
                        Log.d("test",it.toString())
                    }
                    is ApiResult.Fail->{
                        Toast.makeText(context,"데이터를 받는데 실패하였습니다.",Toast.LENGTH_SHORT).show()
                    }
                    is ApiResult.Exception ->{
                        Log.d("test",it.e.message.toString())
                    }
                }

            }
        }
    }


}