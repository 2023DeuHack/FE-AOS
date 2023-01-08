package com.example.deuHack.data.domain.repository

import com.example.deuHack.data.domain.model.LoginModel
import com.example.deuHack.data.domain.model.RegisterModel
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(loginModel: LoginModel):Flow<Any>

    fun register(registerModel: RegisterModel):Flow<Any>
}