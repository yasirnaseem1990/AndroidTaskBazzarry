package com.yasirnaseem.androidtask.network.data.di

import com.yasirnaseem.androidtask.network.data.ErrorType
import com.yasirnaseem.androidtask.network.data.Result
import com.yasirnaseem.androidtask.network.data.Result.Error
import com.yasirnaseem.androidtask.network.data.Result.Success
import retrofit2.HttpException
import java.io.IOException

class NetworkHandler {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> {
        return try {
            Success(apiCall.invoke())
        } catch (e: Exception) {
            Error(mapError(e))
        }
    }

    private fun mapError(e: Exception): ErrorType {
        return when (e) {
            is IOException -> ErrorType.Network
            is HttpException -> ErrorType.Http(e.code(), e.message())
            else -> ErrorType.Unknown
        }
    }
}