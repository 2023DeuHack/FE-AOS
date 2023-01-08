package com.example.deuHack.data.domain.model

import androidx.databinding.ObservableField

data class RegisterModel (
    val userName:ObservableField<String> = ObservableField(""),
    val nickName:ObservableField<String> = ObservableField(""),
    val passWord1:ObservableField<String> = ObservableField(""),
    val passWord2:ObservableField<String> = ObservableField(""),
    val email:ObservableField<String> = ObservableField("")
    )