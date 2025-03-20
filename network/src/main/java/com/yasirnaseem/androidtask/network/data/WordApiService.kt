package com.yasirnaseem.androidtask.network.data

import retrofit2.http.GET

interface WordApiService {
    @GET("/")
    suspend fun getWebsiteContent(): String
}