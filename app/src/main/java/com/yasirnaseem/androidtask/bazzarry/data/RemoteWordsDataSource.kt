package com.yasirnaseem.androidtask.bazzarry.data

import com.yasirnaseem.androidtask.network.data.Result
import com.yasirnaseem.androidtask.network.data.WordApiService
import com.yasirnaseem.androidtask.network.data.di.NetworkHandler
import javax.inject.Inject

class RemoteWordsDataSource @Inject constructor(
    private val apiService: WordApiService,
    private val networkHandler: NetworkHandler,
    private val htmlParser: HtmlParser
) : WordsDataSource {

    override suspend fun getWebsiteContent(): Result<Map<String, Int>> =
        networkHandler.safeApiCall {
            val html = apiService.getWebsiteContent()
            val words = htmlParser.parseWords(html)
            words.ifEmpty {
                throw Exception("No words found")
            }
        }
}