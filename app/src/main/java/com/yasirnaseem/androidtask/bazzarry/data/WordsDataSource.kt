package com.yasirnaseem.androidtask.bazzarry.data

import com.yasirnaseem.androidtask.network.data.Result

interface WordsDataSource {

    suspend fun getWebsiteContent(): Result<Map<String, Int>>
}