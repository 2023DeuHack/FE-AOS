package com.example.deuHack.data.utils

import android.util.Log
import com.example.deuHack.data.data.model.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

object HandleFlowUtils {
    fun <T : Any> handleFlowApi(
        execute: suspend () -> T
    ): Flow<ApiResult<T>> = flow {
        try {
            emit(ApiResult.Success(execute()))
        } catch (e: HttpException) {
            emit(ApiResult.Fail(e.code().toString(), e.message(), e.code().toString()))
        } catch (e:NullPointerException){

        }
        catch (e: Exception) {
            emit(ApiResult.Exception(e=e))
        }
    }
}