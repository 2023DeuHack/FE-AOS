package com.example.deuHack.data.domain.repository

import kotlinx.coroutines.flow.Flow

interface PostingRepository {
    fun posting(token:String):Flow<Any>
}