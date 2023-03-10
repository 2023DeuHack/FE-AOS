package com.example.deuHack.data.domain.repository

import androidx.databinding.ObservableField
import com.example.deuHack.data.data.model.*
import com.example.deuHack.data.domain.api.ApiService
import com.example.deuHack.data.domain.model.LoginModel
import com.example.deuHack.data.domain.model.RegisterModel
import com.example.deuHack.data.utils.HandleFlowUtils.handleFlowApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    LoginRepository {
    override fun login(loginModel: LoginModel): Flow<Any> =
        handleFlowApi{
            apiService.login(
                loginModel.asDomain()
            )
        }.map {
            when(it){
                is ApiResult.Success ->{
                    it.result.asDomain()
                }
                is ApiResult.Fail-> {
                    it
                }
                is ApiResult.Exception->{
                    it
                }
                else -> {}
            }
        }

    override fun register(registerModel: RegisterModel): Flow<Any> = handleFlowApi {
        apiService.register(RegisterRequestDTO(
            registerModel.userName.get()?:"",
            registerModel.email.get()?:"",
            registerModel.passWord1.get()?:"",
            registerModel.passWord2.get()?:"",
            registerModel.nickName.get()?:"")
        )
    }

    fun LoginResponseDTO.asDomain() = LoginModel(
        this.access_token,
        this.refresh_token,
        ObservableField(this.user.email),
        ObservableField("")
    )

    fun LoginModel.asDomain() = LoginRequestDTO(
        this.email.get()?:"",
        this.password.get()?:""
    )
}