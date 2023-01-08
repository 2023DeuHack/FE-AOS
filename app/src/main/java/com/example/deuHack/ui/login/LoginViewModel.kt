package com.example.deuHack.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deuHack.data.data.model.ApiResult
import com.example.deuHack.data.domain.model.LoginModel
import com.example.deuHack.data.domain.model.RegisterModel
import com.example.deuHack.data.domain.repository.LoginRepository
import com.example.deuHack.data.domain.repository.ProfileRepository
import com.example.deuHack.ui.Utils.getToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context:Context,
    private val profileRepository: ProfileRepository,
    private val loginRepository: LoginRepository
): ViewModel() {
    val mutableLoginModel = MutableLiveData<LoginModel>(LoginModel())
    val loginModel:LiveData<LoginModel> get() = mutableLoginModel
    private val mutableLoginState = MutableLiveData<Boolean>(false)
    val loginState:LiveData<Boolean> get() = mutableLoginState

    val mutableRegisterModel = MutableLiveData(RegisterModel())
    val registerModel :LiveData<RegisterModel> get() = mutableRegisterModel
    val mutableRegisterState = MutableLiveData(false)
    val registerState:LiveData<Boolean> get() = mutableRegisterState

    init {
        AutoLogin()
    }

    fun AutoLogin(){
        viewModelScope.launch {
            profileRepository.getMyInfo(getToken(context)).collectLatest {
                when(it){
                    is ApiResult.Success<*> ->{
                        mutableLoginState.value=true
                    }
                    else ->{
                        mutableLoginState.value=false
                    }
                }
            }
        }
    }

    fun login(){
        viewModelScope.launch {
            loginRepository.login(loginModel.value?:LoginModel()).collectLatest {
                when(it){
                    is LoginModel ->{
                        context.applicationContext
                            .getSharedPreferences("accessToken", Context.MODE_PRIVATE)
                            .edit()
                            .putString("accessToken","Bearer "+it.accessToken)
                            .apply()
                        Toast.makeText(context,"${it.email.get()} 님 환영합니다.",Toast.LENGTH_SHORT).show()
                        mutableLoginState.value=true
                    }
                    is ApiResult.Fail->{
                        Toast.makeText(context,"아이디 또는 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
                        mutableLoginState.value=false
                    }
                    is ApiResult.Exception ->{
                        Toast.makeText(context,"통신 장애 발생 : ${it.e.message}",Toast.LENGTH_SHORT).show()
                        mutableLoginState.value=false
                    }
                }
            }
        }
    }

    fun register(){
        viewModelScope.launch {
            loginRepository.register(registerModel.value?: RegisterModel()).collectLatest {
                when(it){
                    is ApiResult.Success<*> ->{
                        Log.d("test","회원가입 성공")
                        mutableRegisterState.value=true
                    }
                    is ApiResult.Fail->{
                        Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                        mutableRegisterState.value=false
                    }
                    is ApiResult.Exception ->{
                        Toast.makeText(context,it.e.message.toString(),Toast.LENGTH_SHORT).show()
                        mutableRegisterState.value=false
                    }
                }
            }
        }
    }
}