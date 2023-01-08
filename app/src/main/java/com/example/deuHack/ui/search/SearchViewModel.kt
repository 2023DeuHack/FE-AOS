package com.example.deuHack.ui.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deuHack.data.data.model.ApiResult
import com.example.deuHack.data.domain.model.UserModel
import com.example.deuHack.data.domain.repository.SearchRepository
import com.example.deuHack.ui.Utils.getToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val searchRepository: SearchRepository
    ):ViewModel() {
    private val mutableUserState = MutableStateFlow<List<UserModel>>(listOf(UserModel()))
    val userState get()= mutableUserState.asStateFlow()

    private val mutableAllUserState = MutableStateFlow(listOf(UserModel()))
    val userAllState get() = mutableAllUserState.asStateFlow()

    private val mutableUserFollowState = MutableStateFlow("")
    val userFollowState get() = mutableUserFollowState.asStateFlow()

    fun searchUser(nickname:String){
        viewModelScope.launch {
            searchRepository.searchUser(
                getToken(context),
                nickname
            ).collectLatest {
                when(it){
                    is List<*> ->{
                        mutableUserState.emit(it as List<UserModel>)
                    }
                    is ApiResult.Fail -> {
                        Log.d("test",it.message)
                    }
                    is ApiResult.Exception -> {
                        Log.d("test",it.e.message.toString())
                    }
                }
            }
        }
    }

    fun getAllUser(){
        viewModelScope.launch {
            searchRepository.getAllUser(token = getToken(context)).collectLatest {
                when(it){
                    is List<*> ->{
                        mutableAllUserState.emit(it as List<UserModel>)
                    }
                    is ApiResult.Fail -> {
                        Log.d("test",it.message)
                    }
                    is ApiResult.Exception -> {
                        Log.d("test",it.e.message.toString())
                    }
                }
            }
        }
    }

    fun followUser(nickName:String){
        viewModelScope.launch {
            searchRepository.followeUser(getToken(context),nickName).collectLatest {
                when(it){
                    is ApiResult.Success ->{
                        mutableUserFollowState.emit(it.result.follow)
                    }
                    is ApiResult.Fail -> {
                        Log.d("test",it.message)
                    }
                    is ApiResult.Exception -> {
                        Log.d("test",it.e.message.toString())
                    }
                    else -> {

                    }
                }
            }
        }
    }
}