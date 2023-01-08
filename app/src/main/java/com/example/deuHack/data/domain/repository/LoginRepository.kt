package com.example.deuHack.data.domain.repository

import com.example.deuHack.data.domain.model.LoginModel
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(loginModel: LoginModel):Flow<Any>

    fun register():Flow<Any>
}